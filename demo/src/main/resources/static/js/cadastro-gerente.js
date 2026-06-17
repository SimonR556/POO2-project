const formCadastro = document.getElementById('formCadastroGerente');

formCadastro.addEventListener('submit', async (evento) => {
    evento.preventDefault();

    const nomeDigitado = document.getElementById('nome').value;
    const cpfDigitado = document.getElementById('cpf').value.replace(/\D/g, '');
    const telefoneDigitado = document.getElementById('telefone').value.replace(/\D/g, '');
    const senhaDigitada = document.getElementById('senha').value;

    const novoGerente = {
        nome: nomeDigitado,
        cpf: cpfDigitado,
        telefone: telefoneDigitado,
        senha: senhaDigitada,
        cargo: 'GERENTE_GERAL'
    };

    try {
        await apiFetch('/gerente-geral/funcionarios', {
            method: 'POST',
            body: JSON.stringify(novoGerente)
        });

        alert('Gerente Geral cadastrado com sucesso!');

        formCadastro.reset();
        window.location.href = 'index.html';

    } catch (error) {
        alert(error.message);
    }
});

document.getElementById('cpf').addEventListener('input', aplicarMascaraCpf);
document.getElementById('telefone').addEventListener('input', aplicarMascaraTelefone);
