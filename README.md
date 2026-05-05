## 1
O sistema tem como objetivo administrar um restaurante desde seu estoque e os produtos faltantes até a quantidade de produtos vendidos e o cadastro de clientes, ajudando na coleta de informações sobre venda, lucro líquido, valor em estoque e produtos em falta. Seu público alvo são donos de restaurantes que precisam de auxílio para organizar seu negócio e o objetivo principal é ajudar na coleta de informações e administração exibindo dados relacionados às operações feitas no estabelecimento

o sistema vai contar com cadastro de clientes, funcionários e gerentes; cadastro de itens, com informações que ajudem a gerenciar o estoque, além de ser cadastrado também o prato ofertado pelo estabelecimento, tendo assim um controle de consumo de produtos e itens em estoque, podendo ser cadastrado também o cardápio e a partir dele uma venda ser feita relacionando cliente, funcionário e prato do cardápio facilitando assim a visualização de quais pratos e produtos saem mais do estoque

## 2
REQUISITOS FUNCIONAIS

|identificador|Descrição|prioridade
|--|--|--|
|RF01|O sistema deve permitir a autenticação de usuários (gerente e funcionário).|ALTA
RF02|O sistema deve permitir o gerenciamento (consultar, criar, atualizar e excluir) de itens do estoque.|ALTA
RF03|O sistema deve permitir o registro de entradas de produtos no estoque (compras).|ALTA
RF04|O sistema deve permitir o gerenciamento (consultar, criar, atualizar e excluir) de clientes.|MÉDIA
RF05|O sistema deve permitir o gerenciamento (consultar, criar, atualizar e excluir) de funcionários, sendo esta funcionalidade restrita ao gerente.|ALTA
RF06|O sistema deve permitir o gerenciamento (consultar, criar, atualizar e excluir) de pratos.|MÉDIA
RF07 |O sistema deve permitir o gerenciamento (consultar, criar, atualizar e excluir) de preços dos produtos.|MÉDIA
RF08|O sistema deve permitir o gerenciamento (consultar, criar, atualizar e excluir) de itens do cardápio.|MÉDIA
RF09|O sistema deve permitir a realização de vendas, relacionando cliente, funcionário e itens do cardápio.|ALTA
RF10|O sistema deve permitir a visualização de relatórios de vendas por período (diário, semanal e mensal).|ALTA
RF11|O sistema deve permitir a visualização dos pratos mais vendidos.|ALTA
RF12|O sistema deve permitir a visualização de produtos mais consumidos do estoque.|ALTA
RF13|O sistema deve calcular e exibir o lucro obtido em determinado período.|ALTA
RF14|O sistema deve permitir a visualização do faturamento total por período.|ALTA
RF15|O sistema deve permitir a visualização de histórico de vendas realizadas.|MÉDIA
RF16|O sistema deve permitir a visualização de histórico de vendas realizadas.|MÉDIA
RF17|O sistema deve permitir a filtragem de relatórios por cliente, funcionário ou produto.|MÉDIA

## 3
REQUISITOS NÃO FUNCIONAIS

identificador|Descrição|categoria
|--|--|--|
RNF01|O sistema deve responder às requisições do usuário em no máximo 3 segundos.|Desempenho
RNF02|O sistema deve suportar no mínimo 2000 usuários simultâneos sem perda de desempenho.|Desempenho
RNF03|O sistema deve garantir a autenticação de usuários por meio de login e senha.|Segurança
RNF04|O sistema deve criptografar as senhas dos usuários armazenadas no banco de dados.|Segurança
RNF05|O sistema deve possuir controle de acesso baseado em níveis (gerente e funcionário).|Segurança
RNF06|O sistema deve estar disponível 99% do tempo durante o horário comercial.|Disponibilidade
RNF07|O sistema deve possuir interface intuitiva e de fácil uso para usuários não técnicos.|Usabilidade
RNF08|O sistema deve permitir fácil manutenção e atualização de código.|Manutenibilidade
RNF09|O sistema deve registrar logs das operações realizadas (cadastros, vendas, alterações).|Auditoria
RNF10|O sistema deve garantir a integridade dos dados armazenados no banco.|Confiabilidade
RNF11|O sistema deve realizar backup automático dos dados diariamente.|Confiabilidade
RNF12|O sistema deve ser escalável para suportar aumento no número de usuários e dados.|Escalabilidade
RNF13|"O sistema deve seguir padrões de desenvolvimento (ex: MVC, boas práticas de código)."|Padrão do projeto
RNF14|O sistema deve possuir tempo de aprendizado inferior a 2 horas para novos usuários.|Usabilidade

## 4
DIAGRAMA DE CASO DE USO

<img width="2830" height="1944" alt="UseCase POO 2 (1)" src="https://github.com/user-attachments/assets/e4de839c-57dd-47e5-b4dc-ef9ab56bbe77" />

## 5
DIAGRAMA DE CLASSES

<img width="2496" height="1336" alt="Class Diagram0 (1)" src="https://github.com/user-attachments/assets/d01ee1d7-dd12-40a7-a966-00deec10f594" />

## 6
DIAGRAMA DE ATIVIDADES

<img width="766" height="898" alt="fluxodevendaeatendimento" src="https://github.com/user-attachments/assets/0ef4a60c-bded-468a-9294-fe2a0b133160" />
<img width="999" height="595" alt="gestapdevendaestoque" src="https://github.com/user-attachments/assets/891d6977-a679-4d5b-8a29-89cec3a8e4a7" />
<img width="1533" height="595" alt="fluxoadmerel" src="https://github.com/user-attachments/assets/0d5f472f-361c-4431-9ed4-b8bcec791177" />
