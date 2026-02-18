package com.fatto.sis_tarefas.controller;

import com.fatto.sis_tarefas.entity.Tarefas;
import com.fatto.sis_tarefas.service.TarefasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class TarefasController {

    private final TarefasService service;

    public TarefasController(TarefasService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tarefas", service.listarTodas());
        model.addAttribute("total", service.obterTotalCustos());
        // Se nao houver uma tarefa de edicao no model, cria uma nova
        if (!model.containsAttribute("novaTarefa")) {
            model.addAttribute("novaTarefa", new Tarefas());
        }
        return "lista";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Tarefas tarefa, RedirectAttributes redirectAttributes) {
        try {
            service.salvar(tarefa);
        } catch (RuntimeException e) {
            // Se der erro de nome duplicado, manda a mensagem de volta
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            redirectAttributes.addFlashAttribute("novaTarefa", tarefa);
        }
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Tarefas tarefa = service.buscarPorId(id);
        model.addAttribute("tarefas", service.listarTodas());
        model.addAttribute("total", service.obterTotalCustos());
        model.addAttribute("novaTarefa", tarefa); // Aqui preenche o form para editar
        return "lista";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
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