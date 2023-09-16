package br.com.forum.model

import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

object RespostaTest {
    fun build() = Resposta(
        id = 1,
        mensagem = "ok",
        dataCriacao = LocalDateTime.now(),
        autor = UsuarioTest.build(),
        topico = TopicoTest.build(),
        solucao = true
    )
}