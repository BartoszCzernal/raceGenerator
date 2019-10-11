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

	public boolean addDriver(Driver driver) {
		if (drivers == null || drivers.isEmpty()) {
			drivers = new ArrayList<>();
		}
		driver.setGroup(this);
		return drivers.add(driver);
	}

	public boolean removeDriver(Driver driver) {
		return drivers.remove(driver);
	}

	public void removeDriver(int id) {
		drivers.remove(id);
	}

	public boolean pickKartsForDrivers(Configuration configuration) {
		if (configuration.getKarts() == null || this.getDrivers() == null || this.getDrivers().isEmpty()) {
			return false;
		}
		List<String> kartsNames = new ArrayList<>(configuration.getKarts());
		if (kartsNames.isEmpty()) {
			return false;
		}

		int stints = configuration.getStints();
		int howManyDrivers = drivers.size();
		int howManyKarts = kartsNames.size();

		if (howManyDrivers > howManyKarts) {
			return false;
		}

		if (stints > howManyKarts) {
			List<String[][]> uniqueMatrices = new ArrayList<>();
			int count = stints / howManyKarts;
			int rest = stints % howManyKarts;
			for (int i = 0; i < count; i++) {
				uniqueMatrices.add(pickUniqueKarts(howManyKarts, howManyDrivers, kartsNames));
			}
			if (rest > 0) {
				uniqueMatrices.add(pickUniqueKarts(rest, howManyDrivers, kartsNames));
				count++;
			}

			String[][] result = new String[stints][];
			int previousLength = 0;
			for (int j = 0; j < count; j++) {
				if (j != 0) {
					previousLength += uniqueMatrices.get(j - 1).length;
				}
				System.arraycopy(uniqueMatrices.get(j), 0, result, previousLength, uniqueMatrices.get(j).length);
			}
			this.fillKartsForDrivers(result);
			printArray(result);
			return true;
		}
		String[][] matrix = pickUniqueKarts(stints, howManyDrivers, kartsNames);
		printArray(matrix);
		this.fillKartsForDrivers(matrix);

		return true;

//		while(true) {
//			if(pickKarts(configuration, kartsNames)) {
//				return true;
//			}
//			kartsNames = new ArrayList<>(configuration.getKarts());
//		}
	}

//	private boolean pickKarts(Configuration configuration, List<String> kartsNames) {
//		Random random = new Random();
//		int kartId = 0;
//		for (int i = 0; i < configuration.getStints(); i++) {
//			for (int k = 0; k < drivers.size(); k++) {
//				Driver driver = drivers.get(k);
//				if (i == 0) {
//					driver.setKartsUsed(new ArrayList<>());
//				}
//				List<String> kartsLeft = new ArrayList<>(kartsNames);
//				kartsLeft.removeAll(driver.getKartsUsed());
//				if (kartsLeft.isEmpty()) {
//					return false;
//				}
//				kartId = random.nextInt(kartsLeft.size());
//				String kart = kartsLeft.get(kartId);
//				kartsLeft.remove(kart);
//				kartsNames.remove(kart);
//				driver.addKartUsed(kart);
//			}
//			kartsNames = new ArrayList<>(configuration.getKarts());
//		}
//		return true;
//	}

	private void fillKartsForDrivers(String[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				Driver driver = drivers.get(j);
				driver.addKartUsed(matrix[i][j]);
			}
		}
		
	}

	private static String[][] pickUniqueKarts(int stints, int howManyDrivers, List<String> kartsNames) {
		String[][] kartsMatrix = new String[stints][howManyDrivers];

		for (int i = 0; i < stints; i++) {
			int count = 0;
			while (!pickKartsForStint(howManyDrivers, new ArrayList<>(kartsNames), kartsMatrix, i)) {
				count++;
				if (count > 5) {
					i = 0;
				}
			}
		}

		return kartsMatrix;
	}

	private static boolean pickKartsForStint(int howManyDrivers, List<String> kartsNames, String[][] kartsMatrix,
			int i) {
		Random random = new Random();
		int kartId;
		for (int k = 0; k < howManyDrivers; k++) {
			List<String> kartsLeft = new ArrayList<>(kartsNames);
			for (int j = 0; j < i; j++) {
				kartsLeft.remove(kartsMatrix[j][k]);
			}
			if (kartsLeft.isEmpty()) {
				for (int h = 0; h < howManyDrivers; h++) {
					kartsMatrix[i][h] = null;
				}
				return false;
			}
			kartId = random.nextInt(kartsLeft.size());
			String kart = kartsLeft.get(kartId);
			kartsNames.remove(kart);
			kartsMatrix[i][k] = kart;
		}
		return true;
	}

	private static void printArray(String[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.print("Wyscig numer " + (i + 1) + ": ");
			for (int k = 0; k < matrix[i].length; k++) {
				System.out.print(matrix[i][k] + ", ");
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

}
