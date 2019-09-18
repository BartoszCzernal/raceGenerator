package com.kart.RaceGenerator.model;

import java.util.HashSet;
import java.util.Set;

public class Group implements Comparable<String> {
	
	private String name;

	private Set<Driver> drivers;
	
	public Group() {}
	
	public Group(String name) {
		this.name = name;
	}
	
	public Group(String name, Set<Driver> drivers) {
		this.name = name;
		this.drivers = drivers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(Set<Driver> drivers) {
		this.drivers = drivers;
	}
	
	public boolean addDriver(Driver driver) {
		if (drivers == null) {
			drivers = new HashSet<>();
		}
		driver.setGroup(this);
		return drivers.add(driver);
	}

	@Override
	public int compareTo(String name) {
		return this.name.compareToIgnoreCase(name);
	}
}
