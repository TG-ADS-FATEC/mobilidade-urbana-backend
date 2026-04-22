Comandos para subir o docker:

Primeiro use docker compose build --no-cache para uma instalação limpa

Depois use docker compose --env-file .env.dev para iniciar no perfil de desenvolvedor

Ou use docker compose --env-file .env.prod para iniciar

Para destruir os containeres utilize docker compose down -v

Para para os containeres utilize docker compose stop

Para inicializar os containeres(se você não os destruiu) utilize docker compose start

Não se esqueça de ter iniciado o docker e preenchido os campos que estão no .env.example .env.dev.example e .envprod.example
