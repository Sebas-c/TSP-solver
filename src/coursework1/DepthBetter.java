package coursework1;

import java.util.ArrayList;
import java.util.Stack;

public class DepthBetter {
	//Variables
	ArrayList<City> cities;
	ArrayList<Stack<Integer>> allPossiblePaths = new ArrayList<Stack<Integer>>();
	ArrayList<Double> allPathsLengths = new ArrayList<Double>();
	
	Stack<Integer> bestPath = new Stack<Integer>();
	Double ShortestLength = Double.MAX_VALUE;
	
	private long searchStart;
	private long searchFinish;
	private long totalSearchTime;
	private long graphTime;
		
	//Constructor
	public DepthBetter(ArrayList<City> cities, long graphTime) {
		this.cities = cities;
		this.graphTime = graphTime;
	}
	
	//Methods
	//Performs the depth first search
	public void depthSearch(){
		//Starts the timer
		searchStart = System.nanoTime();
		
		//Initialises a double to store the length of the path currently being observed and one to pass to the recursion.
		Double length = 0.0;
		Double currentLength = length;
		
		//Creates a stack to store the path
		Stack<Integer> pathStack = new Stack<Integer>();
		
		//Adds the first city to the stack
		pathStack.push(cities.get(0).getCityNumber());
		
		//Sets the first city as visited
		cities.get(0).setVisited(true);
				
		//Loops through all the cities
		for(City city : cities) {


			//If the city is unvisited and in the first part of the ArrayList (used to divide calculations time by 2)
			//This is because all path generated after the second half of the array are just inversions of the first half
			//Example: 1 -> 2 -> 3 -> 4 -> 1 is the same as 1 -> 4 -> 3 -> 2 -> 1
			if(!city.getVisited() && city.getCityNumber() <= (cities.size() / 2) + 1){
				//Add the city number to the stack
				pathStack.push(city.getCityNumber());
				//Set city as visited
				city.setVisited(true);
				//Add length to the total
				currentLength += Math.sqrt(Math.pow(cities.get(pathStack.peek() - 1).getxPosition() - cities.get(pathStack.get(pathStack.size() - 2) - 1).getxPosition(), 2)
						+ Math.pow(cities.get(pathStack.peek() - 1).getyPosition() - cities.get(pathStack.get(pathStack.size() - 2) - 1).getyPosition(), 2));
				//Calls recursive method
				depthSearchRecursion(pathStack, currentLength);
				//Resets length to previous value
				currentLength = length;
				//Resets city to unvisited
				city.setVisited(false);
				//Removes city from the stack
				pathStack.pop();
			}
		}
		

		//Ends timer and calculates total search time
		searchFinish = System.nanoTime();
		totalSearchTime = searchFinish - searchStart + graphTime;
		
//		printAllPaths();
		printShortestPath();
		
	}
	
	//Recursive search. Recursion ends when no unvisited city is found (i.e. only calls itself through an if statement)
	public void depthSearchRecursion(Stack<Integer> pathStack, Double length){
		//Variables to check if the path is complete or not (i.e. to call the recursion or not)
		boolean pathComplete = true;
		//Variable to store current length of the path
		Double currentLength = length;
		
		//Loops through all cities
		for(City city : cities) {
			//If a city is not visited
			if(!city.getVisited()) {
				//Add city to the stack
				pathStack.push(city.getCityNumber());
				//Set path as not complete (next recursion will check for completeness again)
				pathComplete = false;
				//Set city as visited
				city.setVisited(true);
				//Add distance
				currentLength += Math.sqrt(Math.pow(cities.get(pathStack.peek() - 1).getxPosition() - cities.get(pathStack.get(pathStack.size() - 2) - 1).getxPosition(), 2)
						+ Math.pow(cities.get(pathStack.peek() - 1).getyPosition() - cities.get(pathStack.get(pathStack.size() - 2) - 1).getyPosition(), 2));
				//Call itself
				depthSearchRecursion(pathStack, currentLength);
				//Reset length to previous value
				currentLength = length;
				//Removes city from stack
				pathStack.pop();
				//Sets city as unvisited
				city.setVisited(false);
			}
		}

		//If all the cities have been visited
		if(pathComplete) {			
			//Stores copy of current path (not a simple copy as it would copy the reference, not the value)
			//(Note: This is the part that is memory humgry and can be improved as discussed in Main)
			Stack<Integer> newPathStack = (Stack<Integer>) pathStack.clone();
			//Adds the first city to the path (to make it return to the origin point)
			newPathStack.push(cities.get(0).getCityNumber());
			//Adds the distance to the origin city
			currentLength += Math.sqrt(Math.pow(cities.get(pathStack.peek() - 1).getxPosition() - cities.get(0).getxPosition(), 2)
					+ Math.pow(cities.get(pathStack.peek() - 1).getyPosition() - cities.get(0).getyPosition(), 2));
			
			if(currentLength < ShortestLength) {
				ShortestLength = currentLength;
				bestPath = (Stack<Integer>) newPathStack.clone();
			}
		}
	}
		
	//Loops through all the paths to find the shortest
	private void printShortestPath() {
		//Prints shortest path
		System.out.println("The shortest path found with Depth First Search is: ");
		for(int i = 0; i < bestPath.size() - 1; i++) {
			System.out.print(bestPath.get(i) + ", ");
		}
		System.out.println(bestPath.peek() + ". Distance is " + ShortestLength + ".");
		
		System.out.println("Total search time: " + totalSearchTime + " nanoseconds.");
	}
}
