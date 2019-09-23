package com.kart.RaceGenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kart.RaceGenerator.model.Configuration;
import com.kart.RaceGenerator.model.Driver;
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
		model.addAttribute("configuration", configuration);
		
		return "conf-form";
	}
	
	@RequestMapping(value="/form", params= {"addKart"})
	public String addKart(@ModelAttribute Configuration configuration) {
		configuration.addKart(configuration.getKarts().size() + 1 + "");
		return "conf-form";
	}
	
	@RequestMapping(value="/form", params= {"addGroup"})
	public String addGroup(@ModelAttribute Configuration configuration) {
		configuration = raceService.addNextGroup(configuration);
		return "conf-form";
	}
	
	@RequestMapping(value="/form", params= {"addDriver"})
	public String addDriver(@ModelAttribute Configuration configuration,
							@RequestParam(value="addDriver") int id) {
		configuration.getGroups().get(id).addDriver(new Driver());
		return "conf-form";
	}
	
	@PostMapping("/form")
	public String save(@ModelAttribute Configuration configuration,
						Model model) {
		System.out.println(configuration.getKarts());
		System.out.println(configuration.getGroups().get(0).getDrivers());
		System.out.println(configuration.getStints());
		model.addAttribute(configuration);
		return "result";
	}

}



