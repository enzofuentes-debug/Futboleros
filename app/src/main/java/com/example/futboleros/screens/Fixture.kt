package com.example.futboleros.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.futboleros.R
import com.example.futboleros.navigation.AppScreens
import com.example.futboleros.ui.theme.FutbolerosTheme

data class Match(
    val team1: String,
    val team1Logo: Int,
    val score: String, // "vs" o "1-0"
    val team2: String,
    val team2Logo: Int
)

data class Fecha(
    val title: String,
    val matches: List<Match>
)

@Composable
fun FixtureScreen(navController: NavController, viewModel: FixtureViewModel) {
    val fixtureData by viewModel.fixtureData.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    FixtureScreenContent(
        navController = navController,
        fixtureData = fixtureData,
        isLoading = isLoading
    )
}

@Composable
fun FixtureScreenContent(
    navController: NavController,
    fixtureData: List<Fecha>,
    isLoading: Boolean
) {
    val orangeColor = Color(0xFFEBA131)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Copa Chelcos",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(orangeColor, RoundedCornerShape(6.dp))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fixture",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (isLoading && fixtureData.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(fixtureData) { fecha ->
                        FechaCard(fecha)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.navigate(AppScreens.Torneo1.route) },
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Posiciones", color = Color.White)
            }
            Button(
                onClick = { navController.navigate(AppScreens.Pantalla1.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Volver", color = orangeColor)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun getTeamLogo(teamName: String): Int {
    return when (teamName.lowercase()) {
        "equipo 1", "ajax" -> R.drawable.equipo_ajax
        "equipo 2", "juventud unida" -> R.drawable.equipo_juventudunida
        "equipo 3", "independiente" -> R.drawable.equipo_independiente
        "equipo 4", "ferro" -> R.drawable.equipo_ferro
        "equipo 5", "athenas" -> R.drawable.equipo_athenas
        "equipo 6", "mambas negras" -> R.drawable.equipo_mambasnegras
        "equipo 7", "chelsea" -> R.drawable.equipo_chelsea
        "equipo 8", "el pincha" -> R.drawable.equipo_elpincha
        "equipo 9", "la fiore" -> R.drawable.equipo_lafiore
        "equipo 10", "virgen del valle" -> R.drawable.equipo_virgendelvalle
        "equipo 11", "callejeras" -> R.drawable.equipo_callejeras
        "equipo 12", "la roma" -> R.drawable.equipo_laroma
        else -> 0 // Podrías poner un R.drawable.default_logo si existe
    }
}

@Composable
fun FechaCard(fecha: Fecha) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White,)
            .border(1.dp, Color.Black,)
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = fecha.title,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        fecha.matches.forEach { match ->
            MatchRow(match)
        }
    }
}


@Composable
fun MatchRow(match: Match) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val logo1 = if (match.team1Logo != 0) match.team1Logo else getTeamLogo(match.team1)
        val logo2 = if (match.team2Logo != 0) match.team2Logo else getTeamLogo(match.team2)
        

        if (logo1 != 0) {
            Image(
                painter = painterResource(id = logo1),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = match.team1,
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = match.score,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = match.team2,
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (logo2 != 0) {
            Image(
                painter = painterResource(id = logo2),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Spacer(modifier = Modifier.width(16.dp))
}



@Preview(showSystemUi = true)
@Composable
fun PreviewFixture() {
    FutbolerosTheme {
        FixtureScreenContent(
            navController = rememberNavController(),
            fixtureData = listOf(
                Fecha(
                    title = "Fecha 6 (Preview)",
                    matches = listOf(
                        Match("Equipo 1", 0, "vs", "Equipo 2", 0)
                    )
                )
            ),
            isLoading = false
        )
    }
}
