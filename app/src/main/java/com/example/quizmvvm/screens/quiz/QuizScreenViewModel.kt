package com.example.quizmvvm.screens.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class PerguntaQuiz(
    val categoria: String,
    val enunciado: String,
    val alternativas: List<String>,
    val respostaCorreta: String
)

class QuizScreenViewModel : ViewModel() {
    private val perguntas = listOf(
        PerguntaQuiz(
            categoria = "Objetos litúrgicos",
            enunciado = "Qual objeto é utilizado para incensar durante a liturgia?",
            alternativas = listOf(
                "Cálice", "Turíbulo", "Galheta", "Patena"
            ),
            respostaCorreta = "B"
        ),
        PerguntaQuiz(
            categoria = "Objetos litúrgicos",
            enunciado = "Qual objeto é utilizado para cobrir o cálice?",
            alternativas = listOf(
                "Pala", "Manustérgio", "Naveta", "Ambão"
            ),
            respostaCorreta = "A"
        ),
        PerguntaQuiz(
            categoria = "Espaço litúrgico",
            enunciado = "Qual é o lugar de onde são proclamadas as leituras bíblicas?",
            alternativas = listOf(
                "Sacrário", "Credência", "Ambão", "Batistério"
            ),
            respostaCorreta = "C"
        ),
        PerguntaQuiz(
            categoria = "Objetos litúrgicos",
            enunciado = "Qual objeto é utilizado para guardar as hóstias consagradas?",
            alternativas = listOf(
                "Cibório", "Turíbulo", "Lavabo", "Lecionário"
            ),
            respostaCorreta = "A"
        ),
        PerguntaQuiz(
            categoria = "Livros litúrgicos",
            enunciado = "Qual livro contém as leituras bíblicas proclamadas na Missa?",
            alternativas = listOf(
                "Missal Romano", "Lecionário",
                "Ritual de Exéquias", "Saltério"
            ),
            respostaCorreta = "B"
        ),
        PerguntaQuiz(
            categoria = "Cores litúrgicas",
            enunciado = "Qual cor litúrgica é tradicionalmente utilizada no tempo do Advento?",
            alternativas = listOf(
                "Verde", "Vermelho", "Roxo", "Preto"
            ),
            respostaCorreta = "C"
        ),
        PerguntaQuiz(
            categoria = "Cores litúrgicas",
            enunciado = "Qual cor litúrgica é utilizada na celebração do Domingo de Pentecostes?",
            alternativas = listOf(
                "Branco", "Vermelho", "Verde", "Roxo"
            ),
            respostaCorreta = "B"
        ),
        PerguntaQuiz(
            categoria = "Partes da Missa",
            enunciado = "Como se chama a parte da Missa em que são proclamadas as leituras e o Evangelho?",
            alternativas = listOf(
                "Ritos iniciais", "Liturgia Eucarística",
                "Ritos finais", "Liturgia da Palavra"
            ),
            respostaCorreta = "D"
        ),
        PerguntaQuiz(
            categoria = "Partes da Missa",
            enunciado = "Em qual parte da Missa ocorre a consagração do pão e do vinho?",
            alternativas = listOf(
                "Liturgia Eucarística", "Ato penitencial",
                "Liturgia da Palavra", "Ritos finais"
            ),
            respostaCorreta = "A"
        ),
        PerguntaQuiz(
            categoria = "Serviço ao altar",
            enunciado = "Qual é a principal função do coroinha durante a celebração?",
            alternativas = listOf(
                "Substituir o sacerdote",
                "Presidir a Liturgia da Palavra",
                "Auxiliar o sacerdote e servir ao altar",
                "Distribuir a comunhão sem autorização"
            ),
            respostaCorreta = "C"
        ),
        PerguntaQuiz(
            categoria = "Serviço ao altar",
            enunciado = "Qual móvel é utilizado para apoiar os objetos necessários à celebração?",
            alternativas = listOf(
                "Credência", "Patena", "Pala", "Cíngulo"
            ),
            respostaCorreta = "A"
        ),
        PerguntaQuiz(
            categoria = "Gestos litúrgicos",
            enunciado = "Qual gesto expressa adoração diante do Santíssimo Sacramento?",
            alternativas = listOf(
                "Aplauso", "Genuflexão", "Aceno", "Palmas"
            ),
            respostaCorreta = "B"
        ),
        PerguntaQuiz(
            categoria = "Serviço ao altar",
            enunciado = "O que o coroinha deve fazer ao cometer um erro durante a celebração?",
            alternativas = listOf(
                "Abandonar o serviço",
                "Interromper a celebração",
                "Agir com calma e continuar servindo com atenção",
                "Chamar a atenção dos demais"
            ),
            respostaCorreta = "C"
        ),
        PerguntaQuiz(
            categoria = "Ano litúrgico",
            enunciado = "Qual tempo litúrgico prepara a Igreja para a celebração do Natal?",
            alternativas = listOf(
                "Quaresma", "Tempo Comum", "Tempo Pascal", "Advento"
            ),
            respostaCorreta = "D"
        ),
        PerguntaQuiz(
            categoria = "Ano litúrgico",
            enunciado = "Qual é a principal celebração do calendário litúrgico cristão?",
            alternativas = listOf(
                "Solenidade de Pentecostes",
                "Domingo de Páscoa",
                "Festa da Sagrada Família",
                "Quarta-feira de Cinzas"
            ),
            respostaCorreta = "B"
        )
    )

    private val _indicePergunta = MutableLiveData(0)
    val indicePergunta: LiveData<Int> = _indicePergunta

    private val _perguntaAtual = MutableLiveData(perguntas[0])
    val perguntaAtual: LiveData<PerguntaQuiz> = _perguntaAtual

    private val _alternativaSelecionada = MutableLiveData<String?>(null)
    val alternativaSelecionada: LiveData<String?> = _alternativaSelecionada

    private val _respostaAvaliada = MutableLiveData(false)
    val respostaAvaliada: LiveData<Boolean> = _respostaAvaliada

    private val _respostaCorreta = MutableLiveData<String?>(null)
    val respostaCorreta: LiveData<String?> = _respostaCorreta

    private val _pontuacao = MutableLiveData(0)
    val pontuacao: LiveData<Int> = _pontuacao

    private val _quizFinalizado = MutableLiveData(false)
    val quizFinalizado: LiveData<Boolean> = _quizFinalizado

    private var avancarJob: Job? = null

    fun selecionarAlternativa(letra: String) {
        if (_respostaAvaliada.value == true) {
            return
        }

        _alternativaSelecionada.value = letra
    }

    fun confirmarResposta() {
        val selecionada = _alternativaSelecionada.value

        if (selecionada == null) {
            return
        }

        if (_respostaAvaliada.value == true) {
            return
        }

        val correta = _perguntaAtual.value?.respostaCorreta

        if (correta == null) {
            return
        }

        _respostaCorreta.value = correta
        _respostaAvaliada.value = true

        if (selecionada == correta) {
            _pontuacao.value = (_pontuacao.value ?: 0) + 1
        }

        avancarJob?.cancel()

        avancarJob = viewModelScope.launch {
            delay(1500)
            avancarPergunta()
        }
    }

    private fun avancarPergunta() {
        val proximoIndice = (_indicePergunta.value ?: 0) + 1

        if (proximoIndice >= perguntas.size) {
            _quizFinalizado.value = true
            return
        }

        _indicePergunta.value = proximoIndice
        _perguntaAtual.value = perguntas[proximoIndice]
        _alternativaSelecionada.value = null
        _respostaCorreta.value = null
        _respostaAvaliada.value = false
    }

    fun reiniciarQuiz() {
        avancarJob?.cancel()

        _indicePergunta.value = 0
        _perguntaAtual.value = perguntas[0]
        _alternativaSelecionada.value = null
        _respostaCorreta.value = null
        _respostaAvaliada.value = false
        _pontuacao.value = 0
        _quizFinalizado.value = false
    }

    fun totalPerguntas(): Int {
        return perguntas.size
    }
}