package com.example.cozinhadonando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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


// Classe para representar um ingrediente
data class Ingrediente(val nome: String, val quantidadeOriginal: Float, val unidade: String)
// Criação de uma class para guardar o nome, a imagem e os ingredientes da receita
data class Receita(val nome: String, val imagemID : Int, val ingredientes: List<Ingrediente>)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receitas = listOf(
            Receita(
                nome = "Frango grelhado com legumes",
                imagemID = R.drawable.frango_legumes,
                ingredientes = listOf(
                    Ingrediente("Peito de frango", 200f, "g"),
                    Ingrediente("Legumes variados", 300f, "g"),
                    Ingrediente("Azeite de oliva", 2f, "colheres de sopa"),
                    Ingrediente("Sal", 10f, "g")
                )
            ),
            Receita(
                nome = "Bolo de Chocolate",
                imagemID = R.drawable.bolo_chocolate,
                ingredientes = listOf(
                    Ingrediente("Farinha de trigo", 250f, "g"),
                    Ingrediente("Açúcar", 200f, "g"),
                    Ingrediente("Ovos", 4f, "unidades"),
                    Ingrediente("Chocolate em pó", 50f, "g"),
                    Ingrediente("Fermento em pó", 10f, "g")
                )
            )
        )
        setContent {
            CozinhaDoNandoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(receitas)
                }
            }
        }
    }
}

@Composable
fun Navigation(receitas: List<Receita>) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            MainScreen(navController, receitas)
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
            val receita = receitas.find { it.nome == receitaNome && it.imagemID == receitaImagem }
            receita?.let {
                PaginaIngredientes(it)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController, receitas: List<Receita>) {
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

    val (numDoses, setNumDoses) = remember { mutableStateOf(1) }

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
            Text(
                text = "Número de doses:",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Slider(
                value = numDoses.toFloat(),
                onValueChange = { setNumDoses(it.toInt()) },
                valueRange = 1f..10f,
                steps = 9,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = numDoses.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                textAlign = TextAlign.Start
            )
        }
        Text(
            text = "Ingredientes:",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            modifier = Modifier.padding(top = 16.dp)
        )
        receita.ingredientes.forEach { ingrediente ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "${ingrediente.nome}: ",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.width(120.dp)
                )
                Text(
                    text = "${ingrediente.quantidadeOriginal * numDoses} ${ingrediente.unidade}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CozinhaDoNandoTheme {
        MainScreen(rememberNavController(), listOf())
    }
}
