package com.kart.RaceGenerator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Group {

	private String name;

	private List<Driver> drivers;

	public Group() {
	}

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

	public void pickKartsForDrivers() {
		Configuration configuration = Configuration.getInstance();
		if (configuration.getKarts() == null || this.getDrivers() == null) {
			return;
		}
		List<String> kartsNames = new ArrayList<>(configuration.getKarts());
		if (kartsNames.isEmpty() || kartsNames.equals(null)) {
			return;
		}

		while(true) {
			if(pickKarts(configuration, kartsNames)) {
				break;
			}
			kartsNames = new ArrayList<>(configuration.getKarts());
		}
	}

	private boolean pickKarts(Configuration configuration, List<String> kartsNames) {
		Random random = new Random();
		int kartId = 0;
		for (int i = 0; i < configuration.getStints(); i++) {
			for (int k = 0; k < drivers.size(); k++) {
				Driver driver = drivers.get(k);
				if (i == 0) {
					driver.setKartsUsed(new ArrayList<>());
				}
				List<String> kartsLeft = new ArrayList<>(kartsNames);
				kartsLeft.removeAll(driver.getKartsUsed());
				if (kartsLeft.isEmpty()) {
					System.out.println("Nie udalo sie");
					return false;
				}
				kartId = random.nextInt(kartsLeft.size());
				String kart = kartsLeft.get(kartId);
				kartsLeft.remove(kart);
				kartsNames.remove(kart);
				driver.addKartUsed(kart);
			}
			kartsNames = new ArrayList<>(configuration.getKarts());
		}
		return true;
	}

}
