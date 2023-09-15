package br.com.forum.controller;

import br.com.forum.dto.AtualizarTopicoForm
import br.com.forum.dto.NovoTopicoForm
import br.com.forum.dto.TopicoPorCategoriaDto
import br.com.forum.dto.TopicoView
import br.com.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/v1/topicos")
class TopicoController(private val service: TopicoService) {

    @GetMapping
    @Cacheable("topicosCache")
    fun listar(@RequestParam(required = false) nomeCurso: String?,
               @PageableDefault(size = 5, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable): Page<TopicoView> {
        return service.listar(nomeCurso, paginacao);
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicosCache"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid form: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView = service.cadastrar(form)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri();
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicosCache"], allEntries = true)
    fun atualizar(
        @RequestBody @Valid form: AtualizarTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView = service.atualizar(form)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri();
        return ResponseEntity.created(uri).body(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = ["topicosCache"], allEntries = true)
    fun deletar(@PathVariable id: Long) {
        service.deletar(id);
    }

    @GetMapping("/relatorio")
    @Cacheable("topicosRelatoriosCache")
    fun relatorio(): List<TopicoPorCategoriaDto> {
        return service.relatorio()
    }
}
