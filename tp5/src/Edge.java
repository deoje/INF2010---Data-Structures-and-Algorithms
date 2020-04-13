
public class Edge {

	private Node source;
	private Node destination;
	private int distance;
	
	public Edge () {}

	public Edge(Node s, Node d) {
		this.source = s;
		this.destination = d;		
	}

	public Edge(Node s, Node d, int dist) {
		this.source = s;
		this.destination = d;
		this.distance = dist ;
	}
     
      	
	public Node getSource() {
		return source;
	}
	public void setSource(Node source) {
		this.source = source;
	}
	public Node getDestination() {
		return destination;
	}
	public void setDestination(Node destination) {
		this.destination = destination;
	}
	public int getDistance() {
		return distance;
	}

	// Helper
	@Override
	public String toString() {
		return "{" +
				source.getName() +
				", " +
				destination.getName() +
				", " +
				distance +
				'}';
	}
}
