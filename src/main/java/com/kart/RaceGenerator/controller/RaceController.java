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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		configuration = raceService.prepareForForm(configuration);
		
		model.addAttribute("configuration", configuration);
		
		return "conf-form";
	}
	@PostMapping("/raceForm")
	public String editForm(@ModelAttribute("configuration") Configuration configuration,
							RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("configuration", configuration);
		return "redirect:/raceForm";
	}
	
	@GetMapping("/raceForm")
	public String showFormAgain(@ModelAttribute("configuration") Configuration configuration) {
		return "conf-form";
	}
	
	@PostMapping("/form/delete")
	public String deleteDriver(@ModelAttribute Configuration configuration,
							   @RequestParam("groupId") int groupId,
							   @RequestParam("driverId") int driverId,
							   RedirectAttributes redirectAttributes) {
		configuration.getGroups().get(groupId).removeDriver(driverId);
		redirectAttributes.addFlashAttribute("configuration", configuration);
		return "redirect:/raceForm";
	}
	
	@RequestMapping(value="/form", params= {"addKart"})
	public String addKart(@Valid @ModelAttribute Configuration configuration, 
						  RedirectAttributes redirectAttributes) {
		if(configuration.getKarts().size() < 30) {
			configuration.addKart("");
		}
		redirectAttributes.addFlashAttribute("configuration", configuration);
		return "redirect:/raceForm";
	}
	
	@RequestMapping(value="/form", params= {"deleteKart"})
	public String deleteKart(@ModelAttribute Configuration configuration,
							 @RequestParam("deleteKart") int id,
							 RedirectAttributes redirectAttributes) {
		configuration.removeKart(id);
		redirectAttributes.addFlashAttribute("configuration", configuration);
		return "redirect:/raceForm";
	}
	
	@RequestMapping(value="/form", params= {"addGroup"})
	public String addGroup(@Valid @ModelAttribute Configuration configuration,
						   RedirectAttributes redirectAttributes) {
		configuration = raceService.addNextGroup(configuration);
		redirectAttributes.addFlashAttribute("configuration", configuration);
		return "redirect:/raceForm";
	}
	
	@RequestMapping(value="/form", params= {"deleteGroup"})
	public String deleteGroup(@ModelAttribute Configuration configuration,
							  @RequestParam("deleteGroup") int id,
							  RedirectAttributes redirectAttributes) {
		configuration.removeGroup(id);
		redirectAttributes.addFlashAttribute("configuration", configuration);
		return "redirect:/raceForm";
	}
	@RequestMapping(value="/form", params= {"addDriver"})
	public String addDriver(@Valid @ModelAttribute Configuration configuration,
							@RequestParam("addDriver") int id,
							RedirectAttributes redirectAttributes) {
		if (configuration.getGroups() != null) {
			if (configuration.getGroups().get(id).getDrivers().size() < 30) {
				configuration.getGroups().get(id).addDriver(new Driver());
			}
		}
		redirectAttributes.addFlashAttribute("configuration", configuration);
		return "redirect:/raceForm";
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
			} // porownac ilosc stintow z iloscia gokartów, a nie iloscia zawodników
//			if (group.getDrivers().size() < configuration.getStints()) {
//				bindingResult.rejectValue("stints", "error.stints",
//						"Liczba stintów jest za duża w porównaniu do liczby zawodników w grupie");
//				return "conf-form";
//			}
		}
		if (configuration.getStints() > configuration.getKarts().size()) {
			bindingResult.rejectValue("stints", "error.stints", "Liczba stintów jest za duża w porównaniu do liczby gokartów");
			return "conf-form";
		}
		
		for (Group groupPick : configuration.getGroups()) {
			groupPick.pickKartsForDrivers(configuration);
		}
		model.addAttribute(configuration);
		return "result";
	}
}





