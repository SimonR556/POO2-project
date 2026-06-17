let usuarioLogado = null;

let cardapio = [];
let clientes = [];

let carrinho = [];
let clienteAtual = null;

async function carregarCardapio() {
    const grid = document.getElementById('gridProdutosPDV');
    try {
        cardapio = await apiFetch('/gerente-venda/cardapio');
    } catch (error) {
        grid.innerHTML = '<div class="mensagem-vazia">Não foi possível carregar o cardápio.</div>';
        console.error('Erro ao carregar cardápio:', error);
        return;
    }

    grid.innerHTML = '';
    cardapio.forEach(item => {
        const btn = document.createElement('button');
        btn.className = 'btn-produto';
        btn.innerHTML = `
            <span class="produto-nome">${item.nome}</span>
            <span class="produto-preco">${formatarMoeda(item.precoVenda)}</span>
        `;
        btn.onclick = () => adicionarAoCarrinho(item);
        grid.appendChild(btn);
    });
}

async function carregarClientes() {
    try {
        clientes = await apiFetch('/clientes');
    } catch (error) {
        console.error('Erro ao carregar clientes:', error);
    }
}

function carregarListaClientes() {
    const tbody = document.getElementById('tabelaClientesCorpo');
    tbody.innerHTML = '';

    if (clientes.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3">Nenhum cliente cadastrado.</td></tr>';
        return;
    }

    clientes.forEach(cli => {
        tbody.innerHTML += `
            <tr>
                <td><strong>${cli.nome}</strong></td>
                <td>${cli.cpf}</td>
                <td>${cli.telefone || '-'}</td>
            </tr>
        `;
    });
}

function adicionarAoCarrinho(item) {
    carrinho.push(item);
    atualizarComanda();
}

function calcularTotal() {
    return carrinho.reduce((soma, item) => soma + item.precoVenda, 0);
}

function atualizarComanda() {
    const lista = document.getElementById('listaComanda');
    const totalVisor = document.getElementById('valorTotal');

    if (carrinho.length === 0) {
        lista.innerHTML = '<div class="mensagem-vazia">Nenhum item na comanda.</div>';
        totalVisor.innerText = formatarMoeda(0);
        return;
    }

    lista.innerHTML = '';
    carrinho.forEach(item => {
        lista.innerHTML += `
            <div class="item-comanda">
                <span>1x ${item.nome}</span>
                <span>${formatarMoeda(item.precoVenda)}</span>
            </div>
        `;
    });
    totalVisor.innerText = formatarMoeda(calcularTotal());
}

function vincularCliente() {
    const cpfDigitado = document.getElementById('buscaCpf').value.replace(/\D/g, '');
    if (cpfDigitado.length === 0) return;

    const cliente = clientes.find(c => c.cpf.replace(/\D/g, '') === cpfDigitado);

    if (!cliente) {
        alert('Cliente não encontrado. Cadastre-o antes de vincular à comanda.');
        return;
    }

    clienteAtual = cliente;
    document.getElementById('clienteAtivo').innerText = `Cliente: ${cliente.nome}`;
    document.getElementById('buscaCpf').value = '';
}

function abrirModalCliente() { document.getElementById('modalCliente').style.display = 'flex'; }
function abrirModalListaClientes() {
    carregarListaClientes();
    document.getElementById('modalListaClientes').style.display = 'flex';
}
function fecharModais() {
    document.getElementById('modalCliente').style.display = 'none';
    document.getElementById('modalListaClientes').style.display = 'none';
}

document.getElementById('formNovoCliente').addEventListener('submit', async (e) => {
    e.preventDefault();

    const nome = document.getElementById('novoNome').value;
    const cpf = document.getElementById('novoCpf').value.replace(/\D/g, '');
    const telefone = document.getElementById('novoTelefone').value.replace(/\D/g, '');

    try {
        const clienteCriado = await apiFetch('/clientes', {
            method: 'POST',
            body: JSON.stringify({ nome, cpf, telefone })
        });

        clientes.push(clienteCriado);

        document.getElementById('clienteAtivo').innerText = `Cliente: ${clienteCriado.nome}`;
        clienteAtual = clienteCriado;

        fecharModais();
        alert('Cliente cadastrado com sucesso e vinculado à comanda!');
        e.target.reset();
    } catch (error) {
        alert(error.message);
    }
});

async function finalizarVenda() {
    if (carrinho.length === 0) {
        alert('A comanda está vazia!');
        return;
    }

    const isDelivery = document.getElementById('checkDelivery').checked;

    const venda = {
        entregaDelivery: isDelivery,
        valorTotal: calcularTotal(),
        cardapioIds: carrinho.map(item => item.id),
        clienteId: clienteAtual ? clienteAtual.id : null,
        atendenteId: usuarioLogado.id || 1
    };

    try {
        await apiFetch('/atendente/vendas', {
            method: 'POST',
            body: JSON.stringify(venda)
        });

        alert(`Venda finalizada com sucesso!\nValor Total: ${formatarMoeda(venda.valorTotal)}\nDelivery: ${isDelivery ? 'Sim' : 'Não'}`);

        carrinho = [];
        clienteAtual = null;
        document.getElementById('clienteAtivo').innerText = 'Cliente Não Identificado';
        document.getElementById('checkDelivery').checked = false;
        atualizarComanda();
    } catch (error) {
        alert(error.message);
    }
}

document.getElementById('novoCpf').addEventListener('input', aplicarMascaraCpf);
document.getElementById('novoTelefone').addEventListener('input', aplicarMascaraTelefone);
document.getElementById('buscaCpf').addEventListener('input', aplicarMascaraCpf);

window.onload = () => {
    usuarioLogado = exigirAcesso(['ATENDENTE']);
    if (!usuarioLogado) return;

    document.getElementById('nomeAtendenteLogado').innerText = `Atendente: ${usuarioLogado.nome}`;

    carregarCardapio();
    carregarClientes();
};
