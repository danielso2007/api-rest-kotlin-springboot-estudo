package br.com.forum.service

import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.CursoViewMapper
import br.com.forum.model.*
import br.com.forum.repository.CursoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class CursoServiceTest {

    val cursos = PageImpl(listOf(CursoTest.build()))

    val paginacao: Pageable = mockk()

    val slot = slot<Curso>()

    var repository: CursoRepository = mockk {
        every { findByNomeContainingIgnoreCase(any(), any()) } returns cursos
        every { findAll(paginacao) } returns cursos
    }
    val cursoViewMapper: CursoViewMapper = mockk()

    val cursoservice = CursoService(
        repository, cursoViewMapper
    )

    @Test
    fun `deve listar cursos a partir do nome do curso`() {
        every { cursoViewMapper.map(capture(slot)) } returns CursoViewTest.build()

        cursoservice.listar("Kotlin", paginacao)

        verify(exactly = 1) { repository.findByNomeContainingIgnoreCase(any(), any()) }
        verify(exactly = 1) { cursoViewMapper.map(any()) }
        verify(exactly = 0) { repository.findAll(paginacao) }

        val curso = CursoTest.build()
        Assertions.assertThat(slot.captured.nome).isEqualTo(curso.nome)
        Assertions.assertThat(slot.captured.categoria).isEqualTo(curso.categoria)
    }

    @Test
    fun `deve listar todos os cursos quando o nome do curso for nulo`() {
        every { cursoViewMapper.map(capture(slot)) } returns CursoViewTest.build()

        cursoservice.listar(null, paginacao)

        verify(exactly = 1) { repository.findAll(paginacao) }
        verify(exactly = 1) { cursoViewMapper.map(any()) }
        verify(exactly = 0) { repository.findByNomeContainingIgnoreCase(any(), any()) }
    }

    @Test
    fun `deve listar not found exception quando topico nao for achado`() {
        every { repository.findById(any()) } returns Optional.empty()

        val atual = assertThrows<NotFoundException> {
            cursoservice.buscarPorId(1)
        }

        Assertions.assertThat(atual.message).isEqualTo("Curso não encontrado")
    }
    
}