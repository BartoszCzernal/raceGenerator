package com.kart.RaceGenerator.model;

import org.springframework.stereotype.Component;

@Component
public class Driver {

	private String name;
	private Group group;
	
	public Driver() {}
	
	public Driver(String name, Group group) {
		this.name = name;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
