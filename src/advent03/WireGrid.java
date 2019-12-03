package advent03;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

public class WireGrid {
	private List<WireNode> nodes;
	private int lastWireIndex;
	
	public WireGrid() {
		nodes = new ArrayList<>();
		lastWireIndex = 0;
	}
	
	public void addWire(List<String> wireDirections) {
		WireNode lastNode = new WireNode(0, 0, lastWireIndex);
		for(String direction : wireDirections) {
			IntUnaryOperator xTranslate = getXTranslation(direction);
			IntUnaryOperator yTranslate = getYTranslation(direction);
			int length = getDirectionLength(direction);
			while(length > 0) {
				lastNode = new WireNode(xTranslate.applyAsInt(lastNode.getX()), yTranslate.applyAsInt(lastNode.getY()), lastWireIndex);
				nodes.add(lastNode);
				length--;
			}
		}
		lastWireIndex++;
	}
	
	List<WireNode> getIntersections() {
		List<WireNode> intersections = new ArrayList<>();
		for(int i = 0; i < nodes.size(); i++) {
			WireNode node = nodes.get(i);
			for(int j = i + 1; j < nodes.size(); j++) {
				WireNode other = nodes.get(j);
				if(node.intersectsOther(other)) {
					intersections.add(node);
				}
			}
		}
		return intersections.stream().filter(n -> !n.isOrigin()).collect(Collectors.toList());
	}
	
	public int getMinIntersectionDistanceToOrigin() {
		return getIntersections().stream().map(n -> n.distanceToCentralPort()).min(Integer::compare).get();
	}
	
	static char getDirectionIndicator(String direction) {
		return direction.charAt(0);
	}
	
	static int getDirectionLength(String direction) {
		return Integer.parseInt(direction.substring(1));
	}
	
	static IntUnaryOperator getXTranslation(String direction) {
		switch(getDirectionIndicator(direction)) {
			case 'L': return x -> x - 1;
			case 'R': return x -> x + 1;
			default: return IntUnaryOperator.identity();
		}
	}
	
	static IntUnaryOperator getYTranslation(String direction) {
		switch(getDirectionIndicator(direction)) {
			case 'D': return y -> y - 1;
			case 'U': return y -> y + 1;
			default: return IntUnaryOperator.identity();
		}
	}
}

class WireNode {
	private int x, y, wireIndex;
	
	WireNode(int x, int y, int wireIndex) {
		this.x = x;
		this.y = y;
		this.wireIndex = wireIndex;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWireIndex() {
		return wireIndex;
	}
	
	boolean intersectsOther(WireNode node) {
		return x == node.x && y == node.y && wireIndex != node.wireIndex;
	}
	
	int distanceToCentralPort() {
		return Math.abs(x) + Math.abs(y);
	}
	
	boolean isOrigin() {
		return x == 0 && y == 0;
	}
}