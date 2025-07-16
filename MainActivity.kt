package com.example.divelogger

        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        import androidx.compose.foundation.layout.*
        import androidx.compose.foundation.lazy.LazyColumn
        import androidx.compose.material3.*
        import androidx.compose.runtime.*
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.unit.dp
        import androidx.lifecycle.lifecycleScope
        import kotlinx.coroutines.launch

        class MainActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                val db = DiveLogDatabase.getDatabase(this)
                val dao = db.diveLogDao()

                setContent {
                    MaterialTheme {
                        var logs by remember { mutableStateOf(listOf<DiveLog>()) }

                        var location by remember { mutableStateOf("") }
                        var depth by remember { mutableStateOf("") }
                        var duration by remember { mutableStateOf("") }
                        var temp by remember { mutableStateOf("") }
                        var weights by remember { mutableStateOf("") }
                        var notes by remember { mutableStateOf("") }

                        LaunchedEffect(Unit) {
                            logs = dao.getAll()
                        }

                        Column(Modifier.padding(16.dp)) {
                            Text("Add Dive Log", style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.height(8.dp))

                            fun textField(label: String, value: String, onChange: (String) -> Unit) {
                                OutlinedTextField(value, onChange, label = { Text(label) }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp))
                            }

                            textField("Location", location) { location = it }
                            textField("Depth (m)", depth) { depth = it }
                            textField("Duration (min)", duration) { duration = it }
                            textField("Temperature (°C)", temp) { temp = it }
                            textField("Weights (kg)", weights) { weights = it }
                            textField("Notes", notes) { notes = it }

                            Button(onClick = {
                                lifecycleScope.launch {
                                    val log = DiveLog(
                                        location = location,
                                        depth = depth.toIntOrNull() ?: 0,
                                        duration = duration.toIntOrNull() ?: 0,
                                        temperature = temp.toFloatOrNull() ?: 0f,
                                        weights = weights.toFloatOrNull() ?: 0f,
                                        notes = notes
                                    )
                                    dao.insert(log)
                                    logs = dao.getAll()
                                    location = ""; depth = ""; duration = ""; temp = ""; weights = ""; notes = ""
                                }
                            }, modifier = Modifier.padding(top = 8.dp)) {
                                Text("Save")
                            }

                            Spacer(Modifier.height(16.dp))
                            Text("Dive Logs", style = MaterialTheme.typography.titleMedium)
                            LazyColumn {
                                items(logs.size) { i ->
                                    val log = logs[i]
                                    Text("${log.location} - ${log.depth}m for ${log.duration}min @ ${log.temperature}°C", modifier = Modifier.padding(vertical = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }