package br.com.forum.integration

import br.com.forum.configuration.DatabaseContainerConfiguration
import br.com.forum.model.CursoTest
import br.com.forum.repository.CursoRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CursoRepositoryTest : DatabaseContainerConfiguration() {

    @Autowired
    private lateinit var cursoRepository: CursoRepository

    private val curso = CursoTest.build()

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