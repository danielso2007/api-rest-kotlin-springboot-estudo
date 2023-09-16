package br.com.forum.controller

import br.com.forum.dto.NovaRespostaForm
import br.com.forum.service.RespostaService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/respostas")
@SecurityRequirement(name = "bearerAuth")
class RespostaController(
    private val respostaService: RespostaService
) {

    @PostMapping
    @CacheEvict(value = ["topicosCache"], allEntries = true)
    fun salvar(@RequestBody @Valid resposta: NovaRespostaForm) = respostaService.salvar(resposta)
}