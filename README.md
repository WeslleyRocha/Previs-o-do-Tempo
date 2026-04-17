# 🌤️ Previsão do Tempo App

Este projeto é uma aplicação completa (Full-Stack) de previsão do tempo, construída do zero com integração a serviços externos de geolocalização e clima. A aplicação permite que o usuário digite o nome de qualquer cidade e veja, em tempo real, as condições climáticas com uma interface moderna e interativa.

**⚠️ IMPORTANTE:** Este projeto foi **100% desenvolvido utilizando Inteligência Artificial através do Google Gemini**. Toda a estrutura de Backend, Frontend, testes, lógicas de negócios e estilização CSS foi gerada pela IA a partir de comandos (prompts) detalhados fornecidos durante o desenvolvimento.

Este aplicativo foi construído como parte de um projeto de entrega para o curso da **Generation**, cujo objetivo e foco principal era a criação e desenvolvimento de aplicações utilizando ferramentas de Inteligência Artificial.

---

## 🎥 Demonstração (App em Funcionamento)

https://github.com/user-attachments/assets/8c8802fe-0eb7-491d-9495-9ce20487feff

---

## 🚀 Funcionalidades

- **Busca por Cidade:** O usuário digita o nome da cidade e o sistema busca os dados climáticos.
- **Background Dinâmico:** A imagem de fundo do site muda dinamicamente de acordo com o clima sorteado/retornado (Sol, Chuva, Vento, Nublado, Raios, Trovões).
- **Interface Premium (Glassmorphism):** Design moderno com efeito de vidro fosco, sombras suaves e componentes perfeitamente alinhados e centralizados.
- **Tratamento de Erros:** Exibe mensagens amigáveis caso a cidade não seja encontrada.
- **Responsividade:** Layout adaptável para dispositivos móveis e desktops.

---

## 🛠️ Tecnologias Utilizadas

### Backend (Java)
- **Java 11+:** Linguagem principal do backend.
- **`java.net.http.HttpClient`:** Cliente HTTP nativo do Java para fazer as requisições assíncronas/síncronas para a API externa.
- **Gson (Google):** Biblioteca para serialização e desserialização de objetos JSON.
- **JUnit 5 & Mockito:** Frameworks utilizados para criar testes unitários automatizados, garantindo a qualidade e segurança do código com o uso de Mocks para simular respostas da API.
- **Boas Práticas de Segurança:** Implementação de `URLEncoder` para prevenir URL Injection e criação de Exceções Customizadas (`ApiClientException`, `ResourceNotFoundException`) para não vazar detalhes internos da arquitetura.

### Frontend (Angular)
- **Angular (Framework):** Framework TypeScript utilizado para construir a interface de usuário de forma componentizada e reativa (Single Page Application).
- **Bootstrap 5 & Bootstrap Icons:** Framework de CSS utilizado para acelerar o desenvolvimento do layout, usando seu sistema de grids (`row`, `col`), utilitários de flexbox e ícones.
- **HTML5 & CSS3 Avançado:** Estruturação da página e estilização customizada, incluindo animações de *fade-in*, *Glassmorphism* (`backdrop-filter`), transições suaves e posicionamento absoluto.

---

## 🔌 Integrações e APIs Externas

O Backend da aplicação se comunica com a **API pública do Open-Meteo**, dividida em duas etapas de integração (orquestradas pelo Java):

1. **Geocoding API:** A API de clima não aceita busca diretamente por nome de cidade. Por isso, usamos o `GeocodingClient` para consultar o nome da cidade e obter suas coordenadas exatas (Latitude e Longitude).
2. **Weather Forecast API:** Com as coordenadas em mãos, o `WeatherClient` faz uma segunda requisição para obter os dados climáticos atuais (Temperatura, Vento, Direção, etc).

---

## 🤖 O Papel da Inteligência Artificial (Google Gemini)

O desenvolvimento deste software foi uma experiência focada na interação com IA. O fluxo de trabalho consistiu em:

1. **Engenharia de Prompts:** Descrição clara das necessidades, regras de negócio e como a interface deveria parecer visualmente.
2. **Geração de Código:** A IA (Gemini) escreveu todas as classes Java, configurou o `pom.xml`, criou as rotas HTTP, e montou toda a base do Angular (`app.ts`, `app.html`, `app.css`).
3. **Refatoração e Testes:** Através de conversas contínuas, a IA aplicou conceitos de Injeção de Dependências, melhorou a segurança da aplicação, corrigiu problemas de compatibilidade de bibliotecas (como o ByteBuddy no Mockito) e elaborou cenários de testes completos.
4. **UI/UX Design:** A IA ajustou o layout de acordo com o feedback visual solicitado, aplicando regras de CSS modernas (como *Glassmorphism*), alinhamentos complexos no eixo X e Y e manipulando o DOM do Angular de forma segura via `DomSanitizer`.

---

## 👨‍💻 Como executar o projeto

### Rodando o Frontend (Angular)
1. Instale o [Node.js](https://nodejs.org/).
2. Navegue até a pasta do frontend: `cd previsao-tempo-web`
3. Instale as dependências: `npm install`
4. Rode o servidor de desenvolvimento: `ng serve -o`
5. O navegador abrirá automaticamente em `http://localhost:4200`.

### Rodando os Testes do Backend (Java)
1. Certifique-se de ter o Maven e a JDK instalados.
2. Na raiz do projeto backend, execute: `mvn clean test`

---
*Projeto desenvolvido para o curso da Generation focado em Inteligência Artificial.*
