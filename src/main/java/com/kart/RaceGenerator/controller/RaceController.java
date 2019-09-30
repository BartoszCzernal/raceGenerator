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

		return "redirect:/form";
	}
	
	@GetMapping("/form")
	public String showForm(Model model) {
		Configuration configuration = new Configuration();
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
		if (configuration.getKarts() == null || configuration.getKarts().isEmpty()) {
			bindingResult.rejectValue("karts", "error.karts", "Brak kartów!");
			return "conf-form";
		}
		if (configuration.getGroups() == null || configuration.getGroups().isEmpty()) {
			bindingResult.rejectValue("groups", "error.groups", "Brak grup!");
			return "conf-form";
		}
		for (Group group : configuration.getGroups()) {
			if (group.getDrivers() == null || group.getDrivers().isEmpty()) {
				bindingResult.rejectValue("groups", "error.groups", "Brak kierowców!");
				return "conf-form";
			} else if (group.getDrivers().size() > configuration.getKarts().size()) {
				bindingResult.rejectValue("groups", "error.groups", 
						"Liczba kierowców w grupie jest większa od liczby gokartów!");
				return "conf-form";
			}
			if (group.getDrivers().size() < configuration.getStints()) {
				bindingResult.rejectValue("stints", "error.stints",
						"Liczba stintów jest za duża w porównaniu do liczby zawodników w grupie");
				return "conf-form";
			}
		}
		
		for (Group groupPick : configuration.getGroups()) {
			groupPick.pickKartsForDrivers(configuration);
		}
		model.addAttribute(configuration);
		return "result";
	}
}





