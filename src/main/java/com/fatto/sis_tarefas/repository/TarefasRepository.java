package com.fatto.sis_tarefas.repository;

import com.fatto.sis_tarefas.domain.Tarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TarefasRepository extends JpaRepository<Tarefas, Long> {

    boolean existsByNome(String nome);

    Optional<Tarefas> findByOrdemApresentacao(Integer ordem);

    List<Tarefas> findAllByOrderByOrdemApresentacaoAsc();

    @Query("SELECT MAX(t.ordemApresentacao) FROM Tarefas t")
    Integer findMaxOrdem();

    @Query("SELECT SUM(t.custo) FROM Tarefas t")
    BigDecimal somarCustos();
}
