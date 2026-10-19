package com.example.quizmvvm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizmvvm.R

data class Alternativa(
    val letra: String,
    val texto: String
)

@Composable
fun PerguntaCard(
    categoria: String,
    enunciado: String,
    alternativas: List<Alternativa>,
    alternativaSelecionada: String?,
    alternativaCorreta: String? = null,
    respostaAvaliada: Boolean = false,
    habilitado: Boolean = true,
    onSelecionarAlternativa: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.branco_gelo),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = categoria.uppercase(),
            color = colorResource(R.color.cinza_texto),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = enunciado,
            color = colorResource(R.color.azul_marinho),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        alternativas.forEach { alternativa ->
            val correta: Boolean? = when {
                !respostaAvaliada -> null
                alternativa.letra == alternativaCorreta -> true
                alternativa.letra == alternativaSelecionada -> false
                else -> null
            }

            AlternativaItem(
                letra = alternativa.letra,
                texto = alternativa.texto,
                selecionada = alternativa.letra == alternativaSelecionada,
                correta = correta,
                habilitada = habilitado && !respostaAvaliada,
                onClick = {
                    onSelecionarAlternativa(alternativa.letra)
                }
            )
        }
    }
}
