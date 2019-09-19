package com.kart.RaceGenerator.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kart.RaceGenerator.model.Configuration;
import com.kart.RaceGenerator.model.Driver;
import com.kart.RaceGenerator.model.Group;

@Service
public class RaceServiceImpl implements RaceService {

	@Override
	public Configuration prepareForForm(Configuration configuration) {
		List<Group> group = new ArrayList<>();
		for(int i = 1; i <= 6; i++) {
			configuration.addKart("" + i);
			group.add(new Group("grupa" + i));
			group.get(i-1).addDriver(new Driver("driver1"));
			group.get(i-1).addDriver(new Driver("driver2"));
			configuration.addGroup(group.get(i-1));
		}
		
		return configuration;
	}
	
	
}
