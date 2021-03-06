/**
 * @author UCSD MOOC development team and Charlie O'Hara
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import roadgraph.Intersection;
import roadgraph.Road;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.Queue;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class Week3 {
	private HashMap<GeographicPoint, Intersection> locationToIntersection;
	private int numIntersections;
	private int numRoads;
	/** 
	 * Create a new empty Week3 
	 */
	public Week3()
	{
		//TODO: Implement this method in WEEK 3
		this.locationToIntersection = new HashMap<GeographicPoint, Intersection>();
		this.numIntersections = 0;
		this.numRoads = 0;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return this.numIntersections;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The intersections in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return locationToIntersection.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return this.numRoads;
	}

	
	
	/** Add Intersection node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if (location == null || locationToIntersection.containsKey(location)) {
			return false;
		} else {
			locationToIntersection.put(location, new Intersection(location));
			numIntersections++;
			return true;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Adds Road to the graph from Intersection1 to Intersection2
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * aka both intersections are already in locationToIntersection hashmap
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		//TODO: Implement this method in WEEK 3
		if (locationToIntersection.get(from) == null || locationToIntersection.get(to) == null) {
			throw new IllegalArgumentException("Intersection needs to be added to graph first");
		}
		if (roadName.equals(null) || roadType.equals(null) || length < 0) {
			throw new IllegalArgumentException("Invalid road description");
		}
		Intersection pt1 = locationToIntersection.get(from);
		Intersection pt2 = locationToIntersection.get(to);
		pt1.addRoad(roadName, roadType, length, pt1, pt2);
		numRoads++;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Determine if goal intersection is in the map
	 * target intersection found
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return boolean if goal is in map return true
	 */
	private boolean isGoalInMap(Intersection start, Intersection goal, 
			Map<Intersection, Intersection> parentMap) {
		// queue used to track where to search next
		Queue<Intersection> queue = new LinkedList<Intersection>();

		// set used to keep track of the Intersections that have been visited
		Set<Intersection> visited = new HashSet<Intersection>();

		queue.add(start);
		visited.add(start);
		
		while (!queue.isEmpty()) {
			Intersection currentIntersection = queue.poll();
			// found goal intersection so return parent map to build path to goal
			if(currentIntersection == goal) {
				return true;
			}
			// for each intersection connected to the current intersection
			for (Road connectedRoads : currentIntersection.getRoads()) {
				Intersection otherIntersection = connectedRoads.getEnd();
				// if the other intersection has not been visited yet
				if (!visited.contains(otherIntersection)) {
					// add to visited set
					visited.add(otherIntersection);
					// add to parentMap
					parentMap.put(otherIntersection, currentIntersection);
					// add to the end of the queue
					queue.add(otherIntersection);

				}
			}
		}
		return false;
	}
	
	/** Get the path from the start to the goal intersection
	 * 
	 * @param goal The goal location
	 * @param parentMap The mapping between parent and child intersections
	 * @return List containing series of intersections from start to goal
	 */
	private List<GeographicPoint> getPath(GeographicPoint goal, 
			Map<Intersection, Intersection> parentMap) {
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();

		// start by adding the final goal location to the list
		path.addFirst(goal);
		// get the intersection associated with the goal location
		Intersection current = locationToIntersection.get(goal);
		
		// get the previous Intersection and add to the front of the list
		while(parentMap.containsKey(current)) {
			Intersection previous = parentMap.get(current);
			path.addFirst(previous.getLocation());
			current = previous;
		}
		return path;
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (start == null || goal == null) {
			return null;
		}
		
		Intersection startIntersection = locationToIntersection.get(start);
		Intersection goalIntersection = locationToIntersection.get(goal);

		// map used to keep track of the path from the start to the target Intersection
		// map parent/previous Intersection to child/next Intersection
		Map<Intersection, Intersection> parentMap = new HashMap<Intersection, Intersection>();
		
		boolean found = isGoalInMap(startIntersection, goalIntersection, parentMap);
		
		if (!found) {
			return null;
		}
		// get path between start and goal
		return getPath(goal, parentMap);
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if (start == null || goal == null) {
			return null;
		}
		
		Intersection startIntersection = locationToIntersection.get(start);
		Intersection goalIntersection = locationToIntersection.get(goal);

		// map used to keep track of the path from the start to the target Intersection
		// map parent/previous Intersection to child/next Intersection
		Map<Intersection, Intersection> parentMap = new HashMap<Intersection, Intersection>();
		
		boolean found = isGoalInMap(startIntersection, goalIntersection, parentMap);
		
		if (!found) {
			return null;
		}
		// get path between start and goal
		return getPath(goal, parentMap);
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		Week3 firstMap = new Week3();
		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		Week3 simpleTestMap = new Week3();
//		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		Week3 testMap = new Week3();
//		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		/* Use this code in Week 3 End of Week Quiz */
		Week3 theMap = new Week3();
		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
	}
}