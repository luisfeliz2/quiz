package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HolaMundowebController {

  @GetMapping("/saludo")
  public String saludoForm(Model modelo) {
    modelo.addAttribute("saludo", new Saludo());
    return "saludo";
  }

  @PostMapping("/saludo")
  public String envioSaludo(@ModelAttribute Saludo saludo, Model model) {
    model.addAttribute("saludo", saludo);
    return "resultado";
  }

}
