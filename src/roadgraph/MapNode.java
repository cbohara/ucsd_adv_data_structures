package roadgraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import geography.GeographicPoint;

public class MapNode implements Comparable<MapNode> {
	private GeographicPoint location;
	private HashSet<MapEdge> edgeList;
	private double distance;
	private double predictedDistance;
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		edgeList = new HashSet<MapEdge>();
	}
	
	public void addEdge(MapEdge edge) {
		edgeList.add(edge);
	}
	
	public List<MapNode> getNeighbors() {
		List<MapNode> neighbors = new ArrayList<MapNode>();
		for (MapEdge edge : edgeList) {
			neighbors.add(edge.getEnd());
		}
		return neighbors;
	}
	
	public HashSet<MapEdge> getEdgeList() {
		return edgeList;
	}
	
	public GeographicPoint getLocation() {
		return location;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setPredictedDistance(double predictedDistance) {
		this.predictedDistance = predictedDistance;
	}
	
	public double getPredictedDistance() {
		return predictedDistance;
	}
	
	public double straightDistanceTo(MapNode node) {
		return this.getLocation().distance(node.getLocation());
	}
	
	@Override
	public int compareTo(MapNode node) {
		return Double.compare(this.predictedDistance, node.getPredictedDistance());
	}
	
	public String toString() {
		return location.toString();
	}
}