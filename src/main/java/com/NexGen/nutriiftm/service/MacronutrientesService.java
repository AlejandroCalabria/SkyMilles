package com.NexGen.nutriiftm.service;

import com.NexGen.nutriiftm.domain.nutricional.ArredondamentoAnvisa;
import com.NexGen.nutriiftm.domain.nutricional.NutricionalConstants;
import com.NexGen.nutriiftm.model.IngredienteReceita;
import com.NexGen.nutriiftm.model.ItemTACO;
import com.NexGen.nutriiftm.model.Receita;
import com.NexGen.nutriiftm.model.ValoresNutricionais;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Calcula valores nutricionais conforme RDC 429/2020 + IN 75/2020.
 *
 * Correções aplicadas:
 *  1. Fibra removida do cálculo de energia (violava IN 75/2020).
 *  2. BigDecimal usado para acumulação — elimina erro de ponto flutuante.
 *  3. Fail-fast quando receita não tem peso (em vez de retornar objeto zerado silencioso).
 *  4. Todas as constantes vêm de NutricionalConstants.
 *  5. Todos os arredondamentos vêm de ArredondamentoAnvisa.
 */
@Service
public class MacronutrientesService {

    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);

    /**
     * @throws IllegalArgumentException se a receita não contiver nenhum ingrediente
     *                                   com dados TBCA (pesoTotal = 0).
     */
    public ValoresNutricionais calcular(Receita receita) {
        // Acumuladores BigDecimal — evita erro de ponto flutuante em somas longas
        BigDecimal somaEnergia = BigDecimal.ZERO;
        BigDecimal somaCarb    = BigDecimal.ZERO;
        BigDecimal somaAcucar  = BigDecimal.ZERO;
        BigDecimal somaProt    = BigDecimal.ZERO;
        BigDecimal somaLip     = BigDecimal.ZERO;
        BigDecimal somaSat     = BigDecimal.ZERO;
        BigDecimal somaFibra   = BigDecimal.ZERO;
        BigDecimal somaSodio   = BigDecimal.ZERO;
        BigDecimal totalPeso   = BigDecimal.ZERO;

        for (IngredienteReceita ing : receita.getIngredientes()) {
            ItemTACO item = ing.getItemTACO();
            if (item == null) continue;

            BigDecimal qtd   = BigDecimal.valueOf(ing.getQuantidadeG());
            BigDecimal fator = qtd.divide(BigDecimal.valueOf(100), MC);

            // CORREÇÃO #1: Fibra NÃO entra no cálculo de energia do rótulo (IN 75/2020)
            BigDecimal energiaItem = BigDecimal.valueOf(item.getProteina())
                    .multiply(BigDecimal.valueOf(NutricionalConstants.KCAL_POR_GRAMA_PROTEINA))
                    .add(BigDecimal.valueOf(item.getCarboidrato())
                            .multiply(BigDecimal.valueOf(NutricionalConstants.KCAL_POR_GRAMA_CARBOIDRATO)))
                    .add(BigDecimal.valueOf(item.getLipideos())
                            .multiply(BigDecimal.valueOf(NutricionalConstants.KCAL_POR_GRAMA_LIPIDEO)));

            somaEnergia = somaEnergia.add(energiaItem.multiply(fator, MC));
            somaCarb    = somaCarb.add(BigDecimal.valueOf(item.getCarboidrato()).multiply(fator, MC));
            somaAcucar  = somaAcucar.add(BigDecimal.valueOf(item.getAcucarTotal()).multiply(fator, MC));
            somaProt    = somaProt.add(BigDecimal.valueOf(item.getProteina()).multiply(fator, MC));
            somaLip     = somaLip.add(BigDecimal.valueOf(item.getLipideos()).multiply(fator, MC));
            somaSat     = somaSat.add(BigDecimal.valueOf(item.getAcidoGraxoSaturado()).multiply(fator, MC));
            somaFibra   = somaFibra.add(BigDecimal.valueOf(item.getFibra()).multiply(fator, MC));
            somaSodio   = somaSodio.add(BigDecimal.valueOf(item.getSodio()).multiply(fator, MC));
            totalPeso   = totalPeso.add(qtd);
        }

        receita.setPesoTotalG(totalPeso.doubleValue());

        // CORREÇÃO #8: Fail-fast em vez de retornar objeto zerado silencioso
        if (totalPeso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Receita sem peso válido: nenhum ingrediente foi encontrado na TBCA. " +
                "Verifique os nomes dos ingredientes."
            );
        }

        double pesoD = totalPeso.doubleValue();
        double porcaoG = receita.getPorcaoG() > 0 ? receita.getPorcaoG() : 100.0;

        // ── Valores brutos por 100g (double para ArredondamentoAnvisa) ──────────
        double f100 = 100.0 / pesoD;
        double energia100  = somaEnergia.doubleValue() * f100;
        double carb100     = somaCarb.doubleValue()    * f100;
        double acucar100   = somaAcucar.doubleValue()  * f100;
        double prot100     = somaProt.doubleValue()     * f100;
        double lip100      = somaLip.doubleValue()      * f100;
        double sat100      = somaSat.doubleValue()      * f100;
        double fibra100    = somaFibra.doubleValue()    * f100;
        double sodio100    = somaSodio.doubleValue()    * f100;

        // ── Valores brutos por porção ─────────────────────────────────────────
        double fp = porcaoG / pesoD;
        double energiaPorcao = somaEnergia.doubleValue() * fp;
        double carbPorcao    = somaCarb.doubleValue()    * fp;
        double acucarPorcao  = somaAcucar.doubleValue()  * fp;
        double protPorcao    = somaProt.doubleValue()     * fp;
        double lipPorcao     = somaLip.doubleValue()      * fp;
        double satPorcao     = somaSat.doubleValue()      * fp;
        double fibraPorcao   = somaFibra.doubleValue()   * fp;
        double sodioPorcao   = somaSodio.doubleValue()   * fp;

        return ValoresNutricionais.builder()
                // 100g
                .energia100kcal(ArredondamentoAnvisa.energia(energia100))
                .energia100kj(ArredondamentoAnvisa.energia(energia100 * NutricionalConstants.KCAL_TO_KJ))
                .carboidrato100g(ArredondamentoAnvisa.gramas(carb100))
                .acucaresTotal100g(ArredondamentoAnvisa.gramas(acucar100))
                .proteina100g(ArredondamentoAnvisa.gramas(prot100))
                .lipideos100g(ArredondamentoAnvisa.gramas(lip100))
                .saturado100g(ArredondamentoAnvisa.gramas(sat100))
                .trans100g(0.0)  // TBCA não fornece: declarar 0 conforme política
                .fibra100g(ArredondamentoAnvisa.gramas(fibra100))
                .sodio100mg(ArredondamentoAnvisa.miligramas(sodio100))
                // Porção
                .energiaPorcaoKcal(ArredondamentoAnvisa.energia(energiaPorcao))
                .energiaPorcaoKj(ArredondamentoAnvisa.energia(energiaPorcao * NutricionalConstants.KCAL_TO_KJ))
                .carboidratoPorcao(ArredondamentoAnvisa.gramas(carbPorcao))
                .acucaresTotalPorcao(ArredondamentoAnvisa.gramas(acucarPorcao))
                .proteinaPorcao(ArredondamentoAnvisa.gramas(protPorcao))
                .lipideosPorcao(ArredondamentoAnvisa.gramas(lipPorcao))
                .saturadoPorcao(ArredondamentoAnvisa.gramas(satPorcao))
                .transPorcao(ArredondamentoAnvisa.gorduraTrans(0.0))
                .fibraPorcao(ArredondamentoAnvisa.gramas(fibraPorcao))
                .sodioPorcaoMg(ArredondamentoAnvisa.miligramas(sodioPorcao))
                // %VD
                .vdEnergia(ArredondamentoAnvisa.percentualVD(energiaPorcao,  NutricionalConstants.VD_ENERGIA_KCAL))
                .vdCarboidrato(ArredondamentoAnvisa.percentualVD(carbPorcao, NutricionalConstants.VD_CARBOIDRATO_G))
                .vdProteina(ArredondamentoAnvisa.percentualVD(protPorcao,    NutricionalConstants.VD_PROTEINA_G))
                .vdLipideos(ArredondamentoAnvisa.percentualVD(lipPorcao,     NutricionalConstants.VD_LIPIDEOS_G))
                .vdSaturado(ArredondamentoAnvisa.percentualVD(satPorcao,     NutricionalConstants.VD_SATURADO_G))
                .vdFibra(ArredondamentoAnvisa.percentualVD(fibraPorcao,      NutricionalConstants.VD_FIBRA_G))
                .vdSodio(ArredondamentoAnvisa.percentualVD(sodioPorcao,      NutricionalConstants.VD_SODIO_MG))
                // Aux
                .pesoTotalG(pesoD)
                .build();
    }
}