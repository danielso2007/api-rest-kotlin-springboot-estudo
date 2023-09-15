package br.com.forum.model

import br.com.forum.dto.TopicoView
import java.time.LocalDateTime

object TopicoViewTest {
    fun build() = TopicoView (
        id = 1,
        titulo = "Kotlin Básico",
        mensagem = "Aprendendo kotlin básico",
        dataCriacao = LocalDateTime.now(),
        status = StatusTopico.NAO_RESPONDIDO
    )
}