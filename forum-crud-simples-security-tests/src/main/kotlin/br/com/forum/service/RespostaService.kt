package br.com.forum.service

import br.com.forum.dto.NovaRespostaForm
import br.com.forum.mapper.RespostaFormMapper
import br.com.forum.model.Resposta
import br.com.forum.model.StatusTopico
import br.com.forum.repository.RespostaRepository
import org.springframework.stereotype.Service

@Service
class RespostaService(
    private val respostaRepository: RespostaRepository,
    private val emailService: EmailService,
    private val respostaFormMapper: RespostaFormMapper
) {

    fun salvar(resposta: Resposta) {
        respostaRepository.save(resposta)
        val emailAutor = resposta.topico.autor.email
        emailService.notificar(emailAutor)
    }

    fun salvar(novaRespostaForm: NovaRespostaForm) {
        val resposta = respostaFormMapper.map(novaRespostaForm)
        if (novaRespostaForm.solucao) {
            resposta.topico.status = StatusTopico.SOLUCIONADO
        } else {
            resposta.topico.status = StatusTopico.NAO_SOLUCIONADO
        }
        respostaRepository.save(resposta)
        val emailAutor = resposta.topico.autor.email
        emailService.notificar(emailAutor)
    }
}