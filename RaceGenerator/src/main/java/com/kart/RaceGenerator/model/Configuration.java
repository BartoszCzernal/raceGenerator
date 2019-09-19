package com.kart.RaceGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Configuration {

	private List<String> karts;
	private List<Group> groups;
	private int stints;
	
	public Configuration() {}

	public Configuration(List<String> karts, List<Group> groups, int stints) {
		this.karts = karts;
		this.groups = groups;
		this.stints = stints;
	}

	public List<String> getKarts() {
		return karts;
	}

	public void setKarts(List<String> karts) {
		this.karts = karts;
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public int getStints() {
		return stints;
	}

	public void setStints(int stints) {
		this.stints = stints;
	}
	
	public boolean addKart(String kartName) {
		if (karts == null) {
			karts = new ArrayList<>();
		}
		return karts.add(kartName);
	}

	public boolean addGroup(Group group) {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		return groups.add(group);
	}
	
}
