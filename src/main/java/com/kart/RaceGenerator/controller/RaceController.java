package com.kart.RaceGenerator.controller;



import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kart.RaceGenerator.model.Configuration;
import com.kart.RaceGenerator.model.Driver;
import com.kart.RaceGenerator.model.Group;
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
		Configuration configuration = Configuration.getInstance();
		if (configuration.getGroups() == null) {
			configuration = raceService.prepareForForm(configuration);
		}
		model.addAttribute("configuration", configuration);
		
		return "conf-form";
	}
	
	@RequestMapping(value="/form", params= {"addKart"})
	public String addKart(@ModelAttribute Configuration configuration) {
		if (configuration.getKarts() != null) {
			configuration.addKart(configuration.getKarts().size() + 1 + "");
		} else {
			configuration.addKart("1");
		}
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
		if (configuration.getGroups() != null) {
			configuration.getGroups().get(id).addDriver(new Driver());
		}
		return "conf-form";
	}
	
	@PostMapping("/form")
	public String save(@Valid @ModelAttribute("configuration") Configuration configuration, BindingResult bindingResult,
						Model model) {
		if (bindingResult.hasErrors()) {
			return "conf-form";
		}
		configuration = raceService.trimEmpty(configuration);
		//walidacja
		//result.rejectValue
		for (Group group : configuration.getGroups()) {
			group.pickKartsForDrivers(configuration);
		}
		model.addAttribute(configuration);
		return "result";
	}

}



