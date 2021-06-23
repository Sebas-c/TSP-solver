package coursework1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Load {
	
	//Variables
	private ArrayList<City> cities = new ArrayList<City>();
	private long loadStart;
	private long loadFinish;
	private long totalLoadTime;
	
	//Constructor
	public Load() {
		
	}
	
	//Uses the string passed as argument to read a file and extract the cities from it.
	public void loadFile(String fileName) {
		try{
			//Starting a timer
			loadStart = System.currentTimeMillis();
			
			//Uncomment next line to override user input with a specific file name
//			fileName = "test4-19.txt";
			
			
			//Retrieving the file (if required, you can use an absolute path by using the first line instead of the second)
//			File citiesFile = new File("D:\\OneDrive\\MDX\\CST 3170 - Artificial Intelligence\\Coursework1\\Training problems\\" + fileName);
			File citiesFile = new File("cities-Files\\" + fileName);
			
			//Creating a scanner to read the file
			Scanner scanner = new Scanner(citiesFile);
			
			//Saving the position of each city
			while(scanner.hasNextLine() && scanner.hasNextInt()) {
				//Saving city's position
				City newCity = new City(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
				
				//Adding the new city to the arrayList.
				cities.add(newCity);
				
			}
			//Stopping the load time timer and printing the result.
			loadFinish = System.currentTimeMillis();
			totalLoadTime = loadFinish - loadStart;
			System.out.println("Total loading time: " + totalLoadTime + "ms");
			
			//Printing all cities in the console
//			printAllCities();
			
			
		}catch(FileNotFoundException e) {
			// prints error if no matching file was found
			System.out.println("***Error encountered loading the file:***");
			e.printStackTrace();
		} catch(Exception e) {
			// prints any other error encountered
			System.out.println("***Error encountered loading the file:***");
			e.printStackTrace();
		}
	}
	
	//Returns an ArrayList of cities
	public ArrayList<City> getAllCities(){
		return cities;
	}
	
	//Removes all cities from the Load object
	public void flushCities() {
		cities.clear();
	}
	
	//Print all cities in the console
	public void printAllCities() {
		System.out.println("Number of cities loaded: " + cities.size());
		for(City city : cities) {
			System.out.println("City #" + city.getCityNumber() + ": " + city.getxPosition() + " " + city.getyPosition());
		}
	}
	
	//Loops through all cities and resets them as not visited. Used to run algorithms several times in a row through Main.
	public void setAllCitiesNotVisited() {
		for(City city : cities) {
			city.setVisited(false);
		}
	}
}
