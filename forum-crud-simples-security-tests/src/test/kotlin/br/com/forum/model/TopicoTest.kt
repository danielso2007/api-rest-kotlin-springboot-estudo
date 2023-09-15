package br.com.forum.model

object TopicoTest {

    fun build() = Topico(
        id = 1,
        titulo = "Kotlin Básico",
        mensagem = "Aprendendo kotlin básico",
        curso = CursoTest.build(),
        autor = AutorTest.build()
    )

}