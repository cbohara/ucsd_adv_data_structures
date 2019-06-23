package roadgraph;

public class MapEdge {
	private String name;
	private double length;
	private MapNode start;
	private MapNode end;

	public MapEdge(String name, double length, MapNode start, MapNode end) {
		this.name = name;
		this.length = length;
		this.start = start;
		this.end = end;
	}
	public MapNode getStart() {
		return this.start;
	}
	
	public MapNode getEnd() {
		return this.end;
	}
	
	public double getLength() {
		return this.length;
	}
	
	public String getName() {
		return this.name;
	}
}