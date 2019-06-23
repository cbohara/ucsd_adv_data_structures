package roadgraph;

import roadgraph.Road;
import java.util.ArrayList;
import java.util.List;
import geography.GeographicPoint;

public class Intersection {
	private GeographicPoint location;
	private List<Road> roads;
	
	public Intersection(GeographicPoint location) {
		this.location = location;
		this.roads = new ArrayList<Road>();
	}
	
	/**
	 * Add road from current Intersection and a new Intersection
	 * @param name The name of the road
	 * @param type The type of the road
	 * @param length The length of the road, in km
	 * @param start The starting intersection lat/long
	 * @param end The ending intersection lat/long
	 * 
	 */
	public void addRoad(String name, String type, double length, Intersection start, Intersection end) {
		roads.add(new Road(name, type, length, start, end));
	}
	
	public List<Road> getRoads() {
		return this.roads;
		
	}
	
	public GeographicPoint getLocation() {
		return this.location;
	}
	
}
