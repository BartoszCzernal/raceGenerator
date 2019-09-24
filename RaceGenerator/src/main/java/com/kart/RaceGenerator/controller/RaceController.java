package com.kart.RaceGenerator.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	private Configuration configuration;
	
	public RaceController(RaceService raceService) {
		this.raceService = raceService;
		this.configuration = Configuration.getInstance();
	}

	@GetMapping("/")
	public String start() {

		return "index";
	}

	@GetMapping("/form")
	public String showForm(Model model) {
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
	public String save(@ModelAttribute Configuration configuration,
						Model model) {
		List<Group> groups = configuration.getGroups();
		for (Group group : groups) {
			group.pickKartsForDrivers();
		}
		System.out.println(configuration.getKarts());
		for (Group group : groups) {
			List<Driver> drivers = group.getDrivers();
			System.out.println(group.getName());
			for (Driver driver : drivers) {
				List<String> karts = driver.getKartsUsed();
				System.out.print(driver.getName() + ": ");
				for (String kart : karts) {
					System.out.print(kart + ", ");
				}
				System.out.println();
			}
		}
		model.addAttribute(configuration);
		return "result";
	}

}



