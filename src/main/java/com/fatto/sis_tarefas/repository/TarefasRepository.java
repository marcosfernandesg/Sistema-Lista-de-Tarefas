package com.fatto.sis_tarefas.repository;

import com.fatto.sis_tarefas.entity.Tarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TarefasRepository extends JpaRepository<Tarefas, Long> {

    List<Tarefas> findAllByOrderByOrdemApresentacaoAsc();

    Tarefas findByOrdemApresentacao(Integer ordemApresentacao);
}
