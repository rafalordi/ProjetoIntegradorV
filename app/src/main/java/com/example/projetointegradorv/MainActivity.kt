package com.example.projetointegradorv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.projetointegradorv.ui.theme.ProjetoIntegradorVTheme
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
    Column {
        CommandButton(text = "Play/Pause", command = "play_pause")
        CommandButton(text = "Stop", command = "stop")
        CommandButton(text = "Next Track", command = "next_track")
        CommandButton(text = "Previous Track", command = "previous_track")
        CommandButton(text = "Volume Up", command = "volume_up")
        CommandButton(text = "Volume Down", command = "volume_down")
    }
}

@Composable
fun CommandButton(text: String, command: String) {
    Button(onClick = { sendCommandToServer(command) }) {
        Text(text = text)
    }
}

private fun sendCommandToServer(command: String) {
    Thread {
        try {
            // Configure o endereço IP e a porta do servidor no PC
            val serverIp = "192.168.10.16"
            val serverPort = 5000

            // Crie um socket e conecte ao servidor
            val socket = Socket(serverIp, serverPort)

            // Obtenha o OutputStream para enviar o comando
            val outputStream: OutputStream = socket.getOutputStream()

            // Envie o comando como bytes
            outputStream.write(command.toByteArray())

            // Feche a conexão
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
