package com.example.futboleros


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.futboleros.navigation.AppNavigation
import com.example.futboleros.navigation.AppScreens
import com.example.futboleros.ui.theme.FutbolerosTheme
import com.example.futboleros.ui.theme.White


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutbolerosTheme {
                Surface (color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }


    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivity (navController: NavController) {
    Scaffold{
        BodyContent(navController)
    }
}

@Composable
fun BodyContent(navController: NavController){

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



        Spacer(modifier = Modifier.height(6.dp))


        Button(
            onClick = { navController.navigate(route = AppScreens.Pantalla1.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEBA131), // Cambia esto por el color que desees
                contentColor = White,

                )
        ) {
            Text("Torneos")
        }


        Spacer(modifier = Modifier.height(48.dp))



/*
        Text ( "Aqui se cargar a los jugadores y equipos.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = White,
            style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(6.dp))

        Button(
            onClick = { navController.navigate(route = AppScreens.Login.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White, // Cambia esto por el color que desees
                contentColor = Color(0xFFEBA131)
            )
        ) {

            Text("Carga de jugadores")

        }

 */

        Spacer(modifier = Modifier.height(24.dp))

        Text ( "Aqui se cargan los resultados de cada fecha.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = White,
            style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(6.dp))

        Button(
            onClick = { navController.navigate(route = AppScreens.Login.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                contentColor = Color(0xFFEBA131),
            )
        ) {
            Text("Cargar Fecha")
        }



        Spacer(modifier = Modifier.height(72.dp))


        Text ( "creado por Enzo Fuentes",
            color = White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(8.dp)

        )



    }

}

@Preview (showBackground = true)
@Composable
fun DefaultPreview() {
    FutbolerosTheme() {
        AppNavigation()
        BodyContent(
            navController = rememberNavController(),
        )

    }

}