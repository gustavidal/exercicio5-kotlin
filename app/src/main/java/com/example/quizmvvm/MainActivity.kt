package com.example.quizmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizmvvm.screens.inicio.InicioScreen
import com.example.quizmvvm.screens.inicio.InicioScreenViewModel
import com.example.quizmvvm.screens.quiz.QuizScreen
import com.example.quizmvvm.screens.quiz.QuizScreenViewModel
import com.example.quizmvvm.screens.resultado.ResultadoScreen
import com.example.quizmvvm.ui.theme.QuizMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizMVVMTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "inicio",
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(100)
                            )
                        },
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(100)
                            )
                        }
                    ) {
                        composable (route = "inicio") {
                            InicioScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                inicioScreenViewModel = InicioScreenViewModel()
                            )
                        }

                        composable (
                            route = "quiz/{nome}",
                            arguments = listOf(
                                navArgument(name = "nome") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val nome = it.arguments?.getString("nome")

                            QuizScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                quizScreenViewModel = QuizScreenViewModel(),
                                nome = nome!!
                            )
                        }

                        composable (
                            route = "resultado/{nome}/{pontuacao}/{qtdePerguntas}",
                            arguments = listOf(
                                navArgument(name = "nome") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "pontuacao") {
                                    type = NavType.IntType
                                },
                                navArgument(name = "qtdePerguntas") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val nome = it.arguments?.getString("nome")
                            val pontuacao = it.arguments?.getInt("pontuacao")
                            val qtdePerguntas = it.arguments?.getInt("qtdePerguntas")

                            ResultadoScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                nome = nome!!,
                                pontuacao = pontuacao!!,
                                totalPerguntas = qtdePerguntas!!
                            )
                        }
                    }
                }
            }
        }
    }
}

