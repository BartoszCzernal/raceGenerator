package com.kart.RaceGenerator.service;

import javax.validation.Valid;

import com.kart.RaceGenerator.model.Configuration;

public interface RaceService {

	Configuration prepareForForm(Configuration configuration);

	Configuration addNextGroup(Configuration configuration);

	@Valid
	Configuration trimEmpty(@Valid Configuration configuration);

	
}
