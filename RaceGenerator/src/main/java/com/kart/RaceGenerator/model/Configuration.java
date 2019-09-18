package com.kart.RaceGenerator.model;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

public class Configuration {

	private Set<String> karts;
	private Set<Group> groups;
	private int stints;
	
	public Configuration() {}

	public Configuration(Set<String> karts, Set<Group> groups, int stints) {
		this.karts = karts;
		this.groups = groups;
		this.stints = stints;
	}

	public Set<String> getKarts() {
		return karts;
	}

	public void setKarts(Set<String> karts) {
		this.karts = karts;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public int getStints() {
		return stints;
	}

	public void setStints(int stints) {
		this.stints = stints;
	}
	
	public boolean addKart(String kartName) {
		return karts.add(kartName);
	}

	public boolean addGroup(Group group) {
		if (groups == null) {
			groups = new TreeSet<>();
		}
		return groups.add(group);
	}
	
}
