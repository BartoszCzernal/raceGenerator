package com.kart.RaceGenerator.service;

import org.springframework.stereotype.Service;

import com.kart.RaceGenerator.model.Configuration;
import com.kart.RaceGenerator.model.Driver;
import com.kart.RaceGenerator.model.Group;

@Service
public class RaceServiceImpl implements RaceService {

	@Override
	public Configuration prepareForForm(Configuration configuration) {

		Group group = new Group("Grupa A");
		for (int i = 1; i <= 6; i++) {
			group.addDriver(new Driver());
			configuration.addKart(i + "");
		}
		configuration.addGroup(group);

		return configuration;
	}

	@Override
	public Configuration addNextGroup(Configuration configuration) {
		char groupNameChar = 'A';
		if (configuration.getGroups() != null) {
			groupNameChar = (char) (groupNameChar + configuration.getGroups().size());
		}
		String groupName = "Grupa " + groupNameChar;
		Group group = new Group(groupName);
		for (int i = 1; i <= 6; i++) {
			group.addDriver(new Driver());
		}
		configuration.addGroup(group);
		return configuration;
	}

}
