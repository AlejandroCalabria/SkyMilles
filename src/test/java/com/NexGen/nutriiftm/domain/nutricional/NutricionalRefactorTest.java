package com.NexGen.nutriiftm.domain.nutricional;

import com.NexGen.nutriiftm.model.*;
import com.NexGen.nutriiftm.service.MacronutrientesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Testes unitários para o módulo nutricional refatorado.
 * Cobertura dos 13 problemas identificados na auditoria.
 */
class NutricionalRefactorTest {

    // ─────────────────────────────────────────────────────────────────────────
    // ArredondamentoAnvisa
    // ─────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("ArredondamentoAnvisa")
    class ArredondamentoAnvisaTest {

        @Test
        @DisplayName("energia: arredonda para inteiro, retorna 0 para NaN/Inf")
        void energia_arredondaEProtege() {
            assertThat(ArredondamentoAnvisa.energia(239.4)).isEqualTo(239);
            assertThat(ArredondamentoAnvisa.energia(239.5)).isEqualTo(240);
            assertThat(ArredondamentoAnvisa.energia(Double.NaN)).isEqualTo(0);
            assertThat(ArredondamentoAnvisa.energia(Double.POSITIVE_INFINITY)).isEqualTo(0);
        }

        @Test
        @DisplayName("gramas: 1 casa decimal HALF_UP")
        void gramas_umaCasaDecimal() {
            assertThat(ArredondamentoAnvisa.gramas(14.95)).isEqualTo(15.0);
            assertThat(ArredondamentoAnvisa.gramas(14.94)).isEqualTo(14.9);
            assertThat(ArredondamentoAnvisa.gramas(Double.NaN)).isEqualTo(0.0);
        }

        @Test
        @DisplayName("percentualVD: inteiro, guard contra divisão por zero e -0.0")
        void percentualVD_guardContraDivisaoZero() {
            assertThat(ArredondamentoAnvisa.percentualVD(15.0, 300.0)).isEqualTo(5);
            assertThat(ArredondamentoAnvisa.percentualVD(10.0, 0.0)).isEqualTo(0);
            assertThat(ArredondamentoAnvisa.percentualVD(10.0, -0.0)).isEqualTo(0);   // -0.0 IEEE 754
            assertThat(ArredondamentoAnvisa.percentualVD(10.0, -1.0)).isEqualTo(0);   // negativo
            assertThat(ArredondamentoAnvisa.percentualVD(Double.NaN, 100.0)).isEqualTo(0);
        }

        @Test
        @DisplayName("gorduraTrans: < 0.2g → 0 (IN 75/2020)")
        void gorduraTrans_menorQue02_retornaZero() {
            assertThat(ArredondamentoAnvisa.gorduraTrans(0.19)).isEqualTo(0.0);
            assertThat(ArredondamentoAnvisa.gorduraTrans(0.0)).isEqualTo(0.0);
            assertThat(ArredondamentoAnvisa.gorduraTrans(0.2)).isEqualTo(0.2);
            assertThat(ArredondamentoAnvisa.gorduraTrans(0.5)).isEqualTo(0.5);
        }

        @Test
        @DisplayName("valorPor100g: retorna 0 quando porção é zero")
        void valorPor100g_porcaoZero_retornaZero() {
            assertThat(ArredondamentoAnvisa.valorPor100g(10.0, 0.0)).isEqualTo(0.0);
            assertThat(ArredondamentoAnvisa.valorPor100g(10.0, 100.0)).isEqualTo(10.0);
            // 5g por porção de 50g → 10g/100g
            assertThat(ArredondamentoAnvisa.valorPor100g(5.0, 50.0)).isEqualTo(10.0);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MacronutrientesService — correções #1, #4, #5, #8
    // ─────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("MacronutrientesService")
    class MacronutrientesServiceTest {

        private MacronutrientesService service;

        @BeforeEach
        void setUp() { service = new MacronutrientesService(); }

        private ItemTACO itemComNutrientes(double proteina, double carb, double lip,
                                           double fibra, double saturado, double sodio) {
            ItemTACO i = new ItemTACO();
            i.setProteina(proteina);
            i.setCarboidrato(carb);
            i.setLipideos(lip);
            i.setFibra(fibra);
            i.setAcidoGraxoSaturado(saturado);
            i.setSodio(sodio);
            i.setAcucarTotal(0.0);
            return i;
        }

        private Receita receitaComItem(ItemTACO item, double qtdG, double porcaoG) {
            IngredienteReceita ing = new IngredienteReceita();
            ing.setNome("Teste");
            ing.setQuantidadeG(qtdG);
            ing.setItemTACO(item);

            Receita r = new Receita();
            r.setPorcaoG(porcaoG);
            r.setIngredientes(List.of(ing));
            return r;
        }

        @Test
        @DisplayName("CORREÇÃO #1: Fibra NÃO entra no cálculo de energia")
        void fibra_naoEntreNaEnergia() {
            // 100g de proteína pura com fibra: energia deve ser 400 kcal (4*100)
            // Se fibra entrasse (×2), seria 400 + fibra*2 kcal — incorreto
            ItemTACO item = itemComNutrientes(100.0, 0.0, 0.0, 50.0, 0.0, 0.0);
            Receita r = receitaComItem(item, 100.0, 100.0);
            ValoresNutricionais v = service.calcular(r);
            // 100g proteína × 4 kcal/g = 400 kcal — fibra não soma
            assertThat(v.getEnergia100kcal()).isEqualTo(400);
        }

        @Test
        @DisplayName("CORREÇÃO #1: Energia correta com múltiplos macros")
        void energia_formulaAtwater_semFibra() {
            // prot=10g, carb=20g, lip=5g → 10*4 + 20*4 + 5*9 = 40+80+45 = 165 kcal/100g
            ItemTACO item = itemComNutrientes(10.0, 20.0, 5.0, 8.0, 2.0, 100.0);
            Receita r = receitaComItem(item, 100.0, 100.0);
            ValoresNutricionais v = service.calcular(r);
            assertThat(v.getEnergia100kcal()).isEqualTo(165);
        }

        @Test
        @DisplayName("CORREÇÃO #4/#5: Arredondamento correto — retorna int, não double")
        void arredondamento_retornaInt() {
            ItemTACO item = itemComNutrientes(10.0, 20.0, 5.0, 3.0, 2.0, 500.0);
            Receita r = receitaComItem(item, 100.0, 100.0);
            ValoresNutricionais v = service.calcular(r);
            // Verifica que energia é int (sem .5 etc.)
            assertThat(v.getEnergia100kcal()).isInstanceOf(Integer.class);
            assertThat(v.getSodio100mg()).isInstanceOf(Integer.class);
            assertThat(v.getVDEnergia()).isInstanceOf(Integer.class);
        }

        @Test
        @DisplayName("CORREÇÃO #8: Fail-fast quando receita vazia")
        void receitaVazia_lancaException() {
            Receita r = new Receita();
            r.setPorcaoG(100.0);
            r.setIngredientes(List.of()); // sem ingredientes TBCA
            assertThatThrownBy(() -> service.calcular(r))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("sem peso");
        }

        @Test
        @DisplayName("CORREÇÃO #8: Ingredientes sem ItemTACO lançam exceção (não retornam zeros silenciosos)")
        void ingredientesSemTACO_lancaException() {
            IngredienteReceita ing = new IngredienteReceita();
            ing.setNome("Ingrediente Inexistente");
            ing.setQuantidadeG(100.0);
            ing.setItemTACO(null); // não encontrado na TBCA

            Receita r = new Receita();
            r.setPorcaoG(100.0);
            r.setIngredientes(List.of(ing));

            assertThatThrownBy(() -> service.calcular(r))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("CORREÇÃO #5: Acumulação com muitos ingredientes sem erro de ponto flutuante")
        void acumulacao_muitosIngredientes_semErroPontoFlutuante() {
            // 10 ingredientes idênticos de 10g cada → equivalente a 1 ingrediente de 100g
            ItemTACO item = itemComNutrientes(5.0, 10.0, 2.0, 1.0, 0.5, 50.0);
            List<IngredienteReceita> lista = new java.util.ArrayList<>();
            for (int i = 0; i < 10; i++) {
                IngredienteReceita ing = new IngredienteReceita();
                ing.setNome("item" + i);
                ing.setQuantidadeG(10.0);
                ing.setItemTACO(item);
                lista.add(ing);
            }
            Receita r = new Receita();
            r.setPorcaoG(100.0);
            r.setIngredientes(lista);

            ValoresNutricionais v = service.calcular(r);

            // 100g = 10 ingredientes × 10g × (5g prot/100g) = 5g prot/100g
            assertThat(v.getProteina100g()).isEqualTo(5.0);
            assertThat(v.getCarboidrato100g()).isEqualTo(10.0);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // NutricionalConstants — garantir que constantes não foram alteradas
    // ─────────────────────────────────────────────────────────────────────────
    @Nested
    @DisplayName("NutricionalConstants")
    class NutricionalConstantsTest {

        @Test
        @DisplayName("VDs conforme IN 75/2020")
        void constants_conformeIN75_2020() {
            assertThat(NutricionalConstants.VD_ENERGIA_KCAL).isEqualTo(2000.0);
            assertThat(NutricionalConstants.VD_CARBOIDRATO_G).isEqualTo(300.0);
            assertThat(NutricionalConstants.VD_PROTEINA_G).isEqualTo(75.0);
            assertThat(NutricionalConstants.VD_LIPIDEOS_G).isEqualTo(55.0);
            assertThat(NutricionalConstants.VD_SATURADO_G).isEqualTo(22.0);
            assertThat(NutricionalConstants.VD_FIBRA_G).isEqualTo(25.0);
            assertThat(NutricionalConstants.VD_SODIO_MG).isEqualTo(2400.0);
        }

        @Test
        @DisplayName("Fator kJ conforme ANVISA")
        void fatorKj_conformeAnvisa() {
            assertThat(NutricionalConstants.KCAL_TO_KJ).isEqualTo(4.184);
        }

        @Test
        @DisplayName("Fibra não contribui para energia no rótulo")
        void fibra_naoContribuiParaEnergia() {
            assertThat(NutricionalConstants.KCAL_POR_GRAMA_FIBRA_ROTULO).isEqualTo(0.0);
        }
    }
}