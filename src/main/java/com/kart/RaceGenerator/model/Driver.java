package com.kart.RaceGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Driver {

	private String name;
	private Group group;
	private List<String> kartsUsed = new ArrayList<>();
	
	public Driver() {}
	
	public Driver(String name) {
		this.name = name;
	}
	
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

	
	public List<String> getKartsUsed() {
		return kartsUsed;
	}

	public void setKartsUsed(List<String> kartsUsed) {
		this.kartsUsed = kartsUsed;
	}

	public boolean addKartUsed(String kartName) {
			return kartsUsed.add(kartName);
	}

}
