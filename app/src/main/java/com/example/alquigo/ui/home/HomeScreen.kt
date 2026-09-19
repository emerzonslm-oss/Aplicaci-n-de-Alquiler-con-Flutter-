package com.example.alquigo.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alquigo.ui.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    onNavigateToAddProperty: () -> Unit,
    onNavigateToPropertyList: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AlquiGo - Inicio") },
                actions = {
                    TextButton(onClick = { viewModel.logout() }) {
                        Text("Cerrar Sesión", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Bienvenido a AlquiGo", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onNavigateToAddProperty,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("➕ Registrar Propiedad", style = MaterialTheme.typography.titleMedium)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = onNavigateToPropertyList,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("🔍 Ver Propiedades Disponibles", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
