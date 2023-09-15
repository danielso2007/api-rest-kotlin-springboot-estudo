package br.com.forum.model

import br.com.forum.dto.CursoView
import br.com.forum.dto.TopicoView
import java.time.LocalDateTime

object CursoViewTest {
    fun build() = CursoView (
        id = 1,
        nome = "Kotlin",
        categoria = "Programação"
    )
}