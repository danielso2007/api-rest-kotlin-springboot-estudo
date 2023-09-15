package br.com.forum.model

object AutorTest {
    fun build() = Usuario(
        id = 1,
        nome = "Daniel",
        email = "test@GmailTest.com",
        password = "123456"
    )
}