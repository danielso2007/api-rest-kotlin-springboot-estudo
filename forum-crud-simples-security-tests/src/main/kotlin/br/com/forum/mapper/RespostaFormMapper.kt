package br.com.forum.mapper

import br.com.forum.dto.NovaRespostaForm
import br.com.forum.model.Resposta
import br.com.forum.service.TopicoService
import br.com.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaFormMapper(
    private val topicoService: TopicoService, private val usuarioService: UsuarioService
) : Mapper<NovaRespostaForm, Resposta> {

    override fun map(t: NovaRespostaForm): Resposta {
        return Resposta(
            mensagem = t.mensagem,
            topico = topicoService.porId(t.idTopico),
            autor = usuarioService.buscarPorId(t.idAutor),
            solucao = t.solucao
        )
    }

}