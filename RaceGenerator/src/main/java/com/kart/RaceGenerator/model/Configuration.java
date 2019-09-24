package com.kart.RaceGenerator.model;

import java.util.ArrayList;
import java.util.List;



public class Configuration {
	
	private static volatile Configuration configuration;

	private static List<String> karts;
	private static List<Group> groups;
	private static int stints;
	
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
		Configuration.karts = karts;
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		Configuration.groups = groups;
	}

	public int getStints() {
		return stints;
	}

	public void setStints(int stints) {
		Configuration.stints = stints;
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
