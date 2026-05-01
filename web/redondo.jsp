<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Rótulo - Quitandas da Duda</title>
<style>
    body {
        background-color: #f9f9f9;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        font-family: 'Segoe UI', sans-serif;
    }

    .rotulo {
        position: relative;
        width: 600px;
        height: 600px;
        border-radius: 50%;
        background: radial-gradient(circle, #fff 70%, #fceae8 100%);
        border: 10px solid #b80000;
        box-shadow: 0 0 10px rgba(0,0,0,0.2);
        text-align: center;
        overflow: hidden;
    }

    .titulo {
        font-weight: bold;
        font-size: 28px;
        color: #5a2a00;
        margin-top: 25px;
    }

    .faixa {
        position: relative;
        background-color: #b80000;
        color: white;
        padding: 15px 0;
        margin: 20px auto;
        width: 80%;
        font-size: 26px;
        font-family: 'Brush Script MT', cursive;
        border-radius: 6px;
    }

    .faixa small {
        display: block;
        font-size: 16px;
        font-family: 'Segoe UI', sans-serif;
    }

    .ingredientes {
        text-align: left;
        font-size: 14px;
        margin: 20px 60px;
        line-height: 1.5;
    }

    .ingredientes b {
        font-size: 16px;
        text-transform: uppercase;
    }

    .peso {
        font-size: 28px;
        font-weight: bold;
        margin-top: -10px;
    }

    .tabela {
        width: 60%;
        margin: 10px auto;
        font-size: 12px;
        border-collapse: collapse;
    }

    .tabela th, .tabela td {
        border: 1px solid #444;
        padding: 3px 6px;
    }

    .tabela th {
        background-color: #f0f0f0;
    }

    .rodape {
        position: absolute;
        bottom: 30px;
        width: 100%;
        font-size: 13px;
        text-align: center;
        color: #333;
    }

    .whatsapp {
        font-weight: bold;
        color: #006400;
        margin: 5px 0;
    }

    .fabricacao {
        border: 2px solid #b80000;
        border-radius: 8px;
        padding: 4px;
        width: 200px;
        margin: 8px auto;
        text-align: left;
        font-size: 13px;
    }

    .fabricacao label {
        display: block;
        margin-top: 2px;
    }

</style>
</head>
<body>

<div class="rotulo">
    <div class="titulo">? QUITANDAS DA DUDA</div>

    <div class="faixa">
        Bolo de Cenoura
        <small>(Cobertura de chocolate)</small>
    </div>

    <table class="tabela">
        <tr><th colspan="3">TABELA NUTRICIONAL</th></tr>
        <tr><td></td><td>100 g</td><td>15 g</td></tr>
        <tr><td>Calorias (kcal)</td><td>66.0</td><td>0.5</td></tr>
        <tr><td>Carboidratos (g)</td><td>13.0</td><td>0.7</td></tr>
        <tr><td>Açúcares Totais (g)</td><td>12.0</td><td>0.5</td></tr>
        <tr><td>Proteínas (g)</td><td>8.6</td><td>1.2</td></tr>
        <tr><td>Gorduras Totais (g)</td><td>1.9</td><td>0.2</td></tr>
        <tr><td>Gorduras Saturadas (g)</td><td>1.3</td><td>0.1</td></tr>
        <tr><td>Gorduras Trans (g)</td><td>0.0</td><td>0.0</td></tr>
        <tr><td>Fibras Alimentares (g)</td><td>3.5</td><td>0.2</td></tr>
        <tr><td>Sódio (mg)</td><td>46.0</td><td>0.3</td></tr>
    </table>

    <div class="ingredientes">
        <b>Ingredientes:</b><br>
        Cenoura, açúcar, ovos, farinha de trigo, fermento, leite caipira, cacau em pó
    </div>

    <div class="peso">500 g</div>

    <div class="whatsapp">? (34) 99171-4835</div>

    <div class="fabricacao">
        <label>Fabricação: _____________________</label>
        <label>Validade: ______________________</label>
    </div>

    <div class="rodape">
        Produzido por: Quitandas da Duda ? Endereço: P.A Celso Lúcio Fazenda Carinhosa
    </div>
</div>

</body>
</html>
