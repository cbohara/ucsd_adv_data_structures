package roadgraph;

public class Road {
	private String name;
	private String type;
	private double length;
	private Intersection start;
	private Intersection end;

	public Road(String name, String type, double length, Intersection start, Intersection end) {
		this.name = name;
		this.type = type;
		this.length = length;
		this.start = start;
		this.end = end;
	}
	public Intersection getStart() {
		return this.start;
	}
	
	public Intersection getEnd() {
		return this.end;
	}
	
	public double getLength() {
		return this.length;
	}
	
	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}
}