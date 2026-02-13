package com.fatto.sis_tarefas.service;

import com.fatto.sis_tarefas.domain.Tarefas;
import com.fatto.sis_tarefas.repository.TarefasRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository repository;

    public List<Tarefas> listarTodas() {
        return repository.findAllByOrderByOrdemApresentacaoAsc();
    }

    public BigDecimal obterTotalCustos() {
        BigDecimal total = repository.somarCustos();
        return total != null ? total : BigDecimal.ZERO;
    }

    @Transactional
    public Tarefas criar(Tarefas tarefas) {

        if (repository.existsByNome(tarefas.getNome())) {
            throw new RuntimeException("Já existe tarefa com esse nome");
        }

        Integer ultimaOrdem = repository.findMaxOrdem();
        tarefas.setOrdemApresentacao(
                ultimaOrdem == null ? 1 : ultimaOrdem + 1
        );

        return repository.save(tarefas);
    }

    @Transactional
    public Tarefas atualizar(Long id, Tarefas novaTarefa) {

        Tarefas existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (!existente.getNome().equals(novaTarefa.getNome())
                && repository.existsByNome(novaTarefa.getNome())) {
            throw new RuntimeException("Já existe tarefa com esse nome");
        }

        existente.setNome(novaTarefa.getNome());
        existente.setCusto(novaTarefa.getCusto());
        existente.setDataLimite(novaTarefa.getDataLimite());

        return repository.save(existente);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void subir(Long id) {

        Tarefas atual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (atual.getOrdemApresentacao() == 1) {
            return;
        }

        Tarefas anterior = repository
                .findByOrdemApresentacao(atual.getOrdemApresentacao() - 1)
                .orElseThrow();

        int ordemAtual = atual.getOrdemApresentacao();

        atual.setOrdemApresentacao(ordemAtual - 1);
        anterior.setOrdemApresentacao(ordemAtual);
    }

    @Transactional
    public void descer(Long id) {

        Tarefas atual = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        Integer maxOrdem = repository.findMaxOrdem();

        if (atual.getOrdemApresentacao().equals(maxOrdem)) {
            return;
        }

        Tarefas proxima = repository
                .findByOrdemApresentacao(atual.getOrdemApresentacao() + 1)
                .orElseThrow();

        int ordemAtual = atual.getOrdemApresentacao();

        atual.setOrdemApresentacao(ordemAtual + 1);
        proxima.setOrdemApresentacao(ordemAtual);
    }
}
