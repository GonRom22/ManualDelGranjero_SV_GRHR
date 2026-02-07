package com.example.agendacontactosgrhr.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.agendacontactosgrhr.R
import com.example.agendacontactosgrhr.data.local.entity.ContactoEntity

/**
 * Composable que representa un item de contacto en la lista.
 *
 * Muestra:
 * - Nombre y teléfono del contacto
 * - Botón para editar
 * - Botón para eliminar con confirmación
 *
 * Recibe funciones como parámetros para manejar clics:
 * - onEliminarClick
 * - onEditarClick
 * - onVerDetalleClick
 */
@Composable
fun ContactoItem(
    contacto: ContactoEntity,
    onEliminarClick: (ContactoEntity) -> Unit,
    onEditarClick: (ContactoEntity) -> Unit,
    onVerDetalleClick: (ContactoEntity) -> Unit
) {
    //Variable que controla si se muestra diálogo de confirmación
    var mostrarDialogo by remember { mutableStateOf(false) }

    //Contexto de Android para mostrar Toast
    val context = LocalContext.current

    // Preparamos el drawable de error según el nombre
    val errorRes = when (contacto.name.lowercase()) {
        "abigail" -> R.drawable.abigail
        "alex" -> R.drawable.alex
        "elliott" -> R.drawable.elliott
        "emily" -> R.drawable.emily
        "harvey" -> R.drawable.harvey
        "leah" -> R.drawable.leah
        "maru" -> R.drawable.maru
        "penny" -> R.drawable.penny
        "sam" -> R.drawable.sam
        "sebastian" -> R.drawable.sebastian
        else -> R.drawable.ic_launcher_foreground
    }

    //Simula una tarjeta con sombreado
    Card(
        Modifier.fillMaxWidth()
            .padding(8.dp)
            //El clic en la tarjeta abre detalles
            .clickable { onVerDetalleClick(contacto) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- IMAGEN ---
            if (contacto.thumbnailResId != null) {
                // Imagen local precargada
                Image(
                    painter = painterResource(id = contacto.thumbnailResId),
                    contentDescription = "Foto de ${contacto.name}",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Imagen remota API o fallback
                AsyncImage(
                    model = if (contacto.thumbnail.startsWith("http")) contacto.thumbnail else null,
                    contentDescription = "Foto de ${contacto.name}",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = errorRes)
                )
            }




            //Columna para nombre y tlf
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(8f)
            ) {
                Text(contacto.name, style = MaterialTheme.typography.titleMedium)
                Text(contacto.estacion+" "+contacto.cumpleanos, style = MaterialTheme.typography.bodyMedium)
            }

            //Botón de editar
            IconButton(
                onClick = { onEditarClick(contacto) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Green)
            }
            //Botón de eliminar
            IconButton(
                onClick = { mostrarDialogo = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
            }
        }

        //Diálogo de confirmación para borrar
        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false }, //Si clica fuera se cierra
                title = { Text("Eliminar contacto") },
                text = { Text("¿Seguro que deseas eliminar este contacto?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onEliminarClick(contacto)
                            mostrarDialogo = false
                            Toast.makeText(context, "Contacto eliminado", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text("Eliminar", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
                }
            )
        }
    }
}
