package br.com.forum.service

import br.com.forum.dto.AtualizarTopicoForm
import br.com.forum.dto.NovoTopicoForm
import br.com.forum.dto.TopicoView
import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.TopicoFormMapper
import br.com.forum.mapper.TopicoViewMapper
import br.com.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val topicoFormMapper: TopicoFormMapper,
    private val topicoViewMapper: TopicoViewMapper,
    private val notFoundMessage: String = "Tópico não encontrado"
) {

    fun listar(): List<TopicoView> {
        return topicos.stream().map { t -> topicoViewMapper.map(t) }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): TopicoView {
        return topicos.stream().filter { t -> t.id == id }.findFirst().map { t -> topicoViewMapper.map(t) }
            .orElseThrow { NotFoundException(notFoundMessage) }
    }

    fun cadastrar(form: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(form)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(
            topico
        )
        return topicoViewMapper.map(topico)
    }

    fun atualizar(form: AtualizarTopicoForm): TopicoView {
        val topico = topicos.stream().filter { t -> t.id == form.id }.findFirst()
            .orElseThrow { NotFoundException(notFoundMessage) }
        val topicoAtualizado = Topico(
            id = form.id,
            titulo = form.titulo,
            mensagem = form.mensagem,
            curso = topico.curso,
            autor = topico.autor,
            status = topico.status,
            respostas = topico.respostas,
            dataCriacao = topico.dataCriacao
        )
        topicos = topicos.minus(topico).plus(topicoAtualizado);
        topicos = topicos.sortedBy { t -> t.id }
        return topicoViewMapper.map(topicoAtualizado)
    }

    fun deletar(id: Long) {
        val topico =
            topicos.stream().filter { t -> t.id == id }.findFirst().orElseThrow { NotFoundException(notFoundMessage) }
        topicos = topicos.minus(topico);
    }
}