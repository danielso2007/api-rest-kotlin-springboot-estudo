package br.com.forum.mapper

import br.com.forum.dto.CursoView
import br.com.forum.model.Curso
import org.springframework.stereotype.Component

@Component
class CursoViewMapper: Mapper<Curso, CursoView> {

    override fun map(t: Curso): CursoView {
        return CursoView(
            id = t.id,
            nome = t.nome,
            categoria = t.categoria
        )
    }

}