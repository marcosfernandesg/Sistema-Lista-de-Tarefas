package com.fatto.sis_tarefas.service;

import com.fatto.sis_tarefas.entity.Tarefas;
import com.fatto.sis_tarefas.repository.TarefasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TarefasService {

    private final TarefasRepository repository;

    public TarefasService(TarefasRepository repository) {
        this.repository = repository;
    }

    public List<Tarefas> listarTodas() {
        return repository.findAllByOrderByOrdemApresentacaoAsc();
    }

    @Transactional
    public void salvar(Tarefas tarefa) {
        // Validação de Nome Duplicado (Requisito do sistema)
        Optional<Tarefas> tarefaExistente = repository.findByNome(tarefa.getNome());

        // Se achou o nome, verifica se não é o próprio registro sendo editado
        if (tarefaExistente.isPresent()) {
            if (tarefa.getId() == null || !tarefa.getId().equals(tarefaExistente.get().getId())) {
                throw new RuntimeException("Já existe uma tarefa com este nome.");
            }
        }

        // Se for inclusão (ID nulo), define a ordem como a última + 1
        if (tarefa.getId() == null) {
            Integer proximaOrdem = repository.findMaxOrdem() + 1;
            tarefa.setOrdemApresentacao(proximaOrdem);
        }

        repository.save(tarefa);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Tarefas buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public BigDecimal obterTotalCustos() {
        return repository.findAll()
                .stream()
                .map(t -> t.getCusto() != null ? t.getCusto() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void subir(Long id) {
        Tarefas atual = buscarPorId(id);
        if (atual.getOrdemApresentacao() <= 1) return;

        Tarefas anterior = repository.findByOrdemApresentacao(atual.getOrdemApresentacao() - 1);
        if (anterior != null) {
            int ordemTemp = atual.getOrdemApresentacao();
            atual.setOrdemApresentacao(anterior.getOrdemApresentacao());
            anterior.setOrdemApresentacao(ordemTemp);
            repository.save(anterior);
            repository.save(atual);
        }
    }

    @Transactional
    public void descer(Long id) {
        Tarefas atual = buscarPorId(id);
        Tarefas proxima = repository.findByOrdemApresentacao(atual.getOrdemApresentacao() + 1);

        if (proxima != null) {
            int ordemTemp = atual.getOrdemApresentacao();
            atual.setOrdemApresentacao(proxima.getOrdemApresentacao());
            proxima.setOrdemApresentacao(ordemTemp);
            repository.save(proxima);
            repository.save(atual);
        }
    }
}