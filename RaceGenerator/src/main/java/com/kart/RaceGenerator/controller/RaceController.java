package com.kart.RaceGenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

	@GetMapping("/showForm")
	public String showForm(Model model) {
		Configuration configuration = new Configuration();

		model.addAttribute("configuration", configuration);
		
		return "conf-form";
	}

}
