package br.com.forum.service

import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.UsuarioViewMapper
import br.com.forum.model.Usuario
import br.com.forum.model.UsuarioTest
import br.com.forum.model.UsuarioViewTest
import br.com.forum.repository.UsuarioRepository
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

class UsuarioServiceTest {
    val usuarios = PageImpl(listOf(UsuarioTest.build()))

    val paginacao: Pageable = mockk()

    val slot = slot<Usuario>()

    var repository: UsuarioRepository = mockk {
        every { findByNomeContainingIgnoreCase(any(), any()) } returns usuarios
        every { findAll(paginacao) } returns usuarios
    }
    val usuarioViewMapper: UsuarioViewMapper = mockk()

    val usuarioservice = UsuarioService(
        repository, usuarioViewMapper
    )

    @Test
    fun `deve listar usuarios a partir do nome do usuario`() {
        every { usuarioViewMapper.map(capture(slot)) } returns UsuarioViewTest.build()

        usuarioservice.listar("Kotlin", paginacao)

        verify(exactly = 1) { repository.findByNomeContainingIgnoreCase(any(), any()) }
        verify(exactly = 1) { usuarioViewMapper.map(any()) }
        verify(exactly = 0) { repository.findAll(paginacao) }

        val usuario = UsuarioTest.build()
        Assertions.assertThat(slot.captured.nome).isEqualTo(usuario.nome)
        Assertions.assertThat(slot.captured.email).isEqualTo(usuario.email)
    }

    @Test
    fun `deve listar todos os usuarios quando o nome do usuario for nulo`() {
        every { usuarioViewMapper.map(capture(slot)) } returns UsuarioViewTest.build()

        usuarioservice.listar(null, paginacao)

        verify(exactly = 1) { repository.findAll(paginacao) }
        verify(exactly = 1) { usuarioViewMapper.map(any()) }
        verify(exactly = 0) { repository.findByNomeContainingIgnoreCase(any(), any()) }
    }

    @Test
    fun `deve listar not found exception quando topico nao for achado`() {
        every { repository.findById(any()) } returns Optional.empty()

        val atual = assertThrows<NotFoundException> {
            usuarioservice.buscarPorId(1)
        }

        Assertions.assertThat(atual.message).isEqualTo("Usuário não encontrado")
    }
}