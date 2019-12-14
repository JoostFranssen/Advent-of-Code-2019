package util;

import java.util.HashMap;
import java.util.Map;

public class QuantityMap<K> extends HashMap<K, Integer> {
	private static final long serialVersionUID = 2916879041580655128L;
	
	public Integer putAdd(K key, Integer value) {
		Integer oldValue = 0;
		if(containsKey(key)) {
			oldValue = get(key);
		}
		return super.put(key, value + oldValue);
	}
	
	public QuantityMap() {
		super();
	}
	
	public QuantityMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	
	public QuantityMap(int initialCapacity) {
		super(initialCapacity);
	}
	
	public QuantityMap(Map<? extends K, ? extends Integer> m) {
		super(m);
	}
}
