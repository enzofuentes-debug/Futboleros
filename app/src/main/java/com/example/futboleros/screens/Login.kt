package com.example.futboleros.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.futboleros.R
import com.example.futboleros.navigation.AppScreens
import kotlinx.coroutines.launch

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Login(Modifier.align(Alignment.Center), navController ,viewModel)

    }
}

@Composable
fun Login(modifier: Modifier, navController: NavController, viewModel: LoginViewModel){


    val email : String by viewModel.email.observeAsState( initial= "")
    val password : String by viewModel.password.observeAsState( initial= "")
    val loginEnable : Boolean by viewModel.loginEnable.observeAsState( initial= false)

    val isLoading : Boolean by viewModel.isLoading.observeAsState( initial= false)
    val coroutineScope = rememberCoroutineScope()


    val context = LocalContext.current
    val isSuccess by viewModel.isSuccess.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState(initial = null)

    // Mensaje emergente de ingreso exitoso o error.
    LaunchedEffect(isSuccess, errorMessage) {
        if (isSuccess) {
            Toast.makeText(context, "¡Bienvenido a Futboleros!", Toast.LENGTH_SHORT).show()
            navController.navigate(AppScreens.AddResult.route)
            viewModel.resetSuccess() // Reseteamos para que no se repita el mensaje.
        } else if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    if(isLoading){

        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
    else
    {


            Column(modifier = modifier) {

                HeaderImage(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.padding(12.dp))
                EmailField(email){viewModel.onLoginChanged(it,password)}
                Spacer(modifier = Modifier.padding(4.dp))
                PasswordField(password) { viewModel.onLoginChanged(email, it) }
                
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(16.dp))
                ForgotPassword(Modifier.align(Alignment.End))
                LoginButton(loginEnable) {
                    coroutineScope.launch {
                        viewModel.onLoginSelected()
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                BackButton(navController)


            }

        }

}


@Composable
fun HeaderImage(modifier: Modifier){

    Image(painter = painterResource(id = R.drawable.logo1_app),
        contentDescription = "Logo",
        modifier = modifier
    )

}


@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit){
    TextField(value = email,onValueChange = {onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            focusedTextColor = Color(0xFFEBA131), // Cambia el color del texto
            unfocusedTextColor = Color(0xFFEBA131))) // Cambia el color del texto

}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit){



    TextField(value = password,onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Black,
            unfocusedContainerColor = Color.Black,
            focusedTextColor = Color(0xFFEBA131), // Cambia el color del texto
            unfocusedTextColor = Color(0xFFEBA131))) // Cambia el color del texto

}

@Composable
fun ForgotPassword(modifier: Modifier){
    Text( text = "Olvidaste la contraseña?",
        modifier = modifier.clickable{},
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFEBA131)
    )

}


@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit){
    Button(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Black,
            containerColor = Color(0xFFEBA131),
            contentColor = Color.White,
            disabledContentColor = Color.White),
        enabled = loginEnable
    ) {
        Text(text = "Ingresar")
    }

}

@Composable
fun BackButton(navController: NavController) {
    Button(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, // Cambia esto por el color que desees
            contentColor = Color(0xFFEBA131)
        )
    ) {
        Text(text = "Volver al Menú")
    }
}


@Preview(showBackground = true , showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController(), viewModel = viewModel())


}

