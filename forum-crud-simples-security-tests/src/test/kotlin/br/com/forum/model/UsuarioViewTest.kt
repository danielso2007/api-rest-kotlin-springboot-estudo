package br.com.forum.model

import br.com.forum.dto.UsuarioView

object UsuarioViewTest {
    fun build() = UsuarioView (
        id = 1,
        nome = "Daniel",
        email = "test@GmailTest.com"
    )
}