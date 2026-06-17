let usuarioLogado = null;
let equipe = [];

const NOMES_CARGOS = {
    'ATENDENTE': 'Atendente (Caixa)',
    'GERENTE_VENDAS': 'Ger. Vendas',
    'GERENTE_ESTOQUE': 'Ger. Estoque',
    'GERENTE_GERAL': 'Gerente Geral'
};

async function carregarDashboard() {
    try {
        const dadosFin = await apiFetch('/relatorios/financeiro');
        document.getElementById('dashLucro').innerText = formatarMoeda(dadosFin.lucroLiquido);
        document.getElementById('dashReceita').innerText = formatarMoeda(dadosFin.totalVendas);
        document.getElementById('dashDespesa').innerText = formatarMoeda(dadosFin.totalDespesas);
    } catch (error) {
        console.error('Erro ao carregar relatório financeiro:', error);
    }

    try {
        const dadosGer = await apiFetch('/relatorios/geral');
        document.getElementById('dashClientes').innerText = dadosGer.totalClientes;
        document.getElementById('dashEquipe').innerText = dadosGer.totalFuncionarios;

        const qtdBaixa = dadosGer.produtosEstoqueBaixo ? dadosGer.produtosEstoqueBaixo.length : 0;
        document.getElementById('dashEstoque').innerText = `${qtdBaixa} itens`;
    } catch (error) {
        console.error('Erro ao carregar relatório geral:', error);
    }
}

async function carregarEquipe() {
    try {
        equipe = await apiFetch('/gerente-geral/funcionarios');
    } catch (error) {
        console.error('Erro ao carregar a equipe:', error);
        equipe = [];
    }
    renderizarTabelaEquipe();
}

function renderizarTabelaEquipe() {
    const tbody = document.getElementById('tabelaEquipe');
    tbody.innerHTML = '';

    if (equipe.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4">Nenhum funcionário cadastrado.</td></tr>';
        return;
    }

    equipe.forEach((func) => {
        tbody.innerHTML += `
            <tr>
                <td><strong>${func.nome}</strong></td>
                <td>${func.cpf}</td>
                <td><span class="badge-cargo">${NOMES_CARGOS[func.cargo] || func.cargo}</span></td>
                <td>
                    <button class="btn-acao acao-editar" onclick="abrirModalEditar(${func.id})" title="Editar">✏️</button>
                    <button class="btn-acao acao-demitir" onclick="demitirFuncionario(${func.id})" title="Demitir">🗑️</button>
                </td>
            </tr>
        `;
    });
}

function definirCamposEditaveis(emEdicao) {
    document.getElementById('rhCpf').disabled = emEdicao;
    document.getElementById('rhTelefone').disabled = emEdicao;
    document.getElementById('rhCargo').disabled = emEdicao;
    document.getElementById('rhSenha').required = !emEdicao;

    document.getElementById('dicaSenhaRH').innerText = emEdicao
        ? 'Deixe em branco para manter a senha atual. CPF, telefone e cargo não podem ser alterados após a contratação.'
        : '';
}

function abrirModalContratar() {
    document.getElementById('formRH').reset();
    document.getElementById('editIndex').value = '';
    document.getElementById('tituloModalRH').innerText = 'Contratar Funcionário';
    definirCamposEditaveis(false);
    document.getElementById('modalRH').style.display = 'flex';
}

function abrirModalEditar(id) {
    const func = equipe.find(f => f.id === id);
    if (!func) return;

    document.getElementById('rhNome').value = func.nome;
    document.getElementById('rhCpf').value = func.cpf;
    document.getElementById('rhTelefone').value = func.telefone || '';
    document.getElementById('rhSenha').value = '';
    document.getElementById('rhCargo').value = func.cargo;

    document.getElementById('editIndex').value = func.id;
    document.getElementById('tituloModalRH').innerText = 'Editar Funcionário';
    definirCamposEditaveis(true);
    document.getElementById('modalRH').style.display = 'flex';
}

function fecharModalRH() {
    document.getElementById('modalRH').style.display = 'none';
}

document.getElementById('formRH').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('editIndex').value;

    try {
        if (id === '') {
            const novoFunc = {
                nome: document.getElementById('rhNome').value,
                cpf: document.getElementById('rhCpf').value.replace(/\D/g, ''),
                telefone: document.getElementById('rhTelefone').value.replace(/\D/g, ''),
                senha: document.getElementById('rhSenha').value,
                cargo: document.getElementById('rhCargo').value
            };

            await apiFetch('/gerente-geral/funcionarios', {
                method: 'POST',
                body: JSON.stringify(novoFunc)
            });
            alert('Funcionário contratado com sucesso!');
        } else {

            const dadosAtualizados = {
                nome: document.getElementById('rhNome').value,
                senha: document.getElementById('rhSenha').value || null
            };

            await apiFetch(`/gerente-geral/funcionarios/${id}`, {
                method: 'PUT',
                body: JSON.stringify(dadosAtualizados)
            });
            alert('Dados do funcionário atualizados!');
        }

        fecharModalRH();
        await carregarEquipe();
    } catch (error) {
        alert(error.message);
    }
});

async function demitirFuncionario(id) {
    const func = equipe.find(f => f.id === id);
    if (!func) return;

    const confirmar = confirm(`Tem certeza que deseja demitir ${func.nome}? Esta ação removerá o acesso dele ao sistema.`);
    if (!confirmar) return;

    try {
        await apiFetch(`/gerente-geral/funcionarios/${id}`, { method: 'DELETE' });
        await carregarEquipe();
    } catch (error) {
        alert(error.message);
    }
}

document.getElementById('rhCpf').addEventListener('input', aplicarMascaraCpf);
document.getElementById('rhTelefone').addEventListener('input', aplicarMascaraTelefone);

window.onload = () => {
    usuarioLogado = exigirAcesso(['GERENTE_GERAL']);
    if (!usuarioLogado) return;

    document.getElementById('nomeGerenteLogado').innerText = usuarioLogado.nome;

    carregarDashboard();
    carregarEquipe();
};
