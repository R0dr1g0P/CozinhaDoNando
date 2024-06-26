package com.example.cozinhadonando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
                    Ingrediente("Azeite", 2f, "colheres de sopa"),
                    Ingrediente("Alho picado", 1f, "dente(s)"),
                    Ingrediente("Limão espremido", 1f, "unidade(s)"),
                    Ingrediente("Cenoura", 150f, "g"),
                    Ingrediente("Abóbora", 150f, "g"),
                    Ingrediente("Pimentos", 100f, "g"),
                    Ingrediente("Ervas Aromáticas", 1f, "colher(es) de chá"),
                    Ingrediente("Sal", 1f, "colher(es) de chá"),
                    Ingrediente("Pimenta", 1f, "colher(es) de chá")
                )
            ),
            Receita(
                nome = "Wrap de Frango",
                imagemID = R.drawable.wrap_frango,
                ingredientes = listOf(
                    Ingrediente("Peito de frango cozido e desfiado", 100f, "g"),
                    Ingrediente("Tortilla de trigo", 1f, "unidade(s)"),
                    Ingrediente("Alface", 1f, "folha(s)"),
                    Ingrediente("Tomate picado", 50f, "g"),
                    Ingrediente("Queijo ralado", 30f, "g"),
                    Ingrediente("Maionese ou iogurte", 1f, "colher(es) de sopa"),
                    Ingrediente("Mostarda", 1f, "colher(es) de chá"),
                    Ingrediente("Sal", 1f, "pitada(s)"),
                    Ingrediente("Pimenta", 1f, "pitada(s)")
                )
            ),
            Receita(
                nome = "Peixe grelhado com batata assada",
                imagemID = R.drawable.peixe_batatas,
                ingredientes = listOf(
                    Ingrediente("Filete de peixe", 150f, "g"),
                    Ingrediente("Azeite", 2f, "colheres de sopa"),
                    Ingrediente("Limão espremido", 1f, "unidade(s)"),
                    Ingrediente("Sal", 1f, "pitada(s)"),
                    Ingrediente("Pimenta", 1f, "pitada(s)"),
                    Ingrediente("Batata", 200f, "g"),
                    Ingrediente("Alecrim fresco", 1f, "ramo(s)"),
                    Ingrediente("Alho", 1f, "dente(s)"),
                    Ingrediente("Páprica", 1f, "pitada(s)")
                )
            ),
            Receita(
                nome = "Risoto de Cogumelos",
                imagemID = R.drawable.risoto_cogumelos,
                ingredientes = listOf(
                    Ingrediente("Arroz de risoto", 80f, "g"),
                    Ingrediente("Cogumelos fatiados", 100f, "g"),
                    Ingrediente("Cebola picada", 30f, "g"),
                    Ingrediente("Alho picado", 1f, "dente(s)"),
                    Ingrediente("Caldo de legumes", 250f, "ml"),
                    Ingrediente("Vinho branco", 30f, "ml"),
                    Ingrediente("Queijo ralado", 30f, "g"),
                    Ingrediente("Manteiga", 1f, "colher(es) de sopa"),
                    Ingrediente("Azeite", 1f, "colher(es) de sopa"),
                    Ingrediente("Sal", 1f, "pitada(s)"),
                    Ingrediente("Pimenta", 1f, "pitada(s)")
                )
            ),
            Receita(
                nome = "Bolo de Chocolate",
                imagemID = R.drawable.bolo_chocolate,
                ingredientes = listOf(
                    Ingrediente("Farinha de trigo", 30f, "g"),
                    Ingrediente("Açúcar", 30f, "g"),
                    Ingrediente("Chocolate em pó", 15f, "g"),
                    Ingrediente("Ovo", 1f, "unidade(s)"),
                    Ingrediente("Leite", 45f, "ml"),
                    Ingrediente("Óleo vegetal", 45f, "ml"),
                    Ingrediente("Fermento em pó", 1f, "g"),
                    Ingrediente("Sal", 1f, "pitada(s)")
                )
            ),
            Receita(
                nome = "Panqueca",
                imagemID = R.drawable.panqueca,
                ingredientes = listOf(
                    Ingrediente("Farinha de trigo", 60f, "g"),
                    Ingrediente("Açúcar", 10f, "g"),
                    Ingrediente("Fermento em pó", 4f, "g"),
                    Ingrediente("Sal", 1f, "pitada(s)"),
                    Ingrediente("Leite", 60f, "ml"),
                    Ingrediente("Ovos", 1f, "unidade(s)"),
                    Ingrediente("Manteiga derretida", 15f, "g")
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

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "As Receitas do Chef Nando",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 35.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(3f)
                )
                Image(
                    painter = painterResource(id = R.drawable.chapeu_cozinheiro),
                    contentDescription = "Chapéu de Cozinheiro",
                    modifier = Modifier
                        .weight(1f)
                        .size(200.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "- Lista de Receitas:",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    ),
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
                            .weight(1f)
                            .size(120.dp)
                    )
                    Text(
                        text = receita.nome,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 25.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .weight(2f)
                            .padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PaginaIngredientes(receita: Receita) {

    val (numDoses, setNumDoses) = remember { mutableStateOf(1) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = receita.imagemID),
                    contentDescription = receita.nome,
                    modifier = Modifier
                        .weight(1f)
                        .size(150.dp)
                )
                Text(
                    text = receita.nome,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 20.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Número de doses:",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                Slider(
                    value = numDoses.toFloat(),
                    onValueChange = { setNumDoses(it.toInt()) },
                    valueRange = 1f..10f,
                    steps = 9,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = numDoses.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start
                )
            }
            Text(
                text = "- Ingredientes:",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.padding(top = 16.dp)
            )
            receita.ingredientes.forEach { ingrediente ->
                val quantidadeOriginal = ingrediente.quantidadeOriginal
                val quantidade = quantidadeOriginal * numDoses
                val (quantidadeDisplay, unidade) = when (ingrediente.unidade) {
                    "g" -> if (quantidade >= 1000) {
                        (quantidade / 1000).toString() to "kg"
                    } else {
                        quantidade.toInt().toString() to "g"
                    }
                    "ml" -> if (quantidade >= 1000) {
                        (quantidade / 1000).toString() to "L"
                    } else {
                        quantidade.toInt().toString() to "ml"
                    }
                    else -> quantidade.toInt().toString() to ingrediente.unidade
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "${ingrediente.nome}: ",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(4f)
                    )
                    Text(
                        text = "$quantidadeDisplay $unidade",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(3f)
                    )
                }
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
