## 1
O sistema tem como objetivo administrar um restaurante desde seu estoque e os produtos faltantes até a quantidade de produtos vendidos e o cadastro de clientes, ajudando na coleta de informações sobre venda, lucro líquido, valor em estoque e produtos em falta. Seu público alvo são donos de restaurantes que precisam de auxílio para organizar seu negócio e o objetivo principal é ajudar na coleta de informações e administração exibindo dados relacionados às operações feitas no estabelecimento

o sistema vai contar com cadastro de clientes, funcionários e gerentes; cadastro de itens, com informações que ajudem a gerenciar o estoque, além de ser cadastrado também o prato ofertado pelo estabelecimento, tendo assim um controle de consumo de produtos e itens em estoque, podendo ser cadastrado também o cardápio e a partir dele uma venda ser feita relacionando cliente, funcionário e prato do cardápio facilitando assim a visualização de quais pratos e produtos saem mais do estoque

## 2
REQUISITOS FUNCIONAIS

<table>
 <thead>
 <tr>
 <th>ID</th>
 <th>Requisito</th>
 <th>Descrição</th>
 </tr>
 </thead>
 <tbody>
 <tr>
 <td>RF01</td>
 <td>Autenticação de usuários</td>
 <td>O sistema deve permitir login via CPF e senha (endpoint <code>POST /login</code>), validando credenciais de funcionários e retornando identificação, nome, tipo (<code>FUNCIONARIO</code> ou <code>CLIENTE</code>) e cargo quando aplicável.</td>
 </tr>
 <tr>
 <td>RF02</td>
 <td>Redirecionamento por perfil</td>
 <td>Após login bem-sucedido, a interface deve direcionar o funcionário à tela correspondente ao seu cargo: Atendente, Gerente de Estoque, Gerente de Vendas ou Gerente Geral, restringindo o acesso operacional ao portal interno do restaurante.</td>
 </tr>
 <tr>
 <td>RF03</td>
 <td>Cadastro inicial do Gerente Geral</td>
 <td>O sistema deve permitir o cadastro do primeiro Gerente Geral por meio da tela dedicada, registrando nome, CPF, telefone, senha e cargo <code>GERENTE_GERAL</code> via <code>POST /gerente-geral/funcionarios</code>.</td>
 </tr>
 <tr>
 <td>RF04</td>
 <td>Cadastro de clientes</td>
 <td>O sistema deve permitir o cadastro de clientes (<code>POST /clientes</code>) com validação de nome, CPF (11 dígitos) e unicidade de CPF, armazenando também telefone e endereço quando informados.</td>
 </tr>
 <tr>
 <td>RF05</td>
 <td>Consulta de clientes</td>
 <td>O sistema deve disponibilizar a listagem de todos os clientes cadastrados (<code>GET /clientes</code>) para consulta durante o atendimento e apoio às vendas.</td>
 </tr>
 <tr>
 <td>RF06</td>
 <td>Registro de vendas (PDV)</td>
 <td>O atendente deve registrar vendas (<code>POST /atendente/vendas</code>) associando itens do cardápio, valor total, data/hora, indicador de delivery, cliente (opcional) e atendente responsável, com validação de valor positivo e ao menos um item.</td>
 </tr>
 <tr>
 <td>RF07</td>
 <td>Consulta de vendas</td>
 <td>O sistema deve permitir a consulta do histórico de vendas realizadas (<code>GET /atendente/vendas</code>), incluindo data, valor, itens, cliente e atendente vinculados.</td>
 </tr>
 <tr>
 <td>RF08</td>
 <td>Cadastro de produtos</td>
 <td>O Gerente de Estoque deve cadastrar produtos (<code>POST /gerente-estoque/produtos</code>) informando nome, quantidade inicial em estoque e preço unitário, com nome único no banco de dados.</td>
 </tr>
 <tr>
 <td>RF09</td>
 <td>Cadastro de ingredientes</td>
 <td>O Gerente de Estoque deve cadastrar ingredientes (<code>POST /gerente-estoque/ingredientes</code>) com nome, quantidade em estoque, unidade de medida e preço unitário, aplicando validações de campos obrigatórios e valores não negativos.</td>
 </tr>
 <tr>
 <td>RF10</td>
 <td>Reposição de estoque de produtos</td>
 <td>O sistema deve permitir a reposição da quantidade de um produto existente (<code>PUT /gerente-estoque/produtos/{id}/repor</code>), incrementando o estoque atual conforme a quantidade informada.</td>
 </tr>
 <tr>
 <td>RF11</td>
 <td>Registro de compras</td>
 <td>O sistema deve registrar entradas de compras de fornecedor (<code>POST /gerente-estoque/compras</code>), armazenando valor gasto, data da compra e listas de produtos e/ou ingredientes adquiridos.</td>
 </tr>
 <tr>
 <td>RF12</td>
 <td>Registro de perdas e ajustes de estoque</td>
 <td>O Gerente de Estoque deve registrar atualizações de estoque (<code>POST /gerente-estoque/atualizacoes</code>) com quantidade atual, quantidade de perda, motivo obrigatório quando houver perda e data da atualização.</td>
 </tr>
 <tr>
 <td>RF13</td>
 <td>Histórico de movimentações de estoque</td>
 <td>O sistema deve exibir o histórico de registros de estoque (<code>GET /gerente-estoque/historico</code>), permitindo acompanhamento de perdas, ajustes e datas das movimentações.</td>
 </tr>
 <tr>
 <td>RF14</td>
 <td>Cadastro de itens do cardápio</td>
 <td>O Gerente de Vendas deve cadastrar pratos/itens do cardápio (<code>POST /gerente-venda/pratos</code>) com nome, descrição e preço de venda, impedindo nomes duplicados.</td>
 </tr>
 <tr>
 <td>RF15</td>
 <td>Consulta do cardápio</td>
 <td>O sistema deve disponibilizar a listagem completa do cardápio (<code>GET /gerente-venda/cardapio</code>) para gestão de vendas e para o PDV do atendente.</td>
 </tr>
 <tr>
 <td>RF16</td>
 <td>Atualização de itens do cardápio</td>
 <td>O Gerente de Vendas deve poder alterar nome, descrição e preço de venda de um item existente (<code>PUT /gerente-venda/pratos/{id}</code>).</td>
 </tr>
 <tr>
 <td>RF17</td>
 <td>Exclusão de itens do cardápio</td>
 <td>O Gerente de Vendas deve poder remover itens do cardápio (<code>DELETE /gerente-venda/pratos/{id}</code>), com validação de existência do registro.</td>
 </tr>
 <tr>
 <td>RF18</td>
 <td>Contratação de funcionários</td>
 <td>O Gerente Geral deve contratar funcionários (<code>POST /gerente-geral/funcionarios</code>), registrando nome, CPF, telefone, senha (mínimo 6 caracteres) e cargo (<code>ATENDENTE</code>, <code>GERENTE_ESTOQUE</code>, <code>GERENTE_VENDAS</code> ou <code>GERENTE_GERAL</code>).</td>
 </tr>
 <tr>
 <td>RF19</td>
 <td>Consulta da equipe</td>
 <td>O Gerente Geral deve visualizar a equipe cadastrada (<code>GET /gerente-geral/funcionarios</code>), incluindo nome, CPF e cargo de cada funcionário.</td>
 </tr>
 <tr>
 <td>RF20</td>
 <td>Atualização de funcionários</td>
 <td>O Gerente Geral deve poder atualizar dados de funcionários existentes (<code>PUT /gerente-geral/funcionarios/{id}</code>), permitindo alteração de nome e senha.</td>
 </tr>
 <tr>
 <td>RF21</td>
 <td>Demissão de funcionários</td>
 <td>O Gerente Geral deve poder excluir funcionários do sistema (<code>DELETE /gerente-geral/funcionarios/{id}</code>), removendo seu acesso ao portal.</td>
 </tr>
 <tr>
 <td>RF22</td>
 <td>Relatório financeiro</td>
 <td>O sistema deve gerar relatório financeiro (<code>GET /relatorios/financeiro</code>) consolidando total de receitas (vendas), total de despesas (compras) e lucro líquido (receitas − despesas).</td>
 </tr>
 <tr>
 <td>RF23</td>
 <td>Relatório geral operacional</td>
 <td>O sistema deve gerar relatório geral (<code>GET /relatorios/geral</code>) com total de funcionários, total de clientes e lista de produtos com estoque abaixo do limite configurado (quantidade inferior a 10 unidades).</td>
 </tr>
 </tbody>
</table>

## 3
REQUISITOS NÃO FUNCIONAIS

<table>
 <thead>
 <tr>
 <th>ID</th>
 <th>Categoria</th>
 <th>Requisito</th>
 <th>Descrição</th>
 </tr>
 </thead>
 <tbody>
 <tr>
 <td>RNF01</td>
 <td>Tecnologia</td>
 <td>Stack Spring Boot</td>
 <td>O back-end deve ser implementado em Java 17 com Spring Boot (Web MVC e Spring Data JPA), expondo uma API REST consumida pela interface web do sistema.</td>
 </tr>
 <tr>
 <td>RNF02</td>
 <td>Tecnologia</td>
 <td>Persistência relacional</td>
 <td>Os dados devem ser persistidos em banco PostgreSQL, utilizando JPA/Hibernate com mapeamento orientado a objetos e geração automática de schema (<code>ddl-auto=update</code>).</td>
 </tr>
 <tr>
 <td>RNF03</td>
 <td>Manutenibilidade</td>
 <td>Arquitetura em camadas</td>
 <td>O sistema deve seguir arquitetura em camadas (Controller → Service → Repository), separando responsabilidades de exposição HTTP, regras de negócio e acesso a dados.</td>
 </tr>
 <tr>
 <td>RNF04</td>
 <td>Usabilidade</td>
 <td>Interface web por perfil</td>
 <td>A interface deve ser composta por páginas HTML estáticas com CSS e JavaScript, oferecendo telas específicas para login, atendimento, gestão de estoque, gestão de vendas e administração geral.</td>
 </tr>
 <tr>
 <td>RNF05</td>
 <td>Segurança</td>
 <td>Controle de acesso por cargo</td>
 <td>O controle de acesso por perfil deve ser aplicado na camada de apresentação via <code>sessionStorage</code>, impedindo que funcionários acessem telas de cargos distintos do seu.</td>
 </tr>
 <tr>
 <td>RNF06</td>
 <td>Segurança</td>
 <td>Autenticação por credenciais</td>
 <td>A autenticação de funcionários deve exigir CPF e senha válidos; tentativas inválidas devem retornar resposta HTTP 401 com mensagem de erro adequada.</td>
 </tr>
 <tr>
 <td>RNF07</td>
 <td>Confiabilidade</td>
 <td>Validação de regras de negócio</td>
 <td>Operações críticas (cadastros, vendas, movimentações de estoque e RH) devem validar dados obrigatórios e inconsistências nos services, retornando erros descritivos em caso de falha.</td>
 </tr>
 <tr>
 <td>RNF08</td>
 <td>Manutenibilidade</td>
 <td>Tratamento centralizado de exceções</td>
 <td>Exceções de negócio (<code>IllegalArgumentException</code>) devem ser tratadas de forma centralizada (<code>GlobalExceptionHandler</code>), retornando respostas JSON padronizadas com status HTTP 400.</td>
 </tr>
 <tr>
 <td>RNF09</td>
 <td>Integração</td>
 <td>Comunicação REST e CORS</td>
 <td>A comunicação entre front-end e back-end deve ocorrer via requisições HTTP/JSON (<code>fetch</code>), com suporte a CORS configurado para métodos GET, POST, PUT e DELETE.</td>
 </tr>
 <tr>
 <td>RNF10</td>
 <td>Manutenibilidade</td>
 <td>Modelagem orientada a objetos</td>
 <td>O domínio deve utilizar herança e composição (ex.: <code>Pessoa</code>, <code>Cardapio</code>/<code>Prato</code>, relacionamentos entre venda, cliente, atendente e itens), favorecendo reuso e extensibilidade conforme princípios de POO.</td>
 </tr>
 <tr>
 <td>RNF11</td>
 <td>Desempenho</td>
 <td>Operação em ambiente acadêmico</td>
 <td>O sistema deve operar de forma adequada em ambiente local (localhost), atendendo operações administrativas e de PDV de um restaurante de pequeno porte sem degradação perceptível nas telas implementadas.</td>
 </tr>
 </tbody>
</table>

## 4
DIAGRAMA DE CASO DE USO

<img width="2830" height="1944" alt="UseCase POO 2 (1)" src="https://github.com/user-attachments/assets/e4de839c-57dd-47e5-b4dc-ef9ab56bbe77" />

## 5
DIAGRAMA DE CLASSES

<img width="2496" height="1336" alt="Class Diagram0 (1)" src="https://github.com/user-attachments/assets/d01ee1d7-dd12-40a7-a966-00deec10f594" />

## 6
DIAGRAMA DE ATIVIDADES

<img width="766" height="898" alt="fluxodevendaeatendimento" src="https://github.com/user-attachments/assets/0ef4a60c-bded-468a-9294-fe2a0b133160" />
<br>
<img width="999" height="595" alt="gestapdevendaestoque" src="https://github.com/user-attachments/assets/891d6977-a679-4d5b-8a29-89cec3a8e4a7" />
<br>
<img width="1533" height="595" alt="fluxoadmerel" src="https://github.com/user-attachments/assets/0d5f472f-361c-4431-9ed4-b8bcec791177" />
