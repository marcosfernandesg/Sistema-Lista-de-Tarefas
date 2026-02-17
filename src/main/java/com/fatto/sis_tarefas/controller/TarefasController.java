package com.fatto.sis_tarefas.controller;

import com.fatto.sis_tarefas.entity.Tarefas;
import com.fatto.sis_tarefas.service.TarefasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("novaTarefa", new Tarefas());
        return "lista";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Tarefas tarefa) {
        service.salvar(tarefa);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("tarefas", service.listarTodas());
        model.addAttribute("total", service.obterTotalCustos());
        model.addAttribute("novaTarefa", service.buscarPorId(id));
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
