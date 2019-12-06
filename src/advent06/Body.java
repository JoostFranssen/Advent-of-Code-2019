package advent06;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Body {
	private Body orbitsAround;
	private Set<Body> orbitedBy;
	
	public Body() {
		this(null);
	}
	public Body(Body orbitsAround) {
		this.orbitsAround = orbitsAround;
		orbitedBy = new HashSet<>();
	}
	
	public boolean addOrbit(Body orbitingBody) {
		return orbitedBy.add(orbitingBody);
	}
	
	public void setOrbitsAround(Body orbitsAround) {
		this.orbitsAround = orbitsAround;
		orbitsAround.addOrbit(this);
	}
	
	public Body getOrbitedBody() {
		return orbitsAround;
	}
	
	public Set<Body> getOrbiters() {
		return new HashSet<>(orbitedBy);
	}
	
	public Set<Body> getDirectConnections() {
		Set<Body> connections = getOrbiters();
		if(orbitsAround != null) {
			connections.add(orbitsAround);
		}
		return connections;
	}
	
	public int countOrbits() {
		int count = 0;
		if(orbitsAround != null) {
			count = 1 + orbitsAround.countOrbits();
		}
		return count;
	}
	
	public Optional<Integer> getFewestConnectionsTo(Body target, Body skip) {
		List<Integer> counts = new ArrayList<>();
		Set<Body> connections = getDirectConnections();
		if(connections.contains(target)) {
			return Optional.of(1);
		} else {
			for(Body connection : connections) {
				if(connection == skip) {
					continue;
				}
				Optional<Integer> connectionCount = connection.getFewestConnectionsTo(target, this);
				if(connectionCount.isPresent()) {
					counts.add(1 + connectionCount.get());
				}
			}
		}
		Optional<Integer> result = counts.stream().min(Integer::compare);
		return result;
	}
}
