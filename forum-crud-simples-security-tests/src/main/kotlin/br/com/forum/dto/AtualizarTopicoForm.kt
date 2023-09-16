package br.com.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.io.Serializable

data class AtualizarTopicoForm(
    val id: Long,
    @field:NotEmpty @field:Size(min = 5, max = 100) val titulo: String,
    @field:NotEmpty(message = "A mensagem não pode ser vazia") @field:Size(min = 5, max = 150) val mensagem: String
) : Serializable
