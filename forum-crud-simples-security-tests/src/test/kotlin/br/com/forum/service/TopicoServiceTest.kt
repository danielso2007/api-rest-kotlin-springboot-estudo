package br.com.forum.service

import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.TopicoFormMapper
import br.com.forum.mapper.TopicoViewMapper
import br.com.forum.model.Topico
import br.com.forum.model.TopicoTest
import br.com.forum.model.TopicoViewTest
import br.com.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    val slot = slot<Topico>()

    var repository: TopicoRepository = mockk {
        every { findByCursoNome(any(), any()) } returns topicos
        every { findAll(paginacao) } returns topicos
    }
    val topicoFormMapper: TopicoFormMapper = mockk()
    val topicoViewMapper: TopicoViewMapper = mockk()
    val em: EntityManager = mockk()

    val topicoService = TopicoService(
        repository, topicoFormMapper, topicoViewMapper, em
    )

    @Test
    fun `deve listar topicos a partir do nome do curso`() {
        every { topicoViewMapper.map(capture(slot)) } returns TopicoViewTest.build()

        topicoService.listar("Kotlin", paginacao)

        verify(exactly = 1) { repository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 0) { repository.findAll(paginacao) }

        val topico = TopicoTest.build()
        assertThat(slot.captured.titulo).isEqualTo(topico.titulo)
        assertThat(slot.captured.mensagem).isEqualTo(topico.mensagem)
        assertThat(slot.captured.status).isEqualTo(topico.status)
    }

    @Test
    fun `deve listar todos os topicos quando o nome do curso for nulo`() {
        every { topicoViewMapper.map(capture(slot)) } returns TopicoViewTest.build()

        topicoService.listar(null, paginacao)

        verify(exactly = 1) { repository.findAll(paginacao) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 0) { repository.findByCursoNome(any(), any()) }
    }

    @Test
    fun `deve listar not found exception quando topico nao for achado`() {
        every { repository.findById(any()) } returns Optional.empty()

        val atual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(1)
        }

        assertThat(atual.message).isEqualTo("Tópico não encontrado")
    }
}