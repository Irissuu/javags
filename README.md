# 💧 FlowGuard

FlowGuard é um sistema completo de monitoramento de alagamentos baseado em IoT, com um backend em .NET e banco Oracle, além de um aplicativo mobile integrado. O sistema permite que cidadãos registrem ocorrências em tempo real e que regiões críticas sejam monitoradas por gestores públicos.

---

##  Sumário

- Integrantes
- Vídeos
-  Tecnologias Utilizadas
- nstruções de Execução e Testes
-  Rotas Disponíveis (via Swagger)
-  Exemplos de JSON para teste
-  Considerações Finais e Impacto Social

---

## 👥 Integrantes

- **Iris Tavares Alves** - 557728 - 2TDSPM  
- **Taís Tavares Alves** - 557553 - 2TDSPM

---

## 🎥 Vídeos

> <a href="">Vídeo pitch</a> </br>
> <a href="">Vídeo demosntração</a>

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia/Componente | Função |
|------------------------|--------|
| Java + Spring Boot     | Backend RESTful com boas práticas |
| Oracle SQL             | Banco de dados relacional (modelo 3FN) |
| Spring Data JPA        | Mapeamento das entidades e persistência |
| JWT                    | Autenticação segura de usuários |
| Swagger                | Testes e documentação da API |
| Docker (VM Azure)      | Deploy da aplicação com container |
| React Native           | App mobile integrado via API |

---

## 🔎 Instruções de Execução e Testes

### 1. Clone o repositório
```bash
git clone 
```

### 2. Configure o application.properties
```text
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
```

### 3. Acesse o Swagger
``` text
http://localhost:8080/swagger-ui/index.html
```
---

## 🔁 Rotas Disponíveis (via Swagger)

### 🔹 AuthController

| Método | Rota              | Descrição                           |
|--------|-------------------|--------------------------------------|
| POST   | `/auth/login`     | Realiza login e retorna token JWT   |
| POST   | `/auth/register`  | Cadastra um novo usuário com criptografia de senha |

### 🔹 UsuarioController

| Método | Rota                | Descrição                              |
|--------|---------------------|-----------------------------------------|
| GET    | `/usuarios`         | Lista todos os usuários (paginado)      |
| GET    | `/usuarios/{id}`    | Retorna um usuário pelo ID              |
| PUT    | `/usuarios/{id}`    | Atualiza os dados do usuário            |
| DELETE | `/usuarios/{id}`    | Exclui um usuário pelo ID               |

### 🔹 RegiaoController

| Método | Rota                | Descrição                                  |
|--------|---------------------|---------------------------------------------|
| GET    | `/regioes`          | Lista todas as regiões (paginado)           |
| GET    | `/regioes/{id}`     | Retorna uma região pelo ID                  |
| POST   | `/regioes`          | Cadastra uma nova região                    |
| PUT    | `/regioes/{id}`     | Atualiza os dados de uma região             |
| DELETE | `/regioes/{id}`     | Exclui uma região pelo ID                   |

---

## 📄 Exemplos de JSON para teste 

### POST /auth/register
```json
{
  "nome": "Carla Mendes",
  "cpf": 18219822821,
  "email": "carla@gmail.com",
  "senha": "Doritos",
  "role": "ADMIN"
}
```

### PUT /api/usuario/{id}
```json
{
  "nome": "Carla Mendes",
  "email": "carlamendes@gmail.com",
  "senha": "BatataDoce"
}
```

### POST /auth/login
```json
{
  "email": "carla@gmail.com",
  "senha": "BatataDoce"
}
```

### POST /api/regiao
```json
{
  "estado": "SP",
  "distrito": "Itaquera"
}

```

---

### Considerações Finais e Impacto Social
O FlowGuard é uma solução simples, eficaz e de baixo custo que ajuda a prevenir alagamentos com monitoramento em tempo real. Combinando sensores e conectividade, ele protege vidas e torna as cidades mais seguras.
