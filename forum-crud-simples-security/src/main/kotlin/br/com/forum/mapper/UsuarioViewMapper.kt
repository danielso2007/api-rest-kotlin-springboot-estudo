package br.com.forum.mapper

import br.com.forum.dto.UsuarioView
import br.com.forum.model.Usuario
import org.springframework.stereotype.Component

@Component
class UsuarioViewMapper: Mapper<Usuario, UsuarioView> {

    override fun map(t: Usuario): UsuarioView {
        return UsuarioView(
            id = t.id,
            nome = t.nome,
            email = t.email
        )
    }

}