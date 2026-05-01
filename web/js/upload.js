document.getElementById('uploadButton').addEventListener('click', async () => {
    const fileInput = document.getElementById('logo');
    const file = fileInput.files[0];

    if (!file) {
        document.getElementById('message').innerText = 'Por favor, selecione uma imagem.';
        return;
    }

    const formData = new FormData();
    formData.append('image', file);

    try {
        const response = await fetch('https://localhost:8080/upload', {
            method: 'POST',
            body: formData,
        });

        if (response.ok) {
            const result = await response.json();
            document.getElementById('message').innerText = 'Imagem carregada com sucesso: ' + result.url;
        } else {
            throw new Error('Erro ao carregar a imagem.');
        }
    } catch (error) {
        document.getElementById('message').innerText = error.message;
    }
});

