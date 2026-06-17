let itensEstoque = [];

async function carregarEstoque() {
    let produtos = [];
    let ingredientes = [];

    try {
        produtos = await apiFetch('/gerente-estoque/produtos');
    } catch (error) {
        console.error('Erro ao carregar produtos:', error);
    }

    try {
        ingredientes = await apiFetch('/gerente-estoque/ingredientes');
    } catch (error) {
        console.error('Erro ao carregar ingredientes:', error);
    }

    itensEstoque = [
        ...produtos.map(p => ({
            id: p.id,
            nome: p.nome,
            tipo: 'PRODUTO',
            quantidade: p.estoqueAtual,
            unidade: 'UN',
            precoUnico: p.precoUnico
        })),
        ...ingredientes.map(i => ({
            id: i.id,
            nome: i.nome,
            tipo: 'INGREDIENTE',
            quantidade: i.quantidadeEstoque,
            unidade: i.unidadeMedida,
            precoUnico: i.precoUnico
        }))
    ];

    renderizarTabelaEstoque();
}

function renderizarTabelaEstoque() {
    const tbody = document.getElementById('tabelaEstoque');
    tbody.innerHTML = '';

    if (itensEstoque.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4">Nenhum item cadastrado ainda.</td></tr>';
        return;
    }

    itensEstoque.forEach((item) => {
        const tagClasse = item.tipo === 'INGREDIENTE' ? 'tag-ingrediente' : 'tag-produto';
        const tagTexto = item.tipo === 'INGREDIENTE' ? 'Ingrediente' : 'Produto';
        const baixaClasse = item.quantidade < 10 ? 'qtd-baixa' : '';

        tbody.innerHTML += `
            <tr>
                <td><strong>${item.nome}</strong></td>
                <td><span class="tag-tipo ${tagClasse}">${tagTexto}</span></td>
                <td class="${baixaClasse}">${item.quantidade} ${item.unidade}</td>
                <td>
                    <button class="btn-acao acao-repor" onclick="abrirModalMovimento(${item.id}, '${item.tipo}', 'ENTRADA')" title="Registrar Entrada/Compra">📥</button>
                    <button class="btn-acao acao-demitir" onclick="abrirModalMovimento(${item.id}, '${item.tipo}', 'SAIDA')" title="Registrar Perda/Ajuste">📤</button>
                </td>
            </tr>
        `;
    });
}

async function carregarHistorico() {
    const tbody = document.getElementById('tabelaHistorico');
    let historico = [];

    try {
        historico = await apiFetch('/gerente-estoque/historico');
    } catch (error) {
        console.error('Erro ao carregar histórico:', error);
    }

    tbody.innerHTML = '';

    if (historico.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5">Nenhum registro de ajuste até o momento.</td></tr>';
        return;
    }

    historico
        .slice()
        .sort((a, b) => new Date(b.dataUltimaAtualizacao) - new Date(a.dataUltimaAtualizacao))
        .forEach((registro) => {
            const data = registro.dataUltimaAtualizacao
                ? new Date(registro.dataUltimaAtualizacao).toLocaleString('pt-BR')
                : '-';

            tbody.innerHTML += `
                <tr>
                    <td><strong>${registro.nomeItem}</strong></td>
                    <td>${registro.quantidadeAtual}</td>
                    <td>${registro.quantidadePerda}</td>
                    <td>${registro.motivoPerda || '-'}</td>
                    <td>${data}</td>
                </tr>
            `;
        });
}

function alternarUnidadeBase() {
    const tipo = document.getElementById('baseTipo').value;
    document.getElementById('grupoUnidadeBase').style.display = tipo === 'INGREDIENTE' ? 'flex' : 'none';
}

function abrirModalBase() {
    document.getElementById('formBase').reset();
    document.getElementById('grupoUnidadeBase').style.display = 'none';
    document.getElementById('modalBase').style.display = 'flex';
}

document.getElementById('formBase').addEventListener('submit', async (e) => {
    e.preventDefault();

    const nome = document.getElementById('baseNome').value;
    const tipo = document.getElementById('baseTipo').value;
    const precoUnico = moedaParaNumero(document.getElementById('basePreco').value);

    let payload;
    let endpoint;

    if (tipo === 'PRODUTO') {
        endpoint = '/gerente-estoque/produtos';
        payload = { nome, estoqueAtual: 0, precoUnico };
    } else {
        endpoint = '/gerente-estoque/ingredientes';
        payload = {
            nome,
            quantidadeEstoque: 0,
            unidadeMedida: document.getElementById('baseUnidade').value,
            precoUnico
        };
    }

    try {
        await apiFetch(endpoint, { method: 'POST', body: JSON.stringify(payload) });
        alert('Item cadastrado com sucesso! Use o botão de Entrada (📥) para registrar a primeira compra.');
        fecharModais();
        await carregarEstoque();
    } catch (error) {
        alert(error.message);
    }
});

let movimentoAtual = { id: null, tipo: null, operacao: null };

function abrirModalMovimento(id, tipo, operacao) {
    const item = itensEstoque.find(i => i.id === id && i.tipo === tipo);
    if (!item) return;

    movimentoAtual = { id, tipo, operacao };

    document.getElementById('formMovimento').reset();
    document.getElementById('movUnidadeTexto').innerText = item.unidade;
    document.getElementById('tituloMovimento').innerText = operacao === 'ENTRADA'
        ? `Registrar Entrada — ${item.nome}`
        : `Registrar Perda/Ajuste — ${item.nome}`;

    document.getElementById('divValorCompra').style.display = operacao === 'ENTRADA' ? 'flex' : 'none';
    document.getElementById('divMotivoPerda').style.display = operacao === 'SAIDA' ? 'flex' : 'none';

    document.getElementById('modalMovimento').style.display = 'flex';
}

document.getElementById('formMovimento').addEventListener('submit', async (e) => {
    e.preventDefault();

    const quantidadeDigitada = parseFloat(document.getElementById('movQuantidade').value);

    const { id, tipo, operacao } = movimentoAtual;

    const item = itensEstoque.find(i => i.id === id && i.tipo === tipo);

    if (!item) {
        alert("Erro: Item não encontrado na memória.");
        return;
    }

    try {
        if (operacao === 'ENTRADA') {

            let urlRepor = item.tipo === 'PRODUTO'
                ? `/gerente-estoque/produtos/${item.id}/repor?quantidade=${quantidadeDigitada}`
                : `/gerente-estoque/ingredientes/${item.id}/repor?quantidade=${quantidadeDigitada}`;

            await apiFetch(urlRepor, { method: 'PUT' });

            let inputValor = document.getElementById('movValorTotal');
            let valorTexto = inputValor ? inputValor.value.replace(/\D/g, '') : '';
            let valorGasto = valorTexto ? (parseFloat(valorTexto) / 100) : (quantidadeDigitada * item.precoUnico);

            const bodyCompra = {
                valorGasto: valorGasto,
                produtosComprados: item.tipo === 'PRODUTO' ? [item.nome] : [],
                ingredientesComprados: item.tipo === 'INGREDIENTE' ? [item.nome] : []
            };

            await apiFetch('/gerente-estoque/compras', {
                method: 'POST',
                body: JSON.stringify(bodyCompra)
            });

            alert('Movimentação de entrada e despesa registradas com sucesso!');

        } else {

            let urlBaixa = item.tipo === 'PRODUTO'
                ? `/gerente-estoque/produtos/${item.id}/baixa?quantidade=${quantidadeDigitada}`
                : `/gerente-estoque/ingredientes/${item.id}/baixa?quantidade=${quantidadeDigitada}`;

            await apiFetch(urlBaixa, { method: 'PUT' });

            let inputMotivo = document.getElementById('movMotivo');

            let motivoTexto = (inputMotivo && inputMotivo.value) ? inputMotivo.value : "Baixa/Desperdício manual via painel";

            const histBody = {
                nomeItem: item.nome,
                quantidadeAtual: item.quantidade - quantidadeDigitada,
                quantidadePerda: quantidadeDigitada,
                motivoPerda: motivoTexto
            };

            await apiFetch('/gerente-estoque/atualizacoes', {
                method: 'POST',
                body: JSON.stringify(histBody)
            });

            alert('Baixa registrada no estoque e no histórico!');
        }

        fecharModais();

        await carregarEstoque();
        await carregarHistorico();

    } catch (err) {
        alert("Erro na movimentação: " + err.message);
    }
});

document.getElementById('movValorTotal').addEventListener('input', aplicarMascaraMoeda);
document.getElementById('basePreco').addEventListener('input', aplicarMascaraMoeda);

function fecharModais() {
    const modalBase = document.getElementById('modalBase');
    const modalMov = document.getElementById('modalMovimento');

    if (modalBase) modalBase.style.display = 'none';
    if (modalMov) modalMov.style.display = 'none';
}

window.onload = () => {
    const usuario = exigirAcesso(['GERENTE_ESTOQUE']);
    if (!usuario) return;

    document.getElementById('nomeGerenteLogado').innerText = usuario.nome;

    carregarEstoque();
    carregarHistorico();
};
