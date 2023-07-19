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