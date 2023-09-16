package br.com.forum.integration

import br.com.forum.model.RespostaTest
import br.com.forum.repository.RespostaRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RespostaRepositoryTest {

    @Autowired
    private lateinit var respostaRepository: RespostaRepository

    private val resposta = RespostaTest.build()

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
            registry.add("spring.datasource.url", pgsqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", pgsqlContainer::getPassword)
            registry.add("spring.datasource.username", pgsqlContainer::getUsername)
        }
    }

    @Test
    fun `deve listar Resposta`() {
        respostaRepository.save(resposta)
        val resposta = respostaRepository.count()
        Assertions.assertThat(resposta).isNotNull
    }

}