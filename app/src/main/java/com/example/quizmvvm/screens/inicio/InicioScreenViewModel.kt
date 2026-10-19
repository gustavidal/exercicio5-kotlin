package com.example.quizmvvm.screens.inicio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InicioScreenViewModel: ViewModel() {
    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

    private val _nomeValido = MutableLiveData<Boolean>()
    val nomeValido: LiveData<Boolean> = _nomeValido

    fun onNomeChanged(novoValor: String) {
        _nome.value = novoValor
        _nomeValido.value = novoValor.isNotBlank()
    }
}