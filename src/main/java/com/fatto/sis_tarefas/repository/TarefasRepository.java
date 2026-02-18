package com.fatto.sis_tarefas.repository;

import com.fatto.sis_tarefas.entity.Tarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface TarefasRepository extends JpaRepository<Tarefas, Long> {

    List<Tarefas> findAllByOrderByOrdemApresentacaoAsc();

    Tarefas findByOrdemApresentacao(Integer ordemApresentacao);

    // Requisito: Não permitir duplicidade de nome
    Optional<Tarefas> findByNome(String nome);

    // Para encontrar a última ordem e colocar o novo registro no final
    @Query("SELECT COALESCE(MAX(t.ordemApresentacao), 0) FROM Tarefas t")
    Integer findMaxOrdem();
}