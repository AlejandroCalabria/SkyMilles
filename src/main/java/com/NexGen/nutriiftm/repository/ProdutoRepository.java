package com.NexGen.nutriiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.NexGen.nutriiftm.model.Fabricante;
import com.NexGen.nutriiftm.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
        // SELECT * FROM produto WHERE pro_nome LIKE '%valor%'
    List<Produto> findByProNomeContaining(String nome);

    // SELECT * FROM produto WHERE fabricante_id = ?
    List<Produto> findByFabricante(Fabricante fabricante);


}
//interface: significa que é um contrato, uma promessa de que a classe que implementar essa interface vai fornecer as funcionalidades definidas nela.
//extends: significa que a interface ProdutoRepository está estendendo (ou seja, herdando) as funcionalidades da interface JpaRepository. 
//JpaRepository: é uma interface do Spring Data JPA que fornece métodos prontos para realizar operações de CRUD (Create, Read, Update, Delete) e outras
// operações comuns em entidades JPA.
//Produto: é a entidade que esta interface vai gerenciar, ou seja, a tabela do banco de dados que ela vai representar.
//Long: é o tipo do identificador da entidade Produto, ou seja, o tipo do campo que é a chave primária da tabela Produto. Neste caso, o campo proCodigo é do
// tipo long, então usamos Long aqui.