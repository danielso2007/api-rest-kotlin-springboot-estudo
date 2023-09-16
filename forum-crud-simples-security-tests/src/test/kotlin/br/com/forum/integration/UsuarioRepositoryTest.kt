package br.com.forum.integration

import br.com.forum.configuration.DatabaseContainerConfiguration
import br.com.forum.model.UsuarioTest
import br.com.forum.repository.UsuarioRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryTest : DatabaseContainerConfiguration() {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    private val usuario = UsuarioTest.build()

    @Test
    fun `deve listar Usuario pelo email do Usuario`() {
        usuarioRepository.save(usuario)
        val usuario = usuarioRepository.findByEmail(usuario.email)
        assertThat(usuario).isNotNull
    }

    @Test
    fun `deve listar Usuario pelo nome ignore case do Usuario`() {
        usuarioRepository.save(usuario)
        val usuario = usuarioRepository.findByNomeContainingIgnoreCase(usuario.nome, PageRequest.of(0, 1))
        assertThat(usuario).isNotNull
    }

    @Test
    fun `deve listar Usuario pelo nome do Usuario`() {
        usuarioRepository.save(usuario)
        val usuario = usuarioRepository.findByNome(usuario.nome, PageRequest.of(0, 1))
        assertThat(usuario).isNotNull
    }

}