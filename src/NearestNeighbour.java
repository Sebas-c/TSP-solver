package coursework1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NearestNeighbour {
	//Variables
	private ArrayList<City>	cities;
	private Double[][] citiesDistances;
	private Stack<Integer> pathStack = new Stack<Integer>();
	private Double pathLength = 0.0;
	private Stack<Integer> shortestPathStack = new Stack<Integer>();
	private Double shortestPathLength = Double.MAX_VALUE;
	private Integer startingRNNIndex = 0;
	
	private long searchStart;
	private long searchFinish;
	private long totalSearchTime;
	private long graphTime;
	
	private Load loadClass;
	

	//Constructor
	public NearestNeighbour(ArrayList<City> cities, Double[][] citiesDistances, long graphTime, Load loadClass) {
		this.cities = cities;
		this.citiesDistances = citiesDistances;
		this.graphTime = graphTime;
		this.loadClass = loadClass;
		this.loadClass.setAllCitiesNotVisited();
	}
	
	//Getters
	public Stack<Integer> getPathStack() {
		return pathStack;
	}
	
	public Stack<Integer> getShortestPathStack() {
		return shortestPathStack;
	}
	
	public long getTotalSearchTime() {
		return totalSearchTime;
	}
	
	//Methods
	public void NNSearch() {
		//Start timer
		searchStart = System.nanoTime();
		
		//Creates a variable to compare distances found
		Double nearestDistance = Double.MAX_VALUE;
		//Creates a variable to compare index of closest city found
		Integer nearestIndex = -1;
		//Creates a variable to use as index of the first city
		Integer cityIndex = 0;
		//Add first city's number to the stack and sets it to visited
		pathStack.push(1);
		cities.get(cityIndex).setVisited(true);
		
		//Loops through all cities
		for(int i = 0; i < cities.size(); i++) {
			//If city is not visited AND not the one we're currently checking (first city) AND the distance to it is smaller than the current smallest distance
			if(!cities.get(i).getVisited() && !cityIndex.equals(i) && nearestDistance > citiesDistances[cityIndex][i]) {
				//Store distance
				nearestDistance = citiesDistances[cityIndex][i];
				//Store index
				nearestIndex = i;
			}
		}
		//Push city number to the stack & set it to visited
		pathStack.push(nearestIndex + 1);
		cities.get(nearestIndex).setVisited(true);
		
		//Adds distance to the pathLength
		pathLength += citiesDistances[cityIndex][nearestIndex];
		//Call recursive method with the index of the next city to examine
		NNRecursion(nearestIndex);
		
		//Ends timer
		searchFinish = System.nanoTime();
		totalSearchTime = searchFinish - searchStart + graphTime;
	}
	
	//Finds the closest city to the one passed as a variable (or rather its index is passed). Calls itself unless all cities have been visited.
	public void NNRecursion(Integer cityIndex) {
		//Boolean to stop recursion
		Boolean allCitiesVisited = true;
		
		//Creates a variable to compare distances found
		Double nearestDistance = Double.MAX_VALUE;
		//Creates a variable to compare index of closest city found
		Integer nearestIndex = -1;
		
		//Loops through all cities
		for(int i = 0; i < cities.size(); i++) {
			//If city is not visited AND not the one we're currently checking (first city) AND the distance to it is smaller than the current smallest distance
			if(!cities.get(i).getVisited() && !cityIndex.equals(i) && nearestDistance > citiesDistances[cityIndex][i]) {
				//Store distance
				nearestDistance = citiesDistances[cityIndex][i];
				//Store index
				nearestIndex = i;
			}
		}
		//Push city number to the stack & set it to visited
		pathStack.push(nearestIndex + 1);
		cities.get(nearestIndex).setVisited(true);
		
		//Adds distance to the pathLength
		pathLength += citiesDistances[cityIndex][nearestIndex];
		
		//Loops through all cities to check if any is unvisited
		for(City city : cities) {
			if(!city.getVisited()) {
				allCitiesVisited = false;
				//Breaks as soon as a single unvisited city is found. Gotta save every possible CPU cycle to get the best time of the class ;)
				break;
			}
		}
		
		//If any city is unvisited, call itself with the index of the next city to examine as a variable
		if(!allCitiesVisited) {
			NNRecursion(nearestIndex);
		} else { //Adds distance back to the initial city and initial city to respective stacks
			pathLength += citiesDistances[nearestIndex][0];
			pathStack.push(1);
		}
		
	}
	
	public void repeatedNNSearch() {
		//Start timer
		searchStart = System.nanoTime();
		
		//Calls NNRecursion with all possible starting cities
		for(int i = 0; i < cities.size(); i++) {
			//Resets path and length
			pathStack = new Stack<Integer>();
			pathLength = 0.0;
			
			//Sets all cities as not visited
			loadClass.setAllCitiesNotVisited();
			
			//Sets current city as visited
			cities.get(i).setVisited(true);
			
			//Stores the starting city (used in the last recursion to loop the path back to it)
			startingRNNIndex = i;
			
			//Push the first city on the stack
			pathStack.push(i + 1);
			
			//Calls the recursion
			repeatedNNRecursion(i);
		}
		
		//Ends timer
		searchFinish = System.nanoTime();
		totalSearchTime = searchFinish - searchStart + graphTime;
		
	}
	
	public void repeatedNNRecursion(Integer cityIndex) {
		//Boolean to stop recursion
		Boolean allCitiesVisited = true;
		
		//Creates a variable to compare distances found
		Double nearestDistance = Double.MAX_VALUE;
		//Creates a variable to compare index of closest city found
		Integer nearestIndex = -1;
		
		//Loops through all cities
		for(int i = 0; i < cities.size(); i++) {
			//If city is not visited AND not the one we're currently checking (first city) AND the distance to it is smaller than the current smallest distance
			if(!cities.get(i).getVisited() && !cityIndex.equals(i) && nearestDistance > citiesDistances[cityIndex][i]) {
				//Store distance
				nearestDistance = citiesDistances[cityIndex][i];
				//Store index
				nearestIndex = i;
			}
		}
		//Push city number to the stack & set it to visited
		pathStack.push(nearestIndex + 1);
		cities.get(nearestIndex).setVisited(true);
		
		//Adds distance to the pathLength
		pathLength += citiesDistances[cityIndex][nearestIndex];
		
		//Loops through all cities to check if any is unvisited
		for(City city : cities) {
			if(!city.getVisited()) {
				allCitiesVisited = false;
				break;
			}
		}
		
		//If any city is unvisited, call itself with the index of the next city to examine as a variable
		if(!allCitiesVisited) {
			repeatedNNRecursion(nearestIndex);
		} else { 
			//Adds distance back to the initial city
			pathLength += citiesDistances[nearestIndex][startingRNNIndex];
			
			//If current path length is less than best one found so far, store it
			if(pathLength < shortestPathLength) {
				shortestPathLength = pathLength;

				//Adds Start city at the end of the loop
				pathStack.push(startingRNNIndex + 1);
				
				shortestPathStack = (Stack<Integer>) pathStack.clone();
			}
		}
	}
	
	//Prints shortest path found with NN and time taken
	public void printShortestPath() {		
		
		System.out.println("The proposed path with the Nearest Neighbour algorithm is: ");
		for(int i = 0; i < pathStack.size() - 1; i++) {
			System.out.print(pathStack.get(i) + ", ");
		}
		System.out.println(pathStack.peek() + ". Distance is " + pathLength + ".");
		
		System.out.println("Nearest Neighbour search time: " + totalSearchTime + " nanoseconds.");
	}
	
	//Prints shortest path found with Repeated NN and time taken
	public void printShortestRNNPath() {		
		
		System.out.println("The proposed path with the Repeated Nearest Neighbour algorithm is: ");
		for(int i = 0; i < shortestPathStack.size() - 1; i++) {
			System.out.print(shortestPathStack.get(i) + ", ");
		}
		System.out.println(shortestPathStack.peek() + ". Distance is " + shortestPathLength + ".");
			
		System.out.println("Repeated NN search time: " + totalSearchTime + " nanoseconds.");
	}
}
