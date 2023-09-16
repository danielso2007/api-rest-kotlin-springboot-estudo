package br.com.forum.dto

import java.io.Serializable

data class TopicoPorCategoriaDto (
    val categoria: String,
    val quantidade: Long
) : Serializable