create table curso (id bigint generated by default as identity, categoria varchar(255), nome varchar(255), primary key (id));

insert into curso(id, nome, categoria) values (1, 'Kotlin', 'Programação');
insert into curso(id, nome, categoria) values (2, 'Curso completo de Desenvolvimento Web (HTML, CSS, JavaScript)', 'Programação');
insert into curso(id, nome, categoria) values (3, 'Introdução à programação em Python', 'Programação');
insert into curso(id, nome, categoria) values (4, 'Curso de Desenvolvimento de Jogos com Unity', 'Programação');
insert into curso(id, nome, categoria) values (5, 'Aprendizado de Máquina e Inteligência Artificial', 'Programação');
insert into curso(id, nome, categoria) values (6, 'Desenvolvimento de aplicativos web com Node.js e Express', 'Programação');
insert into curso(id, nome, categoria) values (7, 'Curso de Desenvolvimento Web Front-End', 'Front-End');
insert into curso(id, nome, categoria) values (8, 'Curso Avançado de React.js', 'Front-End');
insert into curso(id, nome, categoria) values (9, 'Curso de UI/UX Design para Front-End', 'Front-End');
insert into curso(id, nome, categoria) values (10, 'Curso de Performance e Otimização Front-End', 'Front-End');
