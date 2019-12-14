package util;

import java.util.HashMap;
import java.util.Map;

public class QuantityMap<K> extends HashMap<K, Long> {
	private static final long serialVersionUID = 2916879041580655128L;
	
	public Long putAdd(K key, int value) {
		return putAdd(key, (long)value);
	}
	public Long putAdd(K key, Long value) {
		Long oldValue = 0L;
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
	
	public QuantityMap(Map<? extends K, ? extends Long> m) {
		super(m);
	}
}
