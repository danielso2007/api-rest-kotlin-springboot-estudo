package br.com.forum.controller

import br.com.forum.config.JwtUtil
import br.com.forum.model.Role
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CursoControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    private var token: String? = null

    companion object {
        private const val RECURSO = "/v1/cursos"
        private const val RECURSO_ID = RECURSO.plus("/%s")
    }

    @BeforeEach
    fun setup() {
        token = gerarToken()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply<DefaultMockMvcBuilder?>(
            SecurityMockMvcConfigurers.springSecurity()
        ).build()
    }

    @Test
    fun `deve retornar codigo 400 quando chamar cursos sem token`() {
        mockMvc.get(RECURSO).andExpect { status { is4xxClientError() } }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar cursos com token`() {
        mockMvc.get(RECURSO){
            header("authorization", "Bearer $token")
        }.andExpect { status { is2xxSuccessful() } }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar curso por ir com token`() {
        mockMvc.get(RECURSO_ID.format("1")){
            headers { token?.let { this.setBearerAuth(it) } }
        }.andExpect { status { is2xxSuccessful() } }
    }

    private fun gerarToken(): String {
        val authorities = mutableListOf(Role(1, "LEITURA_ESCRITA"))
        return jwtUtil.generateToken("joao.silva@email.com", authorities)
    }

}