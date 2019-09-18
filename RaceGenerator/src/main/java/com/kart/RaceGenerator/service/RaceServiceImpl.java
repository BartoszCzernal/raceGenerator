package com.kart.RaceGenerator.service;

import org.springframework.stereotype.Service;

import com.kart.RaceGenerator.model.Configuration;

@Service
public class RaceServiceImpl implements RaceService {
	
	private Configuration configuration;
	
	public RaceServiceImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Configuration getConfiguration() {
		return configuration;
	}
	
	
}
