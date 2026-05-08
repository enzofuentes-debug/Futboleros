package com.example.futboleros.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import com.example.futboleros.navigation.AppScreens
import com.example.futboleros.ui.theme.FutbolerosTheme

@Composable
fun AddResultScreen(navController: NavController, viewModel: AddResultViewModel) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isSuccess by viewModel.isSuccess.observeAsState(false)

    AddResultScreenContent(
        isLoading = isLoading,
        isSuccess = isSuccess,
        onSaveResult = { fecha, equipo1, equipo2, goles1, goles2 ->
            viewModel.saveResult(fecha, equipo1, equipo2, goles1, goles2)
        },
        onResetSuccess = { viewModel.resetSuccess() },
        onBackClick = {
            navController.navigate(AppScreens.MainActivity.route) {
                popUpTo(AppScreens.MainActivity.route) { inclusive = true }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResultScreenContent(
    isLoading: Boolean,
    isSuccess: Boolean,
    onSaveResult: (String, String, String, String, String) -> Unit,
    onResetSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var fecha by remember { mutableStateOf("") }
    var equipo1 by remember { mutableStateOf("") }
    var equipo2 by remember { mutableStateOf("") }
    var goles1 by remember { mutableStateOf("") }
    var goles2 by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val orangeColor = Color(0xFFEBA131)

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            showDialog = true
            fecha = ""
            equipo1 = ""
            equipo2 = ""
            goles1 = ""
            goles2 = ""
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onResetSuccess()
            },
            title = { Text("Éxito") },
            text = { Text("El resultado se cargo correctamente") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onResetSuccess()
                    }
                ) {
                    Text("Aceptar", color = orangeColor)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cargar Resultado",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha (ej: Fecha 6)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = orangeColor,
                        focusedLabelColor = orangeColor,
                        cursorColor = orangeColor
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = equipo1,
                        onValueChange = { equipo1 = it },
                        label = { Text("Equipo Local") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = orangeColor,
                            focusedLabelColor = orangeColor,
                            cursorColor = orangeColor
                        )
                    )
                    OutlinedTextField(
                        value = goles1,
                        onValueChange = { goles1 = it },
                        label = { Text("Goles") },
                        modifier = Modifier.width(80.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = orangeColor,
                            focusedLabelColor = orangeColor,
                            cursorColor = orangeColor
                        )
                    )
                }

                Text(
                    text = "VS",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = orangeColor
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = equipo2,
                        onValueChange = { equipo2 = it },
                        label = { Text("Equipo Visitante") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = orangeColor,
                            focusedLabelColor = orangeColor,
                            cursorColor = orangeColor
                        )
                    )
                    OutlinedTextField(
                        value = goles2,
                        onValueChange = { goles2 = it },
                        label = { Text("Goles") },
                        modifier = Modifier.width(80.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = orangeColor,
                            focusedLabelColor = orangeColor,
                            cursorColor = orangeColor
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = orangeColor
                    )
                } else {
                    Button(
                        onClick = {
                            onSaveResult(fecha, equipo1, equipo2, goles1, goles2)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = orangeColor),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Guardar Resultado", color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onBackClick() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Volver al Menú", color = Color(0xFFEBA131))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddResultScreenPreview() {
    FutbolerosTheme {
        AddResultScreenContent(
            isLoading = false,
            isSuccess = false,
            onSaveResult = { _, _, _, _, _ -> },
            onResetSuccess = { },
            onBackClick = { }
        )
    }
}
