package com.example.quizmvvm.screens.resultado

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmvvm.R

@Composable
fun ResultadoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    nome: String,
    pontuacao: Int,
    totalPerguntas: Int
) {
    val porcentagem = if (totalPerguntas > 0) {
        pontuacao * 100 / totalPerguntas
    } else {
        0
    }

    val mensagem = when {
        porcentagem == 100 -> "Perfeito! Você domina o conteúdo!"
        porcentagem >= 70 -> "Muito bem! Você está no caminho certo!"
        porcentagem >= 40 -> "Bom esforço! Continue aprendendo!"
        else -> "Não desanime! A prática leva ao aprendizado."
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.azul_marinho))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "QUIZ LITÚRGICO",
            color = colorResource(R.color.dourado),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = colorResource(R.color.dourado).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🏆",
                fontSize = 48.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Quiz finalizado!",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Parabéns, $nome!",
            color = colorResource(R.color.texto_secundario),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.branco_gelo)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SUA PONTUAÇÃO",
                    color = colorResource(R.color.cinza_texto),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$pontuacao / $totalPerguntas",
                    color = colorResource(R.color.azul_marinho),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$porcentagem% de acertos",
                    color = colorResource(R.color.verde_correto),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = mensagem,
                    color = colorResource(R.color.cinza_texto),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                navController.navigate("inicio") {
                    popUpTo("inicio") {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dourado),
                contentColor = colorResource(R.color.azul_marinho)
            )
        ) {
            Text(
                text = "Jogar novamente",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                navController.navigate("inicio") {
                    popUpTo("inicio") {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Voltar ao início",
                fontSize = 16.sp
            )
        }
    }
}