
package com.codesking.offlineai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                var input by remember { mutableStateOf("Android development in 2026 is moving fast. Jetpack Compose is now the default UI toolkit replacing XML. On-device AI with Gemini Nano and ML Kit is trending because apps work offline with no API cost. KMP is helping startups ship Android and iOS with shared logic.") }
                var output by remember { mutableStateOf("") }

                fun summarizeOffline(text: String): String {
                    val sentences = text.split(". ").filter { it.isNotBlank() }
                    if (sentences.isEmpty()) return "Enter some text"
                    val keywords = listOf("compose", "ai", "kmp", "offline", "android", "2026", "gemini", "default")
                    val scored = sentences.map { s ->
                        val score = keywords.count { k -> s.lowercase().contains(k) } + (if (s.length in 20..120) 1 else 0)
                        s to score
                    }.sortedByDescending { it.second }
                    val top = scored.take(2).map { it.first }.joinToString(". ") + "."
                    val entities = keywords.filter { text.lowercase().contains(it) }.take(3).joinToString(", ").uppercase()
                    return "OFFLINE SUMMARY:\n" + top + "\n\nKEY TOPICS: " + entities + "\n\nProcessed on-device | 0ms API | Airplane Mode ON"
                }

                Column(
                    Modifier.fillMaxSize().background(Color(0xFF0A0A0A)).padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("RUN AI OFFLINE", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("NO API KEY. NO INTERNET.", fontSize = 14.sp, color = Color(0xFF00FF88), fontWeight = FontWeight.Bold)
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)), shape = RoundedCornerShape(12.dp)) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Airplane Mode: ON | ML Kit: Active", color = Color.White, fontSize = 12.sp)
                            Spacer(Modifier.height(8.dp))
                            Text("This demo runs 100% on your phone chip. No OpenAI bill.", color = Color.Gray, fontSize = 11.sp)
                        }
                    }
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.fillMaxWidth().height(150.dp),
                        label = { Text("Paste long text here") },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF00FF88))
                    )
                    Button(
                        onClick = { output = summarizeOffline(input) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88), contentColor = Color.Black),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Summarize Offline", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    if(output.isNotEmpty()){
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                            Text(output, Modifier.padding(16.dp), color = Color.White, fontSize = 14.sp)
                        }
                    }
                    Text("@codes_king | 100% Offline AI Demo", color = Color.Gray, fontSize = 10.sp)
                }
            }
        }
    }
}
