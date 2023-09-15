package br.com.forum.model

object UsuarioTest {
    fun build() = Usuario(
        id = 1,
        nome = "Daniel",
        email = "test@GmailTest.com",
        password = "123456"
    )
}