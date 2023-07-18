package br.com.forum.service

import br.com.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class UsuarioService(private var usuarios : List<Usuario>) {

    init {
        val usuario = Usuario (
            id = 1,
            nome = "daniel",
            email = "daniel@gmail.com"
        )
        usuarios = listOf(usuario)
    }

    fun buscarPorId(id: Long): Usuario {
        return usuarios.stream().filter { t -> t.id == id}.findFirst().get()
    }

}