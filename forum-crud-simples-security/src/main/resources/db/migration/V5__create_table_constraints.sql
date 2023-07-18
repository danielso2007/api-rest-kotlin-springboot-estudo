alter table if exists resposta add constraint FK_resposta_usuario_autor_id foreign key (autor_id) references usuario;
alter table if exists resposta add constraint FK_resposta_topico_topico_id foreign key (topico_id) references topico;
alter table if exists topico add constraint FK_topico_auto_id foreign key (autor_id) references usuario;
alter table if exists topico add constraint FK_topico_curso_id foreign key (curso_id) references curso;