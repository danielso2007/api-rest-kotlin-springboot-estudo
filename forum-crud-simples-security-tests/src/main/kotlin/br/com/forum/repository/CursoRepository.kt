package br.com.forum.repository

import br.com.forum.model.Curso
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long> {
    fun findByNome(nomeCurso: String, paginacao: Pageable): Page<Curso>
    fun findByNomeContainingIgnoreCase(nomeCurso: String, paginacao: Pageable): Page<Curso>

}