package br.com.forum.integration

import br.com.forum.configuration.DatabaseContainerConfiguration
import br.com.forum.model.RespostaTest
import br.com.forum.repository.RespostaRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RespostaRepositoryTest : DatabaseContainerConfiguration() {

    @Autowired
    private lateinit var respostaRepository: RespostaRepository

    private val resposta = RespostaTest.build()

    @Test
    fun `deve listar Resposta`() {
        respostaRepository.save(resposta)
        val resposta = respostaRepository.count()
        Assertions.assertThat(resposta).isNotNull
    }

}