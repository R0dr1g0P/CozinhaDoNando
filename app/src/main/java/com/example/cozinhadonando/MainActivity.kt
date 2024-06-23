package com.example.cozinhadonando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cozinhadonando.ui.theme.CozinhaDoNandoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CozinhaDoNandoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            MainScreen(navController)
        }
        composable(
            "PaginaIngredientes/{receitaNome}/{receitaImagem}",
            arguments = listOf(
                navArgument("receitaNome") { type = NavType.StringType },
                navArgument("receitaImagem") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val receitaNome = backStackEntry.arguments?.getString("receitaNome")!!
            val receitaImagem = backStackEntry.arguments?.getInt("receitaImagem")!!
            val receita = Receita(receitaNome, receitaImagem)
            PaginaIngredientes(receita)
        }
    }
}

// Criação de uma class para guardar o nome e a imagem das receitas
data class Receita(val nome: String, val imagemID : Int)

@Composable
fun MainScreen(navController: NavController) {

    val receitas = listOf(
        Receita("Frango grelhado com legumes", R.drawable.frango_legumes),
        Receita("Bolo de Chocolate", R.drawable.bolo_chocolate)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "As Receitas do Chef Nando",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 35.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(3f)
            )
            Image(
                painter = painterResource(id = R.drawable.chapeu_cozinheiro),
                contentDescription = "Chapéu de Cozinheiro",
                modifier = Modifier
                    .weight(1f)
                    .size(120.dp)
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Lista de Receitas:",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 25.sp, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start
            )
        }

        receitas.forEach { receita ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("PaginaIngredientes/${receita.nome}/${receita.imagemID}")
                    }
            ) {
                Image(
                    painter = painterResource(id = receita.imagemID),
                    contentDescription = receita.nome,
                    modifier = Modifier
                        .weight(2f)
                        .size(120.dp)
                )
                Text(
                    text = receita.nome,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(5f)
                        .padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PaginaIngredientes(receita: Receita) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = receita.imagemID),
                contentDescription = receita.nome,
                modifier = Modifier
                    .weight(2f)
                    .size(150.dp)
            )
            Text(
                text = receita.nome,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 25.sp),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 20.dp)
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Número de doses:")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CozinhaDoNandoTheme {
        MainScreen(rememberNavController())
    }
}
