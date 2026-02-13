package com.fatto.sis_tarefas.controller;

import org.springframework.ui.Model;
import com.fatto.sis_tarefas.domain.Tarefas;
import com.fatto.sis_tarefas.service.TarefasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService service;

    @GetMapping("/")
    public String listar(Model model) {
        model.addAttribute("tarefas", service.listarTodas());
        model.addAttribute("total", service.obterTotalCustos());
        model.addAttribute("tarefas", new Tarefas());
        return "lista";
    }

    @PostMapping("/criar")
    public String criar(@Valid @ModelAttribute Tarefas tarefas,
                        BindingResult result) {

        if (result.hasErrors()) {
            return "lista";
        }

        service.criar(tarefas);
        return "redirect:/";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         @Valid Tarefas tarefas,
                         BindingResult result) {

        if (result.hasErrors()) {
            return "lista";
        }

        service.atualizar(id, tarefas);
        return "redirect:/";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/";
    }

    @GetMapping("/subir/{id}")
    public String subir(@PathVariable Long id) {
        service.subir(id);
        return "redirect:/";
    }

    @GetMapping("/descer/{id}")
    public String descer(@PathVariable Long id) {
        service.descer(id);
        return "redirect:/";
    }
}
