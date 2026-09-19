package com.example.alquigo.ui.property

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.alquigo.data.model.Property

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    
    // Estados locales para el formulario (Sin ViewModels como se solicitó)
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Casa") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    
    var expandedTipoDropdown by remember { mutableStateOf(false) }
    val tiposDisponibles = listOf("Casa", "Apartamento", "Habitación", "Local Comercial")

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            Toast.makeText(context, "Imagen seleccionada de la galería (Simulación)", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Propiedad") },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) {
                        Text("← Volver", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sección de la foto simulada de la propiedad
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clickable { imagePickerLauncher.launch("image/*") },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "🏠",
                                style = MaterialTheme.typography.displayMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "¡Imagen Simulada Seleccionada!",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = imageUri.toString().take(45) + "...",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "📷",
                                style = MaterialTheme.typography.displayMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Simular Foto (Tocar para elegir de Galería)",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Campos del formulario
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título de la Propiedad") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio por Mes ($)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección Completa") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Selector de Tipo de Propiedad (Dropdown customizado para evitar errores de API/versiones)
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = tipo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Propiedad") },
                    trailingIcon = {
                        Text(
                            text = "▼ ",
                            modifier = Modifier.clickable { expandedTipoDropdown = true }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedTipoDropdown = true }
                )
                
                DropdownMenu(
                    expanded = expandedTipoDropdown,
                    onDismissRequest = { expandedTipoDropdown = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    tiposDisponibles.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                tipo = item
                                expandedTipoDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Registro
            Button(
                onClick = {
                    if (titulo.isBlank() || descripcion.isBlank() || precio.isBlank() || direccion.isBlank()) {
                        Toast.makeText(context, "Por favor complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                    } else {
                        val precioDouble = precio.toDoubleOrNull() ?: 0.0
                        val nuevaPropiedad = Property(
                            titulo = titulo,
                            descripcion = descripcion,
                            precio = precioDouble,
                            direccion = direccion,
                            tipo = tipo,
                            imagenUri = imageUri?.toString() ?: ""
                        )
                        
                        // Mensaje de éxito simulado para esta primera entrega
                        Toast.makeText(
                            context, 
                            "¡Propiedad registrada exitosamente (Simulado)!\n${nuevaPropiedad.titulo}", 
                            Toast.LENGTH_LONG
                        ).show()
                        
                        onNavigateBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Registrar Propiedad", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
