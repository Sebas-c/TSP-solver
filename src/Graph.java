package coursework1;

import java.util.ArrayList;

public class Graph {
	//Variables
	private ArrayList<City> cities;
	private Double[][] citiesDistances; 
	private long graphStart;
	private long graphFinish;
	private long totalGraphTime;
	
	
	//CONSTRUCTOR
	public Graph(ArrayList<City> cities) {
		//Start timer
		graphStart = System.nanoTime();
		//Store all the cities
		this.cities = cities;
	
		//Sets matrix size
		citiesDistances = new Double[cities.size()][cities.size()];
		
		//Nested loop to run through each row and column of the matrix
		for (int i = 0; i < cities.size(); i++) {
			for (int j = 0; j < cities.size(); j++) {
				if(i == j) {
					//If we're looking at the same city in both dimensions, set distance as 0.0
					citiesDistances[i][j] = 0.0;
				}else {
					//Else store euclidian distance between cities
					citiesDistances[i][j] = Math.sqrt(
							  Math.pow(cities.get(i).getxPosition() - cities.get(j).getxPosition(), 2)
							+ Math.pow(cities.get(i).getyPosition() - cities.get(j).getyPosition(), 2)
							);
				}
			}
		}
		
		//End Timer
		graphFinish = System.nanoTime();
		totalGraphTime = graphFinish - graphStart;
		//Next line can be uncommented if you want to look at how long the graph took to create.
//		System.out.println("Graph creation time: " + totalGraphTime + " nanoseconds.");
	}
	
	//Prints distances city to city
	public void printGraph() {
		for (int i = 0; i < cities.size(); i++) {
			for (int j = 0; j < cities.size(); j++) {
				System.out.println("Distance between City#" + (i + 1) + " and City#" + (j + 1) + ": "+ citiesDistances[i][j]);
			}
		}
	}
	
	//Prints Matrix line by line
	public void printMatrix() {
		for (int i = 0; i < cities.size(); i++) {
			System.out.print("City #:" + (i + 1) + ": ");
			for (int j = 0; j < cities.size() - 1; j++) {
				System.out.print("#" + (j + 1) + " = " + citiesDistances[i][j].intValue() + ", ");
			}
			System.out.println("# " + cities.size() + " " + citiesDistances[i][cities.size() - 1].intValue() + ".");
		}
	}

	//Returns matrix with distances
	public Double[][] getCitiesDistances() {
		return citiesDistances;
	}

	//Returns time taken to create the graph
	public long getTotalGraphTime() {
		return totalGraphTime;
	}
}
