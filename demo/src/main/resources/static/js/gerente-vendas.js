// gerente-vendas.js
// Gestão de Cardápio integrada com /gerente-venda/* do back-end.
// Observação: o controller trabalha com a classe Cardapio (nome, descricao,
// precoVenda); por isso o item criado aqui não tem receita/ingredientes
// associados (isso pertence à subclasse Prato, não exposta nesta tela).

let cardapio = [];

// --- 1. Carregar e Renderizar a Tabela ---
async function carregarCardapio() {
    try {
        cardapio = await apiFetch('/gerente-venda/cardapio');
    } catch (error) {
        console.error('Erro ao carregar cardápio:', error);
        cardapio = [];
    }
    renderizarTabelaCardapio();
}

function renderizarTabelaCardapio() {
    const tbody = document.getElementById('tabelaCardapio');
    tbody.innerHTML = '';

    if (cardapio.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4">Nenhum item cadastrado no cardápio.</td></tr>';
        return;
    }

    cardapio.forEach((item) => {
        tbody.innerHTML += `
            <tr>
                <td><strong>${item.nome}</strong></td>
                <td style="color: #8E6B4E; font-size: 13px;">${item.descricao || ''}</td>
                <td style="font-weight: 600; color: #D47E30;">${formatarMoeda(item.precoVenda)}</td>
                <td>
                    <button class="btn-acao acao-editar" onclick="abrirModalEditar(${item.id})" title="Editar Preço/Item">✏️</button>
                    <button class="btn-acao acao-demitir" onclick="excluirPrato(${item.id})" title="Remover do Cardápio">🗑️</button>
                </td>
            </tr>
        `;
    });
}

// --- 2. Controle do Modal ---
function abrirModalNovoPrato() {
    document.getElementById('formPrato').reset();
    document.getElementById('editIndex').value = '';
    document.getElementById('tituloModalPrato').innerText = 'Cadastrar Novo Item';
    document.getElementById('modalPrato').style.display = 'flex';
}

function abrirModalEditar(id) {
    const item = cardapio.find(c => c.id === id);
    if (!item) return;

    document.getElementById('pratoNome').value = item.nome;
    document.getElementById('pratoDescricao').value = item.descricao || '';

    // Formata o preço de volta para o input (em centavos, para a máscara)
    document.getElementById('pratoPreco').value = Math.round(item.precoVenda * 100).toString();
    aplicarMascaraMoeda({ target: document.getElementById('pratoPreco') });

    document.getElementById('editIndex').value = item.id;
    document.getElementById('tituloModalPrato').innerText = 'Editar Item';
    document.getElementById('modalPrato').style.display = 'flex';
}

function fecharModalPrato() {
    document.getElementById('modalPrato').style.display = 'none';
}

document.getElementById('formPrato').addEventListener('submit', async (e) => {
    e.preventDefault();


    const id = document.getElementById('editIndex').value;

    const item = {
        nome: document.getElementById('pratoNome').value,
        descricao: document.getElementById('pratoDescricao').value,
        precoVenda: moedaParaNumero(document.getElementById('pratoPreco').value),
        itemReceitaList: []
    };

    try {
        if (id === '') {
            await apiFetch('/gerente-venda/pratos', {
                method: 'POST',
                body: JSON.stringify(item)
            });
            alert('Item adicionado ao cardápio com sucesso!');
        } else {
            await apiFetch(`/gerente-venda/pratos/${id}`, {
                method: 'PUT',
                body: JSON.stringify(item)
            });
            alert('Prato atualizado com sucesso!');
        }

        fecharModalPrato();
        await carregarCardapio();
    } catch (error) {
        alert(error.message);
    }
});

async function excluirPrato(id) {
    const item = cardapio.find(c => c.id === id);
    if (!item) return;

    const confirmar = confirm(`Tem certeza que deseja remover "${item.nome}" do cardápio?`);
    if (!confirmar) return;

    try {
        await apiFetch(`/gerente-venda/pratos/${id}`, { method: 'DELETE' });
        await carregarCardapio();
    } catch (error) {
        alert(error.message);
    }
}

document.getElementById('pratoPreco').addEventListener('input', aplicarMascaraMoeda);

window.onload = () => {
    const usuario = exigirAcesso(['GERENTE_VENDAS']);
    if (!usuario) return;

    document.getElementById('nomeGerenteLogado').innerText = usuario.nome;
    carregarCardapio();
};
