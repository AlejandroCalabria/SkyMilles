package modelo;

/**
 * Classe responsável por calcular os valores nutricionais de uma receita
 * a partir dos ingredientes e seus dados da Tabela TACO.
 *
 * Lógica:
 * 1. Para cada ingrediente, pega os nutrientes por 100g da TACO
 * 2. Proporciona pela quantidade real em gramas
 * 3. Soma todos os ingredientes
 * 4. Calcula por 100g da receita final e por porção
 * 5. Calcula %VD conforme RDC 360/2003
 */
public class MacronutrientesCalc {

    // Valores diários de recomendação (RDC 360/2003)
    private static final double VD_ENERGIA = 2000; // kcal
    private static final double VD_CARB = 300;     // g
    private static final double VD_PROT = 75;      // g
    private static final double VD_LIP = 55;       // g
    private static final double VD_SAT = 22;       // g
    private static final double VD_FIBRA = 25;     // g
    private static final double VD_SODIO = 2400;   // mg

    public ValoresNutricionais calcular(Receita receita) {
        ValoresNutricionais v = new ValoresNutricionais();

        double totalPeso = 0;
        double somaEnergia = 0;
        double somaCarb = 0;
        double somaProt = 0;
        double somaLipideos = 0;
        double somaSaturado = 0;
        double somaFibra = 0;
        double somaSodio = 0;

        for (IngredienteReceita ing : receita.getIngredientes()) {
            ItemTACO item = ing.getItemTACO();
            if (item == null) continue; // Ingrediente não encontrado na TACO

            double qtd = ing.getQuantidadeG();
            double fator = qtd / 100.0; // Valores TACO são por 100g

            somaEnergia += item.getEnergia() * fator;
            somaCarb += item.getCarboidrato() * fator;
            somaProt += item.getProteina() * fator;
            somaLipideos += item.getLipideos() * fator;
            somaSaturado += item.getAcidoGraxoSaturado() * fator;
            somaFibra += item.getFibra() * fator;
            somaSodio += item.getSodio() * fator;
            totalPeso += qtd;
        }

        receita.setPesoTotalG(totalPeso);

        if (totalPeso <= 0) return v;

        // Valores por 100g da receita final
        double fator100g = 100.0 / totalPeso;
        v.setEnergia100g(arredondar(somaEnergia * fator100g));
        v.setCarboidrato100g(arredondar(somaCarb * fator100g));
        v.setProteina100g(arredondar(somaProt * fator100g));
        v.setLipideos100g(arredondar(somaLipideos * fator100g));
        v.setSaturado100g(arredondar(somaSaturado * fator100g));
        v.setFibra100g(arredondar(somaFibra * fator100g));
        v.setSodio100g(arredondar(somaSodio * fator100g));

        // Valores por porção
        double porcao = receita.getPorcaoG();
        if (porcao <= 0) porcao = 100; // Fallback
        double fatorPorcao = porcao / totalPeso;
        v.setEnergiaPorcao(arredondar(somaEnergia * fatorPorcao));
        v.setCarboidratoPorcao(arredondar(somaCarb * fatorPorcao));
        v.setProteinaPorcao(arredondar(somaProt * fatorPorcao));
        v.setLipideosPorcao(arredondar(somaLipideos * fatorPorcao));
        v.setSaturadoPorcao(arredondar(somaSaturado * fatorPorcao));
        v.setFibraPorcao(arredondar(somaFibra * fatorPorcao));
        v.setSodioPorcao(arredondar(somaSodio * fatorPorcao));

        // Cálculo dos %VD (baseado na porção)
        v.setVDEnergia(arredondar(v.getEnergiaPorcao() / VD_ENERGIA * 100));
        v.setVDCarboidrato(arredondar(v.getCarboidratoPorcao() / VD_CARB * 100));
        v.setVDProteina(arredondar(v.getProteinaPorcao() / VD_PROT * 100));
        v.setVDLipideos(arredondar(v.getLipideosPorcao() / VD_LIP * 100));
        v.setVDSaturado(arredondar(v.getSaturadoPorcao() / VD_SAT * 100));
        v.setVDFibra(arredondar(v.getFibraPorcao() / VD_FIBRA * 100));
        v.setVDSodio(arredondar(v.getSodioPorcao() / VD_SODIO * 100));

        return v;
    }

    /**
     * Arredonda para 1 casa decimal seguindo ANVISA.
     * Valor Energético e Sódio seguem regras diferentes mas usamos mesma base.
     */
    private double arredondar(double valor) {
        return Math.round(valor * 10.0) / 10.0;
    }
}
