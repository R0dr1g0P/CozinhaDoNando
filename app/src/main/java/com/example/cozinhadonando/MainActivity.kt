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
import androidx.navigation.compose.rememberNavController
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
                    MainScreen(rememberNavController())
                }
            }
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
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline),
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
                        navController.navigate("detalhesScreen/${receita.nome}/${receita.imagemID}")
                    }
            ) {
                Image(
                    painter = painterResource(id = receita.imagemID),
                    contentDescription = receita.nome,
                    modifier = Modifier
                        .weight(1f)
                        .size(120.dp)
                )
                Text(
                    text = receita.nome,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun paginaIngredientes(receita: Receita) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
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
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CozinhaDoNandoTheme {
        MainScreen(rememberNavController())
    }
}
