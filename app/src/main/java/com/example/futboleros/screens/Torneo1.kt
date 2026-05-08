package com.example.futboleros.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.futboleros.R
import com.example.futboleros.navigation.AppScreens
import com.example.futboleros.ui.theme.FutbolerosTheme
import com.example.futboleros.ui.theme.White

data class equipo(
    val pos: Int,
    val logo: Int,
    val equipo: String,
    val jp: Int,
    val diff: String,
    val pts: Int
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Torneo1(navController: NavController, viewModel: TorneoViewModel = viewModel()) {
    val equipos by viewModel.tablaPosiciones.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    Scaffold {
        CopaChelcos_BodyContent(navController, equipos, isLoading)
    }
}

@Composable
fun CopaChelcos_BodyContent(
    navController: NavController, 
    equipos: List<equipo>, 
    isLoading: Boolean
) {
    val orangeColor = Color(0xFFEBA131)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Copa Chelcos",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            color = orangeColor
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Tabla de posiciones",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Tabla
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(orangeColor)
                .border(1.dp, Color.Black),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeaderCell("N°", Modifier.weight(0.10f))
            HeaderCell("Equipos", Modifier.weight(0.45f), textAlign = TextAlign.Start)
            HeaderCell("PJ", Modifier.weight(0.10f))
            HeaderCell("+/-", Modifier.weight(0.10f))
            HeaderCell("PTS", Modifier.weight(0.10f))
        }

        // Tabla de equipos
        if (isLoading && equipos.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = orangeColor)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                items(equipos) { equipo ->
                    TableRowItem(equipo)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate(route = AppScreens.Fixture.route) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEBA131),
                contentColor = White,
            )
        ) {
            Text("Ver Fixture y Resultados")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(route = AppScreens.Pantalla1.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        ) {
            Text("Volver a Torneos", color = orangeColor)
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}



@Composable
fun HeaderCell(text: String, modifier: Modifier, textAlign: TextAlign = TextAlign.Center) {
    Box(
        modifier = modifier
            .border(0.2.dp, Color.Black)
            .padding(vertical = 8.dp, horizontal = 6.dp),
        contentAlignment = if (textAlign == TextAlign.Start) Alignment.CenterStart else Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = textAlign
        )
    }
}



@Composable
fun TableRowItem(equipo: equipo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(0.5.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {


        // N°
        Box(
            modifier = Modifier
                .weight(0.10f)
                .border(0.5.dp, Color.Black)
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = equipo.pos.toString(), color = Color.Black, fontWeight = FontWeight.Bold)
        }


        // Equipos
        Row(
            modifier = Modifier
                .weight(0.45f)
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 6.dp))
            Image(
                painter = painterResource(id = equipo.logo),
                contentDescription = null,
                modifier = Modifier.size(24.dp))


            Spacer(modifier = Modifier.width(8.dp))


            Text(text = equipo.equipo, color = Color.Black, fontSize = 14.sp)
        }


        // PJ
        Box(
            modifier = Modifier
                .weight(0.10f)
                .border(0.5.dp, Color.Black)
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = equipo.jp.toString(), color = Color.Black)
        }



        // +/-
        Box(
            modifier = Modifier
                .weight(0.10f)
                .border(0.5.dp, Color.Black)
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = equipo.diff, color = Color.Black)
        }


        // PTS
        Box(
            modifier = Modifier
                .weight(0.10f)
                .border(0.5.dp, Color.Black)
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = equipo.pts.toString(), color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun PreviewTorneo1() {
    FutbolerosTheme {
        CopaChelcos_BodyContent(
            navController = rememberNavController(),
            equipos = listOf(
                equipo(1, R.drawable.equipo_ajax, "Ajax (Preview)", 0, "0", 0)
            ),
            isLoading = false
        )
    }
}
