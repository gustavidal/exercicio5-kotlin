package com.example.quizmvvm.screens.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmvvm.R

@Composable
fun InicioScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    inicioScreenViewModel: InicioScreenViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val nome by inicioScreenViewModel.nome.observeAsState(initial = "")
    val nomeValido by inicioScreenViewModel.nomeValido.observeAsState(initial = false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.azul_marinho))
            .focusTarget()
            .pointerInput(Unit) {
                detectTapGestures( onTap = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                })
            },
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        -- header
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(R.drawable.bible_icon),
                contentDescription = "Logo App",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(90.dp)
            )
            Text(
                text = "Quiz Litúrgico",
                fontSize = 24.sp,
                color = colorResource(R.color.branco),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Coroinhas e Acólitos",
                fontSize = 18.sp,
                color = colorResource(R.color.dourado),
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedTextField(
            value = nome,
            singleLine = true,
            onValueChange = {
                inicioScreenViewModel.onNomeChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text("Digite um nome para jogar o quiz")
            },
            label = {
                Text("Nome")
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = colorResource(R.color.coral),
                focusedBorderColor = colorResource(R.color.coral),
                focusedLabelColor = colorResource(R.color.coral),
                unfocusedLabelColor = colorResource(R.color.coral),
                focusedTextColor = colorResource(R.color.branco_gelo),
                unfocusedTextColor = colorResource(R.color.branco_gelo)
            )
        )

//        -- button
        Button(
            onClick = {
                navController.navigate("quiz/$nome")
            },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 17.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.coral),
                    shape = RoundedCornerShape(32.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.coral),
                contentColor = colorResource(R.color.branco),
                disabledContentColor = colorResource(R.color.branco)
            ),
            enabled = nomeValido
        ) {
            Text(
                text = "COMEÇAR QUIZ",
                fontSize = 18.sp
            )
        }
    }
}