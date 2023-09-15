package br.com.forum.service

import br.com.forum.dto.CursoView
import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.CursoViewMapper
import br.com.forum.model.Curso
import br.com.forum.repository.CursoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CursoService(private val repository: CursoRepository,
                   private val cursoViewMapper: CursoViewMapper,
                   private val notFoundMessage: String = "Curso não encontrado") {

    fun buscarPorId(id: Long): Curso {
        return repository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
    }

    fun listar(nome: String?, paginacao: Pageable): Page<CursoView> {
        val cursos = if (nome == null) {
            repository.findAll(paginacao)
        } else {
            repository.findByNomeContainingIgnoreCase(nome, paginacao)
        }
        return cursos.map { t -> cursoViewMapper.map(t) }
    }

}