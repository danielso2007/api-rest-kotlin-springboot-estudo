package br.com.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.io.Serializable

data class NovaRespostaForm(
    @field:NotEmpty(message = "A mensagem não pode ser vazia") @field:Size(min = 5, max = 150) val mensagem: String,
    @field:NotNull(message = "O id do autor não pode ser nulo") val idAutor: Long,
    @field:NotNull(message = "O id do topico não pode ser nulo") val idTopico: Long,
    @field:NotNull(message = "A solução não pode ser nula") val solucao: Boolean
) : Serializable
