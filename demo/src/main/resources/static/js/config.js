const API_BASE_URL = 'http://localhost:8080';

function salvarUsuarioLogado(usuario) {
    sessionStorage.setItem('usuarioLogado', JSON.stringify(usuario));
}

function obterUsuarioLogado() {
    const dados = sessionStorage.getItem('usuarioLogado');
    return dados ? JSON.parse(dados) : null;
}

function sairDoSistema() {
    sessionStorage.removeItem('usuarioLogado');
    window.location.href = 'index.html';
}

function exigirAcesso(cargosPermitidos) {
    const usuario = obterUsuarioLogado();

    if (!usuario || usuario.tipo !== 'FUNCIONARIO') {
        window.location.href = 'index.html';
        return null;
    }

    if (cargosPermitidos && !cargosPermitidos.includes(usuario.cargo)) {
        alert('Você não tem permissão para acessar esta página.');
        window.location.href = 'index.html';
        return null;
    }

    return usuario;
}

async function apiFetch(caminho, opcoes = {}) {
    const resposta = await fetch(`${API_BASE_URL}${caminho}`, {
        headers: { 'Content-Type': 'application/json' },
        ...opcoes
    });

    if (!resposta.ok) {
        let mensagem = `Erro ${resposta.status}`;
        const texto = await resposta.text();
        if (texto) {
            try {
                const corpo = JSON.parse(texto);
                mensagem = typeof corpo === 'string' ? corpo : (corpo.erro || corpo.message || texto);
            } catch (e) {
                mensagem = texto;
            }
        }
        throw new Error(mensagem);
    }

    if (resposta.status === 204) {
        return null;
    }

    const texto = await resposta.text();
    return texto ? JSON.parse(texto) : null;
}

function formatarMoeda(valor) {
    return Number(valor || 0).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

function moedaParaNumero(textoMoeda) {
    const limpo = (textoMoeda || '').replace(/\D/g, '');
    return limpo === '' ? 0 : parseFloat(limpo) / 100;
}

function aplicarMascaraMoeda(e) {
    let valor = e.target.value.replace(/\D/g, '');
    if (valor === '') {
        e.target.value = '';
        return;
    }
    valor = (parseInt(valor, 10) / 100).toFixed(2);
    valor = valor.replace('.', ',');
    valor = valor.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
    e.target.value = 'R$ ' + valor;
}

function aplicarMascaraCpf(e) {
    let x = e.target.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,3})(\d{0,3})(\d{0,2})/);
    e.target.value = !x[2] ? x[1] : x[1] + '.' + x[2] + (x[3] ? '.' : '') + x[3] + (x[4] ? '-' + x[4] : '');
}

function aplicarMascaraTelefone(e) {
    let x = e.target.value.replace(/\D/g, '').match(/(\d{0,2})(\d{0,5})(\d{0,4})/);
    e.target.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
}
