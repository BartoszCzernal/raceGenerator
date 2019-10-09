package com.kart.RaceGenerator.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.kart.RaceGenerator.model.Configuration;
import com.kart.RaceGenerator.model.Driver;
import com.kart.RaceGenerator.model.Group;

@Service
public class RaceServiceImpl implements RaceService {

	@Override
	public Configuration prepareForForm(Configuration configuration) {
		configuration.setKarts(null);
		configuration.setGroups(null);
		Group group = new Group("Grupa A");
		for (int i = 1; i <= 2; i++) {
			group.addDriver(new Driver());
			configuration.addKart("");
		}
		configuration.addGroup(group);
		configuration.setStints(1);

		return configuration;
	}

	@Override
	public Configuration addNextGroup(Configuration configuration) {
		char groupNameChar = 'A';
		if (configuration.getGroups() != null) {
			if (configuration.getGroups().size() >= 30) {
				return configuration;
			}
			groupNameChar = (char) (groupNameChar + configuration.getGroups().size());
		}
		String groupName = "Grupa " + groupNameChar;
		Group group = new Group(groupName);
		for (int i = 1; i <= 2; i++) {
			group.addDriver(new Driver());
		}
		configuration.addGroup(group);
		return configuration;
	}

	@Override
	public @Valid Configuration trimEmpty(@Valid Configuration configuration) {
		if (configuration.getKarts() != null) {
			trimKarts(configuration);
		}
		List<ArrayList<Driver>> emptyDrivers = new ArrayList<ArrayList<Driver>>();
		List<Group> emptyGroups = new ArrayList<>();
		if (configuration.getGroups() == null) {
			return configuration;
		}
		for (Group group : configuration.getGroups()) {
			if (group.getDrivers() == null) {
				continue;
			}
			int index = configuration.getGroups().indexOf(group);
			emptyDrivers.add(index, new ArrayList<>());
			for (Driver driver : group.getDrivers()) {
				if (driver.getName() == null || driver.getName().trim().isEmpty()) {
					emptyDrivers.get(index).add(driver);
				}
			}
			if (emptyDrivers.get(index).size() == group.getDrivers().size()) {
				emptyGroups.add(group);
			}
		}
		
		for (List<Driver> list : emptyDrivers) {
			int index = emptyDrivers.indexOf(list);
			for (Driver emptyDriver : list) {
				configuration.getGroups().get(index).removeDriver(emptyDriver);
			}
		}
		
		for (Group group : emptyGroups) {
			configuration.removeGroup(group);
		}

		return configuration;
	}

	private void trimKarts(Configuration configuration) {
		List<String> emptyKarts = new ArrayList<>();
		
		for (String kart : configuration.getKarts()) {
			if (kart == null || kart.trim().isEmpty()) {
				emptyKarts.add(kart);
			}
		}
		for (String kart : emptyKarts) {
			configuration.removeKart(kart);
		}
	}

}
