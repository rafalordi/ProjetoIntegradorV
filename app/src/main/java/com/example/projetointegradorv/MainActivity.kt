package com.example.projetointegradorv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetointegradorv.ui.theme.ProjetoIntegradorVTheme
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjetoIntegradorVTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RemoteControlButtons()
                }
            }
        }
    }
}

@Composable
fun RemoteControlButtons() {
    var receivedInfo by remember { mutableStateOf("") }
    Column {
        CommandButton(text = "Play/Pause", command = "play_pause") { info ->
            receivedInfo = info
        }
        CommandButton(text = "Stop", command = "stop") { info ->
            receivedInfo = info
        }
        CommandButton(text = "Next Track", command = "next_track") { info ->
            receivedInfo = info
        }
        CommandButton(text = "Previous Track", command = "previous_track") { info ->
            receivedInfo = info
        }
        CommandButton(text = "Volume Up", command = "volume_up") { info ->
            receivedInfo = info
        }
        CommandButton(text = "Volume Down", command = "volume_down") { info ->
            receivedInfo = info
        }

        // Display area for information from the server
        Text(text = receivedInfo)
    }
}

@Composable
fun CommandButton(text: String, command: String, onClick: (String) -> Unit) {
    Button(onClick = { onClick(command) }) {
        Text(text = text)
    }
}

private fun sendCommandToServer(command: String, infoCallback: (String) -> Unit) {
    Thread {
        try {
            val serverIp = "192.168.10.16"
            val serverPort = 5000

            val socket = Socket(serverIp, serverPort)
            val outputStream: OutputStream = socket.getOutputStream()

            outputStream.write(command.toByteArray())

            // Receive information from the server
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
            val info = input.readLine()

            // Call the callback to update the receivedInfo state
            infoCallback(info)

            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.start()
}

@Preview(showBackground = true)
@Composable
fun RemoteControlButtonsPreview() {
    ProjetoIntegradorVTheme {
        RemoteControlButtons()
    }
}
