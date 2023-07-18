package br.com.forum.service

import br.com.forum.dto.UsuarioView
import br.com.forum.mapper.UsuarioViewMapper
import br.com.forum.model.Usuario
import br.com.forum.repository.UsuarioRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val repository : UsuarioRepository,
                     private val usuarioViewMapper: UsuarioViewMapper,
                     private val notFoundMessage: String = "Usuário não encontrado") {

    fun buscarPorId(id: Long): Usuario {
        return repository.findById(id).orElseThrow { EntityNotFoundException(notFoundMessage) }
    }

    fun listar(nome: String?, paginacao: Pageable): Page<UsuarioView> {
        val usuarios = if (nome == null) {
            repository.findAll(paginacao)
        } else {
            repository.findByNomeContainingIgnoreCase(nome, paginacao)
        }
        return usuarios.map { t -> usuarioViewMapper.map(t) }
    }

}