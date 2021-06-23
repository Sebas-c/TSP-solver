package coursework1;

public class City {
	//Variables
	private int cityNumber;
	private double xPosition;
	private double yPosition;
	private boolean visited;
	
	//Constructor
	public City(int cityNumber, double xPosition, double yPosition) {
		this.cityNumber = cityNumber;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.visited = false;
	}
	
	//Getters
	public int getCityNumber() {
		return cityNumber;
	}
	
	public double getxPosition() {
		return xPosition;
	}
	
	public double getyPosition() {
		return yPosition;
	}

	public boolean getVisited() {
		return visited;
	}
	
	//Setters
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
