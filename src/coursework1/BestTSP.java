package coursework1;

import java.util.Scanner;

public class BestTSP {

	public static void main(String[] args) {
		//Welcoming my wonderful users (or user singular since it's only you Mark)
		System.out.println("Welcome to the \"best Travelling Saleman Problem Solver\"! (Note: the name is non-legally binding)");
		System.out.println("Please enter the name of the file you would like to use or type \"Exit\" to quit: ");
		
		//Creates a class to load files
		Load loadClass = new Load();
		
		//Reads file based on user inputs
		while(true) {
			//Creates a scanner to read user input
			Scanner inputReader = new Scanner(System.in);
			
			//Stores user input and converts it to lowercase
			String fileName = inputReader.nextLine();
			fileName = fileName.toLowerCase();
		
			//If user typed "Exit", quit the program
			if(fileName.equals("exit")) {
				System.exit(0);
			}
				
			//Loads the file
			loadClass.loadFile(fileName);
			
			//Drawing a line to read results a bit more clearly
			System.out.println();
			System.out.println("---------------------------------------");
			System.out.println();
			
			//Creates an instance of Graph (used to create a distance matrix)
			Graph newGraph = new Graph(loadClass.getAllCities());
			
			//Can be used to print the distance matrix (line by line or city by city), mostly used for debugging
//			newGraph.printMatrix();
//			newGraph.printGraph();
			
			//Note: the instantiation of the "Depth" class and the Depth First Search is commented out by default as it performs terribly on large data set.
			//They will work fine (albeit the time is obviously fairly bad) on data sets of less than 12 cities.
			//Creates an instance of Depth class
//			DepthBetter depthSearch = new DepthBetter(loadClass.getAllCities(), newGraph.getTotalGraphTime());
				
			//Performs Depth First Search
			//Would NOT recommend for more than 12 cities
//			depthSearch.depthSearch();
			
			//Drawing a line to read results a bit more clearly
//			System.out.println();
//			System.out.println("---------------------------------------");
//			System.out.println();
						
			//Creates an instance of NearestNeighbours class
			NearestNeighbour NNSearch = new NearestNeighbour(loadClass.getAllCities(), newGraph.getCitiesDistances(), newGraph.getTotalGraphTime(), loadClass);
			
			//Performs a Nearest Neighbour search
			NNSearch.NNSearch();
			
			//Prints results
			NNSearch.printShortestPath();
			
			//Drawing a line to read results a bit more clearly
			System.out.println();
			System.out.println("---------------------------------------");
			System.out.println();
			
			//Performs TwoOpt with different variations
			//Instantiates the TwoOpt class. Passes the path found with Nearest Neighbour (not repeated).
			TwoOpt twoOpt = new TwoOpt(NNSearch.getPathStack(), newGraph.getCitiesDistances(), NNSearch.getTotalSearchTime());

			//Run 2-Opt with the Repeated Best First setting and prints results
			twoOpt.optimisation(true);
			twoOpt.printOptimisedPath();
			
			//Skipping a line to read results a bit more clearly
			System.out.println();
			
			//Resets the path (to the one found with Nearest Neighbour (not repeated)).
			twoOpt.setPath(NNSearch.getPathStack());
			
			//Run 2-Opt with the Continuous Improvement setting and print results
			twoOpt.optimisation(false);
			twoOpt.printOptimisedPath();
			
			//Drawing a line to read results a bit more clearly
			System.out.println();
			System.out.println("---------------------------------------");
			System.out.println();
			
			
			//Runs the Repeated Nearest Neighbour Search and prints the results
			NNSearch.repeatedNNSearch();
			NNSearch.printShortestRNNPath();
			
			//Drawing a line to read results a bit more clearly
			System.out.println();
			System.out.println("---------------------------------------");
			System.out.println();
			
			//Sets the path to shortest one found with Repeated NN
			twoOpt.setPath(NNSearch.getShortestPathStack());
			//Resets Time of initial search (since we just re-ran it)
			twoOpt.setNNSearchTime(NNSearch.getTotalSearchTime());
			
			//Run 2-Opt with the Repeated Best First setting and print results
			twoOpt.optimisation(true);
			twoOpt.printOptimisedPath();
			
			//Skipping a line to read results a bit more clearly
			System.out.println();
			
			//Reset path
			twoOpt.setPath(NNSearch.getShortestPathStack());
			
			//Run 2-Opt with the Continuous Improvement setting and print results
			twoOpt.optimisation(false);
			twoOpt.printOptimisedPath();
			
			
			//Empties the cities stored in loadClass (to allow the program to be run on another file)
			loadClass.flushCities();
			
			System.out.println("Would you like to give it another go? Just type another filename :)");
			System.out.println("You can also type \"Exit\" to quit.");
		}
	}
}
