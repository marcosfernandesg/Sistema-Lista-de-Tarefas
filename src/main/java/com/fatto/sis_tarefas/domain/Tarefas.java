package com.fatto.sis_tarefas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tarefas",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nome"),
                @UniqueConstraint(columnNames = "ordem_apresentacao")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarefas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Custo é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Custo deve ser maior ou igual a zero")
    private BigDecimal custo;

    @Column(nullable = false)
    @NotNull(message = "Data limite é obrigatória")
    private LocalDate dataLimite;

    @Column(name = "ordem_apresentacao",
            nullable = false,
            unique = true)
    private Integer ordemApresentacao;
}
