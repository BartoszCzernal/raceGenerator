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
	public String start(Model model) {
		Configuration configuration = raceService.getConfiguration();
		
		model.addAttribute("configuration", configuration);

		return "index";
	}

}
