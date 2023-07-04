package br.com.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class NovoTopicoForm(
    @field:NotEmpty @field:Size(min = 5, max = 100) val titulo: String,
    @field:NotEmpty(message = "A mensagem não pode ser vazia") @field:Size(min = 5, max = 150) val mensagem: String,
    @field:NotNull(message = "O id do curso não pode ser nulo") val idCurso: Long,
    @field:NotNull(message = "O id do autor não pode ser nulo") val idAutor: Long
)
