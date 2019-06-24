package roadgraph;

public class MapEdge {
	//String roadName, String roadType, double length
	private MapNode start;
	private MapNode end;
	private String roadName;
	private String roadType;
	private double length;
	
	public MapEdge(MapNode start, MapNode end, String roadName, String roadType, double length) {
		this.start = start;
		this.end = end;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}
	
	public MapNode getEnd() {
		return end;
	}
	
	public double getLength() {
		return length;
	}
	
	public String toString() {
		return "" + start + " " + end + " " + roadName + " " + roadType + " " + length;
	}

}