package br.com.forum.integration

import br.com.forum.model.CursoTest
import br.com.forum.repository.CursoRepository
import org.assertj.core.api.Assertions
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
class CursoRepositoryTest {

    @Autowired
    private lateinit var cursoRepository: CursoRepository

    private val curso = CursoTest.build()

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
    fun `deve listar Curso pelo nome do curso`() {
        cursoRepository.save(curso)
        val curso = cursoRepository.findByNome(curso.nome, PageRequest.of(0, 1))
        Assertions.assertThat(curso).isNotNull
    }

    @Test
    fun `deve listar Curso pelo nome ignore case do curso`() {
        cursoRepository.save(curso)
        val curso = cursoRepository.findByNomeContainingIgnoreCase(curso.nome, PageRequest.of(0, 1))
        Assertions.assertThat(curso).isNotNull
    }

}