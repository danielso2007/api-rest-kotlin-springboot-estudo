package br.com.forum.dto

import java.io.Serializable

data class CursoView(
    val id: Long?,
    val nome: String,
    val categoria: String
) : Serializable
