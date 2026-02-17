package com.fatto.sis_tarefas.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class Tarefas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer ordemApresentacao;

    @Column(nullable = false)
    private BigDecimal custo;

    @Column(name = "data_limite", nullable = false)
    private LocalDate dataLimite;

    // ===== GETTERS E SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {   // <<< ESSENCIAL PARA EDITAR
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getOrdemApresentacao() {
        return ordemApresentacao;
    }

    public void setOrdemApresentacao(Integer ordemApresentacao) {
        this.ordemApresentacao = ordemApresentacao;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }
}
