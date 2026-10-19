package com.example.quizmvvm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizmvvm.R

@Composable
fun AlternativaItem(
    letra: String,
    texto: String,
    selecionada: Boolean,
    correta: Boolean? = null,
    habilitada: Boolean = true,
    onClick: () -> Unit
) {
    val cor = when {
        correta == true -> colorResource(R.color.verde_correto)
        correta == false && selecionada -> colorResource(R.color.vermelho_incorreto)
        selecionada -> colorResource(R.color.azul_marinho)
        else -> colorResource(R.color.cinza_texto)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = when {
                    correta == true -> colorResource(R.color.verde_correto).copy(alpha = 0.10f)
                    correta == false && selecionada -> colorResource(R.color.vermelho_incorreto).copy(alpha = 0.10f)
                    else -> colorResource(R.color.branco_gelo)
                },
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = cor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                enabled = habilitada,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selecionada,
            onClick = {
                if (habilitada) {
                    onClick()
                }
            },
            enabled = habilitada,
            colors = RadioButtonDefaults.colors(
                selectedColor = cor,
                unselectedColor = colorResource(R.color.azul_marinho)
            )
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = letra,
            color = colorResource(R.color.azul_marinho),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = texto,
            color = colorResource(R.color.azul_marinho),
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )
    }
}
