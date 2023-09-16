package br.com.forum.dto

import java.io.Serializable

data class UsuarioView(
    val id: Long? = null,
    val nome: String,
    val email: String
) : Serializable
