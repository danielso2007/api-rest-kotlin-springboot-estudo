package br.com.forum.integration

import br.com.forum.model.UsuarioTest
import br.com.forum.repository.UsuarioRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryTest {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    private val usuario = UsuarioTest.build()

    companion object {
        @Container
        private val pgsqlContainer = PostgreSQLContainer<Nothing>("postgres:15").apply {
            withDatabaseName("testdb")
            withUsername("joao")
            withPassword("12345")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", pgsqlContainer::getJdbcUrl);
            registry.add("spring.datasource.password", pgsqlContainer::getPassword);
            registry.add("spring.datasource.username", pgsqlContainer::getUsername);
        }
    }

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