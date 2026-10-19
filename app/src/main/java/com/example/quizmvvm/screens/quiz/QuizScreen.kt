package com.example.quizmvvm.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmvvm.R
import com.example.quizmvvm.components.Alternativa
import com.example.quizmvvm.components.PerguntaCard

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    quizScreenViewModel: QuizScreenViewModel,
    nome: String
) {
    val pergunta    by quizScreenViewModel.perguntaAtual.observeAsState()
    val indice      by quizScreenViewModel.indicePergunta.observeAsState(0)
    val selecionada by quizScreenViewModel.alternativaSelecionada.observeAsState(null)
    val avaliada    by quizScreenViewModel.respostaAvaliada.observeAsState(false)
    val correta     by quizScreenViewModel.respostaCorreta.observeAsState(null)
    val finalizado  by quizScreenViewModel.quizFinalizado.observeAsState(false)
    val pontuacao   by quizScreenViewModel.pontuacao.observeAsState(0)

    LaunchedEffect(finalizado) {
        if (finalizado) {
            navController.navigate("resultado/$nome/$pontuacao/${quizScreenViewModel.totalPerguntas()}") {
                popUpTo("quiz/$nome") {
                    inclusive = true
                }
            }
        }
    }

    if (pergunta == null) return

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.azul_marinho))
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "QUIZ LITÚRGICO",
                color = colorResource(R.color.dourado),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Vamos lá, $nome!",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            LinearProgressIndicator(
                progress = {
                    (indice + 1).toFloat() /
                            quizScreenViewModel.totalPerguntas()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = colorResource(R.color.dourado),
                trackColor = Color.White.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pergunta ${indice + 1} de ${quizScreenViewModel.totalPerguntas()}",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }

        PerguntaCard(
            categoria = pergunta!!.categoria,
            enunciado = pergunta!!.enunciado,
            alternativas = pergunta!!.alternativas.mapIndexed { index, texto ->
                Alternativa(('A' + index).toString(), texto)
            },
            alternativaSelecionada = selecionada,
            alternativaCorreta = correta,
            respostaAvaliada = avaliada,
            habilitado = !avaliada,
            onSelecionarAlternativa = { letra ->
                quizScreenViewModel.selecionarAlternativa(letra)
            }
        )

        Button(
            onClick = {
                quizScreenViewModel.confirmarResposta()
            },
            enabled = selecionada != null && !avaliada,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.dourado),
                contentColor = colorResource(R.color.azul_marinho)
            )
        ) {
            Text(
                text = "Confirmar resposta",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
