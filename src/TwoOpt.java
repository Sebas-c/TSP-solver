package coursework1;

import java.util.ArrayList;
import java.util.Stack;

public class TwoOpt {
	//Variables
	private ArrayList<Integer> path = new ArrayList<Integer>();
	private Double[][] distanceMatrix;
	
	private long searchStart;
	private long searchFinish;
	private long totalSearchTime;
	private long NNSearchTime;
	
	private Boolean didBreak;
	
	//Setters
	public void setPath(Stack<Integer> pathStack) {
		path = new ArrayList<Integer>();
		
		for(Integer cityNumber : pathStack) {
			path.add(cityNumber - 1);
		}
	}
	
	public void setNNSearchTime(long NNSearchTime) {
		this.NNSearchTime = NNSearchTime;
	}

	//Hi Mark :D I had a tiny bit of time left the night before submission so I figured I'd give 2-Opt a go.
	//Didn't turn out too bad, I'll mention improvements in the report.

	//Constructor
	public TwoOpt(Stack<Integer> pathStack, Double[][] distanceMatrix, long NNSearchTime) {
		for(Integer cityNumber : pathStack) {
			path.add(cityNumber - 1);
		}
		
		this.distanceMatrix = distanceMatrix;
		this.NNSearchTime = NNSearchTime;
	}
	
	//Methods
	
	//This is the main method, it tries to swap cities around until no further improved can be made
	//The boolean passed as a parameter is used to select the setting (there is probably a clever name for it but I don't know it so I named them myself)
	//If true, it breaks the loop as soon as an optimisation is found and starts from the beginning again. (I called this one Repeated Best First Iteration)
	//Else, it keeps trying to improve and then recursively calls itself and tries to improve again from the beginning. (I called this one Continuous Improvement)
	//It makes differences on how it goes down the tree of optimisation, which leads to slight differences in results. 
	//I'm not sure any of the two is always better (there might be some way to prove mathematically if one is) so I kept both
	public void optimisation(Boolean doBreak) {	
		//Starts timer
		searchStart = System.nanoTime();
		//Boolean to keep track of whether we need to keep looping to optimise
		Boolean optimised = true;
		
		//Setting for the Two-Opt (Repeated Best First Iteration or Continuous Improvement)
		didBreak = doBreak;
		
		while(optimised) {
			//Breaks the loop unless we optimise later on
			optimised = false;
			
			//Looping through all the cities in the path
			for(int i = 1; i < path.size() - 1; i++) {				
				for(int j = 1; j < path.size() - 1; j++) {
					//For any other city that is not just before, the same or just after
					if(j != i - 1 && j != i && j != i + 1) {
												
						//If the distance between that first city and its following one plus the distance between the second city and its following one
						//is more than the distance between the first and second city plus the distance between both following city
						//(i.e. if we swap the edges, is the path becoming shorter)
						Double originalValue = distanceMatrix[path.get(i)][path.get(i + 1)] + distanceMatrix[path.get(j)][path.get(j + 1)];
						Double newValue = distanceMatrix[path.get(i)][path.get(j)] + distanceMatrix[path.get(i + 1)][path.get(j + 1)];
						
						if(originalValue > newValue) {
							//Swap the cities and inverted the order between them
							if(i < j) {
								swapCities(i, j);
							}else {
								swapCities(j, i);
							}
							
							//Looping again to check if we can optimise again
							optimised = true;
							
							//Depending on the setting, break the loop or let it continue
							if(doBreak) {
								break;
							}
						}
					}			
				}
			}
		}
				
		//Stops timer and calculates total search time
		searchFinish = System.nanoTime();
		totalSearchTime = searchFinish - searchStart + NNSearchTime;
	}
		
	//Swaps two cities in the path ArrayList and inverts the order of all the cities in between them
	//First city must be before second City
	public void swapCities(int firstCity, int secondCity) {
		//Increments the value of the first city
		firstCity++;
				
		//Creates a temporary ArrayList to work with
		ArrayList<Integer> tempArray = new ArrayList<Integer>();
		
		//Add all cities until we reach the first to swap
		for(int i = 0; i < firstCity; i++) {
			tempArray.add(path.get(i));
		}
		//Add the second city (swapping with first one)
		tempArray.add(path.get(secondCity));
		
		//Add all cities from the second one to the first one
		for(int i = secondCity - 1; i > firstCity; i--) {
			tempArray.add(path.get(i));
		}
		
		//Add the first city
		tempArray.add(path.get(firstCity));
		
		//Add all cities after the second city to swap
		for(int i = secondCity + 1; i < path.size(); i++) {
			tempArray.add(path.get(i));
		}
		
		//Saves reordered path
		path = tempArray;
	}
	
	//Returns length of the path
	public Double calculatePathLength() {
		//Creates a variable to store the value
		Double length = 0.0;
		
		//Loops through the entire path and uses the distance Matrix to add the distance between each cities to the path
		for(int i = 0; i < path.size() - 1; i++) {
			length += distanceMatrix[path.get(i)][path.get(i + 1)];
		}
		return length;
	}
	
	//Prints the optimised path. The boolean passed here should be the same as the one passed to the optimised function
	public void printOptimisedPath() {
		
		//Print a message showing which setting was used
		if(didBreak) {
			System.out.println("The shortest path found with Two Opt - Repeated Best First Iteration is: ");
		}else{
			System.out.println("The shortest path found with Two Opt - Continuous Improvement is: ");
		}
		
		//Print the path and its length followed by the search time
		for(int i = 0; i < path.size() - 1; i++) {
			System.out.print(path.get(i) + 1 + ", ");
		}
		System.out.println(path.get(path.size() - 1) + 1 + ". Distance is " + calculatePathLength() + ".");
		
		System.out.println("Total search time: " + totalSearchTime + " nanoseconds.");
	}
}
