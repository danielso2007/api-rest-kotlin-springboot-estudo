# Projeto com Spring Security

Apenas estudo com spring-boot-starter-security.

# Console h2

jdbc URL: jdbc:h2:mem:forum

user: sa

password:

http://localhost:8080/h2-console

# Heroku
```
curl https://cli-assets.heroku.com/install.sh | sh
```
```
heroku login
git init
heroku git:remote -a forum-teste-daniel
heroku container:login
heroku container:push web
heroku container:release web
```

# Banco de dados

Usando o postgresql. Dentro da pastas `postgresql`:

- `start.sh`: Inicia o container 
- `stop.sh`: Para o container

## Outros dados do banco:
- Endereço: jdbc:postgresql://localhost:5432/forum
- username: forum
- password: forum

## Interface web para gerenciamento do banco:

[pgAdmin4](http://localhost:5050/browser/)

# Test

- Usando https://mockk.io/
- Usando https://joel-costigliola.github.io/assertj/

