Para iniciar o projeto, utilize o comando docker compose up.

Caso deseje iniciar com o banco de dados de desenvolvedor, coloque docker compose --env-file .env.dev up

Caso deseje iniciar com o banco de dados de produção , coloque docker compose --env-file .env.prod up

Para finalizar e destruir os conteineres utilize docker compose down -v

Para parar a execução utilize docker compose stop


=================================================================================================
# Como rodar o projeto com Docker

## Pré-requisitos

Antes de tudo, você precisa ter instalado:

* Docker
* Docker Compose 

---

## Primeira vez (ou quando mudar o banco)

Execute:

```bash
docker compose down -v
docker compose up --build
```

### O que isso faz:

* Remove containers antigos
* Apaga o banco antigo
* Recria tudo do zero
* Executa o script `VersãoMaisRecenteDoBanco.sql`
* Sobe backend + banco conectados

---

## Rodar no dia a dia

Depois da primeira vez, basta:

```bash
docker compose up
```

### Isso:

* Sobe o backend
* Sobe o banco
* Mantém os dados

---

## Parar o projeto

```bash
docker compose down
```

---

## ⚠️ Quando usar `down -v` novamente?

Use SOMENTE se:

* alterou ou criou um novo script do banco
* deu erro estranho no banco
* quer resetar tudo
* quer apagar dados

---

## Acessos

* Backend: http://localhost:8080
* Banco: localhost:5432

---

## Observações importantes

* O banco só executa os scripts de `/docker/init` quando é criado pela primeira vez
* Se não usar `-v`, o banco antigo continua existindo
* Variáveis de ambiente vêm do `.env`
* O backend usa automaticamente as configurações do Docker

---

## Problemas comuns

### Erro de conexão com banco

-> Verifique se o `.env` está correto

### Script SQL não rodou

-> Rode:

```bash
docker compose down -v
docker compose up --build
```

### Porta já em uso

-> Feche outro projeto ou mude a porta

---

## Fluxo recomendado

Primeira vez:

```bash
docker compose down -v
docker compose up --build
```

Depois:

```bash
docker compose up
ou
docker compose up --build     
```

