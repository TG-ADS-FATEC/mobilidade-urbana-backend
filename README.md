Para iniciar o projeto, utilize o comando docker compose up.

Caso deseje iniciar com o banco de dados de desenvolvedor, coloque docker compose --env-file .env.dev up

Caso deseje iniciar com o banco de dados de produção , coloque docker compose --env-file .env.prod up

Para finalizar e destruir os conteineres utilize docker compose down -v

Para parar a execução utilize docker compose stop
