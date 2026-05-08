package com.example.futboleros.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.futboleros.R
import com.example.futboleros.navigation.AppScreens
import com.example.futboleros.ui.theme.FutbolerosTheme
import com.example.futboleros.ui.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Pantalla1 (navController: NavController) {
    Scaffold {

        Pantalla1_BodyContent(navController)
    }
}

@Composable
fun Pantalla1_BodyContent(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.logo1_app),
            contentDescription = "Logo de la app",
            modifier = Modifier
                //.padding(top = 28.dp)
                .size(300.dp)
        )

        Text ( "Torneos y Copas",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = White,
            style = MaterialTheme.typography.titleLarge)


        //BOTÓN 1

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate(route = AppScreens.Torneo1.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = White, // Cambia esto por el color que desees
                contentColor = Color(0xFFEBA131)
            )
        ) {
            Text("Copa Chelcos ",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold)
        }

        /*
                //BOTÓN 2


                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = { navController.navigate(route = AppScreens.Pantalla1.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White, // Cambia esto por el color que desees
                        contentColor = Color(0xFFEBA131)
                    )
                ) {
                    Text("Copa Libertadores",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold)
                }

                //BOTÓN 3

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = { navController.navigate(route = AppScreens.Pantalla1.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White, // Cambia esto por el color que desees
                        contentColor = Color(0xFFEBA131)
                    )
                ) {
                    Text("Copa Sudamericana",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold)
                }


         */

        //VOLVEMOS AL MENU

        Spacer(modifier = Modifier.height(72.dp))

        Button( onClick = { navController.navigate(route = AppScreens.MainActivity.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEBA131), // Cambia esto por el color que desees
                contentColor = White
            )
        )

        {
            Text("Volver al Menú")
        }

    }


}



@Preview(showBackground = true)
@Composable
fun Pantalla1Preview() {
    FutbolerosTheme() {
        Pantalla1_BodyContent(
            navController = rememberNavController(),
        )
    }
}



