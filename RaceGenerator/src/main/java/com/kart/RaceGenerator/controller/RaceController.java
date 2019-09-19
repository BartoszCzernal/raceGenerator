package com.kart.RaceGenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kart.RaceGenerator.model.Configuration;
import com.kart.RaceGenerator.service.RaceService;

@Controller
public class RaceController {

	private RaceService raceService;
	
	public RaceController(RaceService raceService) {
		this.raceService = raceService;
	}

	@GetMapping("/")
	public String start() {

		return "index";
	}

	@GetMapping("/form")
	public String showForm(Model model) {
		Configuration configuration = new Configuration();
		configuration = raceService.prepareForForm(configuration);
		
		System.out.println("BEFORE");
		System.out.println(configuration.getGroups());
		System.out.println(configuration.getKarts());
		System.out.println(configuration.getStints());

		model.addAttribute("configuration", configuration);
		
		return "conf-form";
	}
	
	@RequestMapping(value="/form", params= {"addKart"})
	public String addKart(@ModelAttribute Configuration configuration) {
		System.out.println("AFTER");
		System.out.println(configuration.getGroups());
		System.out.println(configuration.getKarts());
		System.out.println(configuration.getStints());
		configuration.addKart(configuration.getKarts().size() + 1 + "");
		return "conf-form";
	}

}
