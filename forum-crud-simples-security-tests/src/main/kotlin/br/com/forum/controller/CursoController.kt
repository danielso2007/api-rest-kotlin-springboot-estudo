package br.com.forum.controller;

import br.com.forum.dto.CursoView
import br.com.forum.model.Curso
import br.com.forum.service.CursoService
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cursos")
class CursoController(private val service: CursoService) {

    @GetMapping
    @Cacheable("cursosCache")
    fun listar(@RequestParam(required = false) nome: String?,
               @PageableDefault(size = 5, sort = ["nome"], direction = Sort.Direction.ASC) paginacao: Pageable): Page<CursoView> {
        return service.listar(nome, paginacao);
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Curso {
        return service.buscarPorId(id)
    }

}
