package com.kart.RaceGenerator.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;




public class Configuration {

	private List<String> karts;
	private List<Group> groups;
	@Min(1)
	@Max(30)
	private int stints;
	
	public Configuration() {}

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
	
	public void removeKart(int id) {
		karts.remove(id);
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
	
	public void removeGroup(int id) {
		groups.remove(id);
	}
	
}
