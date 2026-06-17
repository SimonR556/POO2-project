const form = document.getElementById('formLogin');

form.addEventListener('submit', async (evento) => {
    evento.preventDefault();

    const cpfDigitado = document.getElementById('cpf').value.replace(/\D/g, '');
    const senhaDigitada = document.getElementById('senha').value;

    const credenciais = {
        cpf: cpfDigitado,
        senha: senhaDigitada
    };

    try {
        const usuarioLogado = await apiFetch('/login', {
            method: 'POST',
            body: JSON.stringify(credenciais)
        });

        salvarUsuarioLogado(usuarioLogado);

        switch (usuarioLogado.cargo) {
            case 'GERENTE_GERAL':
                window.location.href = 'gerente-geral.html';
                break;
            case 'GERENTE_VENDAS':
                window.location.href = 'gerente-vendas.html';
                break;
            case 'GERENTE_ESTOQUE':
                window.location.href = 'gerente-estoque.html';
                break;
            case 'ATENDENTE':
                window.location.href = 'atendente.html';
                break;
            default:
                sessionStorage.removeItem('usuarioLogado');
                alert('Acesso negado: Este portal é exclusivo para funcionários do restaurante.');
        }

    } catch (error) {
        alert(error.message);
        document.getElementById('senha').value = '';
    }
});

document.getElementById('cpf').addEventListener('input', aplicarMascaraCpf);
