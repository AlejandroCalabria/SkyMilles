-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Tempo de geração: 10/05/2026 às 00:21
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `nutricional`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `cooperativa`
--

CREATE TABLE `cooperativa` (
  `cooCodigo` bigint(20) NOT NULL,
  `cooNome` varchar(255) DEFAULT NULL,
  `cooEndereco` varchar(255) DEFAULT NULL,
  `cooCNPJ` varchar(255) DEFAULT NULL,
  `cooCidade` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Despejando dados para a tabela `cooperativa`
--

INSERT INTO `cooperativa` (`cooCodigo`, `cooNome`, `cooEndereco`, `cooCNPJ`, `cooCidade`) VALUES
(1, 'Cooper Safra', 'Rau', '000', 'Uberlândia');

-- --------------------------------------------------------

--
-- Estrutura para tabela `elemento`
--

CREATE TABLE `elemento` (
  `eleCodigo` bigint(20) NOT NULL,
  `eleOrdem` int(11) NOT NULL,
  `eleValorRecomendado` double NOT NULL,
  `eleNome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Despejando dados para a tabela `elemento`
--

INSERT INTO `elemento` (`eleCodigo`, `eleOrdem`, `eleValorRecomendado`, `eleNome`) VALUES
(1, 1, 300, 'Carboridratos (g)'),
(2, 2, 300, ' Açúcares Totais (g)'),
(3, 3, 50, '  Açúcares Adicionados (g)'),
(4, 4, 50, 'Proteínas (g)'),
(5, 5, 65, 'Gorduras Totais (g)'),
(6, 6, 20, ' Gorduras Saturadas (g)'),
(7, 7, 2, ' Gorduras Trans (g)'),
(8, 8, 20, ' Gorduras monoinsaturadas (g)'),
(9, 9, 20, ' Gorduras poli-insaturadas (g)'),
(10, 11, 18, ' Ômega 6 (g)'),
(12, 13, 4000, ' Ômega 3 (mg)'),
(14, 14, 300, ' Colesterol (mg)'),
(15, 15, 25, 'Fibras Alimentares (g)'),
(16, 16, 2000, 'Sódio (mg)'),
(17, 17, 800, 'Vitamina A (mg)'),
(18, 18, 15, 'Vitamina D (mg)'),
(19, 19, 15, 'Vitamina E (mg)'),
(20, 20, 120, 'Vitamina K (mg)'),
(21, 21, 100, 'Vitamína C (mg)'),
(22, 22, 1.2, 'Tiamina (mg)'),
(23, 23, 1.2, 'Riboflavina (mg)'),
(24, 24, 15, 'Niacina (mg)'),
(25, 25, 1.3, 'Vitamina B6 (mg)'),
(26, 26, 400, 'Ácido fólico (mg)'),
(27, 27, 5, 'Ácido pantotênico (mg)'),
(28, 28, 2.4, 'Vitamina B12 (mg)'),
(29, 29, 1000, 'Cálcio (mg)'),
(30, 30, 2300, 'Cloreto (mg)'),
(31, 31, 900, 'Cobre (mg)'),
(32, 32, 35, 'Cromo (mg)'),
(33, 33, 14, 'Ferro (mg)'),
(34, 34, 4, 'Flúor (mg)'),
(35, 35, 700, 'Fósforo (mg)'),
(36, 36, 150, 'Iodo (mg)'),
(37, 37, 420, 'Magnésio (mg)'),
(38, 38, 3, 'Manganês (mg)'),
(39, 39, 45, 'Molibdênio (mg)'),
(40, 40, 3500, 'Potássio (mg)'),
(41, 41, 60, 'Selênio (mg)'),
(42, 42, 11, 'Zinco (mg)'),
(43, 43, 550, 'Colina (mg)');

-- --------------------------------------------------------

--
-- Estrutura para tabela `fabricante`
--

CREATE TABLE `fabricante` (
  `fabCodigo` bigint(20) NOT NULL,
  `cooCodigo` bigint(20) DEFAULT NULL,
  `fabNome` varchar(255) DEFAULT NULL,
  `fabEndereco` varchar(255) DEFAULT NULL,
  `fabContato` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Despejando dados para a tabela `fabricante`
--

INSERT INTO `fabricante` (`fabCodigo`, `cooCodigo`, `fabNome`, `fabEndereco`, `fabContato`) VALUES
(1, 1, 'Catarina Henrique de Moura', 'Chácara Canta Gallo - Setor Douradinho', '(34) 99116-1669'),
(2, 1, 'Quitandas da Duda', 'P.A Celso Lúcio Fazenda Carinhosa \r\n', '(34) 99171-4835\r\n'),
(3, 1, 'Teste - Produtos de Exemplo', 'Endereco de teste', 'contato@teste.com.br');

-- --------------------------------------------------------

--
-- Estrutura para tabela `ingrediente_receita`
--

CREATE TABLE `ingrediente_receita` (
  `id` bigint(20) NOT NULL,
  `codigoTACO` int(11) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `quantidadeG` double NOT NULL,
  `receita_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `produto`
--

CREATE TABLE `produto` (
  `proCodigo` bigint(20) NOT NULL,
  `fabCodigo` bigint(20) DEFAULT NULL,
  `proNome` varchar(255) DEFAULT NULL,
  `proNomeFantasia` varchar(255) DEFAULT NULL,
  `proDataFabricacao` date DEFAULT NULL,
  `proDataVencimento` date DEFAULT NULL,
  `proIngredientes` text DEFAULT NULL,
  `proPeso` double NOT NULL,
  `proRecomendacoes` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Despejando dados para a tabela `produto`
--

INSERT INTO `produto` (`proCodigo`, `fabCodigo`, `proNome`, `proNomeFantasia`, `proDataFabricacao`, `proDataVencimento`, `proIngredientes`, `proPeso`, `proRecomendacoes`) VALUES
(1, 1, 'MOLHO DE PIMENTA SUAVE', 'Pimenta da Catarina', '2024-11-01', '2024-12-01', 'Pimenta dedo de moça (sem sementes), Tomate Cereja, Óleo de soja, Sal', 150, 'Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias'),
(2, 1, 'MOLHO DE PIMENTA FORTE', 'Pimenta da Catarina', '2024-11-01', '2024-12-01', 'Pimenta dedo de moça (com sementes), Tomate Cereja, Óleo de soja, Sal', 150, 'Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias'),
(3, 1, 'MOLHO DE PIMENTA BLEND DE PIMENTAS EXTRA FORTE', 'Pimenta da Catarina', '2024-11-01', '2024-12-01', ' Pimenta Malagueta, Pimenta dedo de moça (com sementes), Pimenta Chocolate Habanero, Pimenta Roxa, Tomate Cereja, Óleo de soja, Sal', 150, 'Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias'),
(4, 1, 'CONSERVA DE JURUBEBA', 'Jurubeba da Catarina', '2024-11-01', '2024-12-01', 'Jurubeba (Solanum paniculatum), Vinagre, Sal, Alho.', 150, 'Manter em local seco e arejado. Após aberto armazenar sob refrigeração e consumir em até 30 dias'),
(5, 2, 'BOLO DE CENOURA', 'Quitandas da Duda', '2024-11-01', '2024-12-01', 'Farinha de Trigo, cenoura, óleo, ovos, açúcar e fermento químico. (Calda: Leite e achocolatado em pó)', 100, 'Depois de aberto, manter em lugar seco e arejado.'),
(6, 2, 'BOLO DE MILHO VERDE', 'Quitandas da Duda', '2024-11-01', '2024-11-01', 'Milho verde, fubá, queijo parmesão, ovos, açúcar, leite, óleo e fermento químico.', 100, 'Depois de aberto, manter em lugar seco e arejado.'),
(7, 2, 'BOLO DE CAPIM CIDREIRA', 'Quitandas da Duda', '2024-11-01', '2024-11-01', 'Farinha de Trigo, óleo, ovos, capim cidreira, açúcar, limão taiti e fermento químico.', 100, 'Depois de aberto, manter em lugar seco e arejado.'),
(8, 2, 'BOLO DE BANANA COM AVEIA E GRANOLA', 'Quitandas da Duda', '2024-11-01', '2024-11-01', 'Banana nanica, ovos, aveia, granola, óleo e fermento químico.', 100, 'Depois de aberto, manter em lugar seco e arejado.'),
(9, 2, 'BOLO DE LARANJA', 'Quitandas da Duda', '2024-11-01', '2024-11-01', 'Fubá, amido de milho, suco de laranja, óleo, ovos, açúcar e fermento químico.', 100, 'Depois de aberto, manter em lugar seco e arejado.'),
(10, 2, 'BISCOITO DE POLVILHO', 'Quitandas da Duda', '2024-11-01', '2024-11-01', 'Polvilho azedo, água, ovos, óleo e sal.', 100, 'Depois de aberto, manter em lugar seco e arejado.'),
(11, 2, 'CONSERVA DE BROTO DE BAMBU', 'Quitandas da Duda', '2024-11-01', '2024-11-01', 'Broto de bambu, água, vinagre, açúcar e sal.', 100, 'Depois de aberto, manter em lugar seco e arejado.'),
(20, 3, 'Frango Refogado com Legumes', 'Franguinho Gostosinho', '2026-04-13', '2026-07-26', 'Frango, peito, cozido (200.0g, 159.0kcal/100g), Cenoura, crua (80.0g, 40.0kcal/100g), Cebola, crua (60.0g, 39.0kcal/100g), Tomate, molho (50.0g, 29.0kcal/100g), Azeite de oliva (15.0g, 899.0kcal/100g), Alho (10.0g, 132.0kcal/100g), Sal de cozinha (3.0g, 0.0kcal/100g), Salsa (salsinha), crua (5.0g, 36.0kcal/100g)', 423, 'Manter em local seco e arejado. Após aberto armazenar sob refrigeração.'),
(21, 3, 'Bolo de Banana com Aveia:', 'Bolinho Bananilson', '2026-04-13', '2027-06-06', 'Banana, prata (150.0g, 96.0kcal/100g), Aveia, flocos (80.0g, 394.0kcal/100g), Farinha de trigo (60.0g, 364.0kcal/100g), Ovo de galinha, cozido (100.0g, 145.0kcal/100g), Açúcar, refinado (40.0g, 387.0kcal/100g), Manteiga (30.0g, 744.0kcal/100g), Leite de vaca, integral (150.0g, 58.0kcal/100g)', 610, 'Manter em local seco e arejado. Após aberto armazenar sob refrigeração.');

-- --------------------------------------------------------

--
-- Estrutura para tabela `receita`
--

CREATE TABLE `receita` (
  `id` bigint(20) NOT NULL,
  `nomeProduto` varchar(255) DEFAULT NULL,
  `pesoTotalG` double NOT NULL,
  `porcaoG` double NOT NULL,
  `unidadeMedidaPorcao` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tabelanutricional`
--

CREATE TABLE `tabelanutricional` (
  `tabCodigo` bigint(20) NOT NULL,
  `proCodigo` bigint(20) DEFAULT NULL,
  `tabPorcao` double NOT NULL,
  `tabTotalColheres` double DEFAULT NULL,
  `tabUnidadeMedidasColheres` double DEFAULT NULL,
  `tabValorenergeticoPorcao` double NOT NULL,
  `undCodigo` bigint(20) DEFAULT NULL,
  `tabPorcaoPadrao` double NOT NULL,
  `tabTotalPorcao` double NOT NULL,
  `tabVD` double NOT NULL,
  `tabValorEnergetico` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Despejando dados para a tabela `tabelanutricional`
--

INSERT INTO `tabelanutricional` (`tabCodigo`, `proCodigo`, `tabPorcao`, `tabTotalColheres`, `tabUnidadeMedidasColheres`, `tabValorenergeticoPorcao`, `undCodigo`, `tabPorcaoPadrao`, `tabTotalPorcao`, `tabVD`, `tabValorEnergetico`) VALUES
(1, 1, 15, 1, 3, 12, 1, 0, 0, 0, 0),
(2, 2, 15, 1, 3, 12, 1, 0, 0, 0, 0),
(3, 3, 15, 1, 3, 10, 1, 0, 0, 0, 0),
(4, 4, 60, 3, 4, 22, 1, 0, 0, 0, 0),
(5, 5, 60, 1, 6, 239.1, 6, 0, 0, 0, 0),
(6, 6, 60, 1, 6, 231.72, 6, 0, 0, 0, 0),
(7, 7, 60, 1, 6, 158.34, 6, 0, 0, 0, 0),
(8, 11, 50, 1, 6, 34.4, 6, 0, 0, 0, 0),
(9, 8, 60, 1, 6, 221.8, 6, 0, 0, 0, 0),
(10, 10, 30, 1, 6, 121.5, 6, 0, 0, 0, 0),
(11, 9, 60, 1, 6, 235.5, 6, 0, 0, 0, 0),
(20, 20, 150, 0, 6, 190.7, 6, 0, 0, 0, 0),
(21, 21, 60, 0, 6, 126.6, 6, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estrutura para tabela `tabela_nutricional`
--

CREATE TABLE `tabela_nutricional` (
  `tabCodigo` bigint(20) NOT NULL,
  `tabPorcao` double NOT NULL,
  `tabPorcaoPadrao` double NOT NULL,
  `tabTotalColheres` int(11) NOT NULL,
  `tabTotalPorcao` double NOT NULL,
  `tabUnidadeMedidasColheres` int(11) NOT NULL,
  `tabVD` double NOT NULL,
  `tabValorEnergetico` double NOT NULL,
  `tabValorEnergeticoPorcao` double NOT NULL,
  `proCodigo` bigint(20) DEFAULT NULL,
  `undCodigo` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `tabnutelemento`
--

CREATE TABLE `tabnutelemento` (
  `tneCodigo` bigint(20) NOT NULL,
  `eleCodigo` bigint(20) DEFAULT NULL,
  `tabCodigo` bigint(20) DEFAULT NULL,
  `tneValor` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Despejando dados para a tabela `tabnutelemento`
--

INSERT INTO `tabnutelemento` (`tneCodigo`, `eleCodigo`, `tabCodigo`, `tneValor`) VALUES
(1, 1, 1, 2),
(2, 2, 1, 0.8),
(3, 3, 1, 0),
(4, 4, 1, 0.4),
(5, 5, 1, 1.2),
(6, 6, 1, 0.2),
(7, 7, 1, 0),
(8, 15, 1, 0.5),
(9, 16, 1, 5),
(10, 1, 2, 2),
(11, 2, 2, 1.8),
(12, 3, 2, 0),
(13, 4, 2, 0.4),
(14, 5, 2, 1.2),
(15, 6, 2, 0.2),
(16, 7, 2, 0),
(17, 15, 2, 0.5),
(18, 16, 2, 5),
(19, 1, 3, 0.2),
(20, 2, 3, 1.8),
(21, 3, 3, 0),
(22, 4, 3, 1),
(23, 5, 3, 1.2),
(24, 6, 3, 0.2),
(25, 7, 3, 0),
(26, 15, 3, 0.5),
(27, 16, 3, 7),
(28, 1, 4, 4.4),
(29, 2, 4, 0.3),
(30, 3, 4, 0),
(31, 4, 4, 0.3),
(32, 5, 4, 0.6),
(33, 6, 4, 0),
(34, 7, 4, 0),
(35, 15, 4, 9.1),
(36, 16, 4, 160),
(37, 1, 5, 21.6),
(38, 3, 5, 9.5),
(39, 4, 5, 2.4),
(40, 5, 5, 15.9),
(41, 6, 5, 2.94),
(42, 15, 5, 0.564),
(43, 16, 5, 340),
(44, 1, 6, 18.12),
(45, 3, 6, 8.8),
(46, 4, 6, 6.06),
(47, 5, 6, 15),
(48, 6, 6, 2.55),
(49, 15, 6, 0.06),
(50, 16, 6, 430.2),
(51, 1, 7, 16.38),
(52, 3, 7, 9.6),
(53, 4, 7, 1.74),
(54, 5, 7, 9.54),
(55, 6, 7, 2.4),
(56, 15, 7, 0),
(57, 16, 7, 297),
(58, 1, 8, 7.6),
(59, 3, 8, 1),
(60, 4, 8, 1),
(61, 5, 8, 0),
(62, 6, 8, 0),
(63, 15, 8, 14.7),
(64, 16, 8, 222.5),
(65, 1, 9, 12.5),
(66, 3, 9, 1.8),
(67, 4, 9, 2.9),
(68, 5, 9, 17.8),
(69, 6, 9, 3.24),
(70, 15, 9, 1.4),
(71, 16, 9, 202),
(72, 1, 10, 18.2),
(73, 3, 10, 0),
(74, 4, 10, 0.7),
(75, 5, 10, 5.1),
(76, 6, 10, 1),
(77, 15, 10, 0),
(78, 16, 10, 810),
(79, 1, 11, 38.6),
(80, 3, 11, 9.5),
(81, 4, 11, 2.5),
(82, 5, 11, 12.5),
(83, 6, 11, 1.4),
(84, 15, 11, 0.7),
(85, 16, 11, 241),
(92, 1, 20, 6.5),
(93, 4, 20, 22.9),
(94, 5, 20, 7.6),
(95, 6, 20, 1.4),
(96, 15, 20, 1.5),
(97, 16, 20, 518.6),
(98, 1, 21, 17.7),
(99, 4, 21, 3.7),
(100, 5, 21, 4.5),
(101, 6, 21, 2.3),
(102, 15, 21, 1.2),
(103, 16, 21, 21.6);

-- --------------------------------------------------------

--
-- Estrutura para tabela `tab_nut_elemento`
--

CREATE TABLE `tab_nut_elemento` (
  `tneCodigo` bigint(20) NOT NULL,
  `tneVD` double NOT NULL,
  `tneValor` double NOT NULL,
  `tneValorPadrao` double NOT NULL,
  `eleCodigo` bigint(20) DEFAULT NULL,
  `tabCodigo` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `unidademedida`
--

CREATE TABLE `unidademedida` (
  `undCodigo` bigint(20) NOT NULL,
  `undNome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Despejando dados para a tabela `unidademedida`
--

INSERT INTO `unidademedida` (`undCodigo`, `undNome`) VALUES
(1, 'ml'),
(2, 'mg'),
(3, 'colher de sobremesa'),
(4, 'colher de sopa'),
(5, 'copo'),
(6, 'g');

-- --------------------------------------------------------

--
-- Estrutura para tabela `unidade_medida`
--

CREATE TABLE `unidade_medida` (
  `undCodigo` bigint(20) NOT NULL,
  `undNome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `cooperativa`
--
ALTER TABLE `cooperativa`
  ADD PRIMARY KEY (`cooCodigo`);

--
-- Índices de tabela `elemento`
--
ALTER TABLE `elemento`
  ADD PRIMARY KEY (`eleCodigo`),
  ADD UNIQUE KEY `uk_elemento_ordem` (`eleOrdem`);

--
-- Índices de tabela `fabricante`
--
ALTER TABLE `fabricante`
  ADD PRIMARY KEY (`fabCodigo`),
  ADD KEY `fk` (`cooCodigo`);

--
-- Índices de tabela `ingrediente_receita`
--
ALTER TABLE `ingrediente_receita`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsvjpsvifdlvb8fe02ja3cruth` (`receita_id`);

--
-- Índices de tabela `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`proCodigo`),
  ADD KEY `fk1` (`fabCodigo`);

--
-- Índices de tabela `receita`
--
ALTER TABLE `receita`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `tabelanutricional`
--
ALTER TABLE `tabelanutricional`
  ADD PRIMARY KEY (`tabCodigo`),
  ADD KEY `fk2` (`proCodigo`),
  ADD KEY `fk3` (`undCodigo`);

--
-- Índices de tabela `tabela_nutricional`
--
ALTER TABLE `tabela_nutricional`
  ADD PRIMARY KEY (`tabCodigo`),
  ADD KEY `FK1irptutr0pc62u25g9obpknxg` (`proCodigo`),
  ADD KEY `FK8o27e3w6523dwtt89mnowurej` (`undCodigo`);

--
-- Índices de tabela `tabnutelemento`
--
ALTER TABLE `tabnutelemento`
  ADD PRIMARY KEY (`tneCodigo`),
  ADD KEY `fk4` (`eleCodigo`),
  ADD KEY `fk5` (`tabCodigo`);

--
-- Índices de tabela `tab_nut_elemento`
--
ALTER TABLE `tab_nut_elemento`
  ADD PRIMARY KEY (`tneCodigo`),
  ADD KEY `FKamjhl8plqy0pwjhchm0jifbay` (`eleCodigo`),
  ADD KEY `FKihlpyk6dlsfwq3opp49psf6lr` (`tabCodigo`);

--
-- Índices de tabela `unidademedida`
--
ALTER TABLE `unidademedida`
  ADD PRIMARY KEY (`undCodigo`);

--
-- Índices de tabela `unidade_medida`
--
ALTER TABLE `unidade_medida`
  ADD PRIMARY KEY (`undCodigo`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cooperativa`
--
ALTER TABLE `cooperativa`
  MODIFY `cooCodigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de tabela `elemento`
--
ALTER TABLE `elemento`
  MODIFY `eleCodigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT de tabela `fabricante`
--
ALTER TABLE `fabricante`
  MODIFY `fabCodigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `ingrediente_receita`
--
ALTER TABLE `ingrediente_receita`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `produto`
--
ALTER TABLE `produto`
  MODIFY `proCodigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de tabela `receita`
--
ALTER TABLE `receita`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tabelanutricional`
--
ALTER TABLE `tabelanutricional`
  MODIFY `tabCodigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de tabela `tabela_nutricional`
--
ALTER TABLE `tabela_nutricional`
  MODIFY `tabCodigo` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `tabnutelemento`
--
ALTER TABLE `tabnutelemento`
  MODIFY `tneCodigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

--
-- AUTO_INCREMENT de tabela `tab_nut_elemento`
--
ALTER TABLE `tab_nut_elemento`
  MODIFY `tneCodigo` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `unidademedida`
--
ALTER TABLE `unidademedida`
  MODIFY `undCodigo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `unidade_medida`
--
ALTER TABLE `unidade_medida`
  MODIFY `undCodigo` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `fabricante`
--
ALTER TABLE `fabricante`
  ADD CONSTRAINT `fk` FOREIGN KEY (`cooCodigo`) REFERENCES `cooperativa` (`cooCodigo`);

--
-- Restrições para tabelas `ingrediente_receita`
--
ALTER TABLE `ingrediente_receita`
  ADD CONSTRAINT `FKsvjpsvifdlvb8fe02ja3cruth` FOREIGN KEY (`receita_id`) REFERENCES `receita` (`id`);

--
-- Restrições para tabelas `produto`
--
ALTER TABLE `produto`
  ADD CONSTRAINT `fk1` FOREIGN KEY (`fabCodigo`) REFERENCES `fabricante` (`fabCodigo`);

--
-- Restrições para tabelas `tabelanutricional`
--
ALTER TABLE `tabelanutricional`
  ADD CONSTRAINT `fk2` FOREIGN KEY (`proCodigo`) REFERENCES `produto` (`proCodigo`),
  ADD CONSTRAINT `fk3` FOREIGN KEY (`undCodigo`) REFERENCES `unidademedida` (`undCodigo`);

--
-- Restrições para tabelas `tabela_nutricional`
--
ALTER TABLE `tabela_nutricional`
  ADD CONSTRAINT `FK1irptutr0pc62u25g9obpknxg` FOREIGN KEY (`proCodigo`) REFERENCES `produto` (`proCodigo`),
  ADD CONSTRAINT `FK8o27e3w6523dwtt89mnowurej` FOREIGN KEY (`undCodigo`) REFERENCES `unidade_medida` (`undCodigo`);

--
-- Restrições para tabelas `tabnutelemento`
--
ALTER TABLE `tabnutelemento`
  ADD CONSTRAINT `fk4` FOREIGN KEY (`eleCodigo`) REFERENCES `elemento` (`eleCodigo`),
  ADD CONSTRAINT `fk5` FOREIGN KEY (`tabCodigo`) REFERENCES `tabelanutricional` (`tabCodigo`);

--
-- Restrições para tabelas `tab_nut_elemento`
--
ALTER TABLE `tab_nut_elemento`
  ADD CONSTRAINT `FK1jo4pd6u1p8272wvu52u7xxkt` FOREIGN KEY (`tabCodigo`) REFERENCES `tabela_nutricional` (`tabCodigo`),
  ADD CONSTRAINT `FKamjhl8plqy0pwjhchm0jifbay` FOREIGN KEY (`eleCodigo`) REFERENCES `elemento` (`eleCodigo`),
  ADD CONSTRAINT `FKihlpyk6dlsfwq3opp49psf6lr` FOREIGN KEY (`tabCodigo`) REFERENCES `tabelanutricional` (`tabCodigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
