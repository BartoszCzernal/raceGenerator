package com.kart.RaceGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String name;

	private List<Driver> drivers;
	
	public Group() {}
	
	public Group(String name) {
		this.name = name;
	}
	
	public Group(String name, List<Driver> drivers) {
		this.name = name;
		this.drivers = drivers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}
	
	public List<Driver> getDriversAsList() {
		return new ArrayList<Driver>(drivers);
	}
	
	public boolean addDriver(Driver driver) {
		if (drivers == null) {
			drivers = new ArrayList<>();
		}
		driver.setGroup(this);
		return drivers.add(driver);
	}

}
