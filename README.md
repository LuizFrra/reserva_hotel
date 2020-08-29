  Aplicação foi construída com ReactJS e Spring Boot, a aplicação consiste de um site para o usuário fazer reservas e comparas de passagems aéreas para um determinado destino pré-cadastrado no sistema.

  ### `Hotel API`
  A implementação da API responsável pela a interação dos hotéis, ou seja, busca de hotéis, reserva de quartos, busca de disponibilidades e etc.
  Para a iteração com o banco de dados foi utilizado o JdbcTemplate, sendo o mesmo injetado pelo spring nos DAO, também possui endpoits para cadastro e consultas de cidades.
 
 ### `Voo API`
 A implementação da API responsável pela iteração dos vôos, muito semelhante a API de hotéis.
 Para a iteração com o banco de dados foi utilizando o SPRING JPA.
 
 ### `Hotel/Voo Front`
 Implementação do frontend, onde foi utilizada a biblioteca de UI antDesign.
 
 ### `Banco de Dados`
 Para a api do hotel, dentro da pasta é possível encontrar um arquivo hotelSchema, neste arquivo tem o código SQL para a geração das tabelas, para a api de VOO, como utiliza o spring JPA, as tabelas são geradas no inicio da aplicação.
 
 ### `como Executar APIs`
 Foi utilizado o maven junto com o spring, portanto basta apenas executar o comando : mvn spring-boot:run 
 
 ### `Como executar frontend`
 Foi utilizado o yarn como gerenciador, portanto basta primeiro executar o comando yarn install seguido de yarn start, a aplicação iniciará na porta 3000.
 
