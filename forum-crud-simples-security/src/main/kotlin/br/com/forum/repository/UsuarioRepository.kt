package br.com.forum.repository

import br.com.forum.model.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findByNome(nome: String, paginacao: Pageable): Page<Usuario>
    fun findByNomeContainingIgnoreCase(nome: String, paginacao: Pageable): Page<Usuario>
    fun findByEmail(username: String?): Usuario

}