package com.example.agendacontactosgrhr.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity

@Composable
fun ContactoItem(
    contacto: ContactoEntity,
    onEliminarClick: (ContactoEntity) -> Unit,
    onEditarClick: (ContactoEntity) -> Unit,
    onVerDetalleClick: (ContactoEntity) -> Unit
) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        Modifier.fillMaxWidth()
            .padding(8.dp)
            .clickable { onVerDetalleClick(contacto) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.padding(16.dp).weight(8f)) {
                Text(contacto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(contacto.telefono, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onEditarClick(contacto) }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Green)
            }
            IconButton(onClick = { mostrarDialogo = true }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("Eliminar contacto") },
                text = { Text("¿Seguro que deseas eliminar este contacto?") },
                confirmButton = {
                    TextButton(onClick = {
                        onEliminarClick(contacto)
                        mostrarDialogo = false
                        Toast.makeText(context, "Contacto eliminado", Toast.LENGTH_SHORT).show()
                    }) { Text("Eliminar", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
                }
            )
        }
    }
}
