package com.kart.RaceGenerator.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;




public class Configuration {
	
	private static volatile Configuration configuration;

	private List<String> karts;
	private List<Group> groups;
	@Min(1)
	private int stints;
	
	private Configuration() {}

	public static Configuration getInstance() {
		if (configuration == null) {
			synchronized (Configuration.class) {
				if (configuration == null) {
					configuration = new Configuration();
				}
			}
		}
		return configuration;
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
	
	public boolean removeKart(String kartName) {
		return karts.remove(kartName);
	}

	public boolean addGroup(Group group) {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		return groups.add(group);
	}
	
	public boolean removeGroup(Group group) {
		return groups.remove(group);
	}
	
}
