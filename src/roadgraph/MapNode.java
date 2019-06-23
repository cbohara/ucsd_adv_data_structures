package roadgraph;

import java.util.ArrayList;
import java.util.List;
import geography.GeographicPoint;

public class MapNode {
	private GeographicPoint location;
	private List<MapEdge> edges;
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		this.edges = new ArrayList<MapEdge>();
	}
	
	/**
	 * Create edge between current MapNode and another MapNode
	 * 
	 */
	public void addMapEdge(String name, double length, MapNode start, MapNode end) {
		edges.add(MapEdge(name, length, start, end));
	}
	
	public List<MapEdge> getEdges() {
		return this.edges;
		
	}
	
}
