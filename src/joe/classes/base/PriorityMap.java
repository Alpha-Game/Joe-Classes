package joe.classes.base;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PriorityMap<Key, Value> implements Map<Key, Value> {
	protected final Map<Double, LinkedHashMap<Key, Value>> fValues;
	protected final Map<Key, Double> fPriority;
	private Set<Map.Entry<Key, Value>> fResultSet;

	public PriorityMap() {
		fValues = new TreeMap<Double, LinkedHashMap<Key, Value>>(new Comparator<Double>() {
			@Override
			public int compare(Double paramT1, Double paramT2) {
				return Double.compare(paramT1, paramT2);
			}
		});
		fPriority = new HashMap<Key, Double>();
	}
	
	@Override
	public void clear() {
		fValues.clear();
		fPriority.clear();
		fResultSet = null;
	}
	
	@Override
	public boolean isEmpty() {
		return fPriority.isEmpty();
	}
	
	@Override
	public boolean containsKey(Object key) {
		return fPriority.containsKey(key);
	}
	
	public Double getPriority(Object key) {
		return fPriority.get(key);
	}
	
	@Override
	public Value get(Object key) {
		Double priority = getPriority(key);
		if (priority == null) {
			return fValues.get(priority).get(key);
		}
		return null;
	}	
	
	public Value put(Key key, double priority, Value value) {
		if (Double.isNaN(priority)) {
			throw new IllegalArgumentException("Priority must be a number.");
		}
		fResultSet = null;
		
		Value oldValue = remove(key);
		
		fPriority.put(key, priority);
		LinkedHashMap<Key, Value> list = fValues.get(priority);
		if (list == null) {
			list = new LinkedHashMap<Key, Value>();
			fValues.put(priority, list);
		}
		list.put(key, value);
		
		return oldValue;
	}

	@Override
	public Value put(Key key, Value value) {
		Double priority = getPriority(key);
		if (priority == null) {
			return fValues.get(priority).put(key, value);
		}
		throw new UnsupportedOperationException("Put can only set existing values.");
	}

	@Override
	public void putAll(Map<? extends Key, ? extends Value> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value remove(Object key) {
		Double priority = fPriority.remove(key);
		if (priority != null) {
			fResultSet = null;
			
			LinkedHashMap<Key, Value> list = fValues.get(priority);
			Value value = list.remove(key);
			if (list.size() < 1) {
				fValues.remove(priority);
			}			
			return value;
		}
		return null;
	}

	@Override
	public int size() {
		return fPriority.size();
	}
	
	@Override
	public Set<Key> keySet() {
		return fPriority.keySet();
	}
	
	public Set<Double> prioritySet() {
		return fValues.keySet();
	}
	
	public Set<Map.Entry<Key, Value>> prioritySet(double priority) {
		LinkedHashMap<Key, Value> values = fValues.get(priority);
		if (values == null) {
			return null;
		}
		return values.entrySet();
	}
	
	@Override
	public Set<Map.Entry<Key, Value>> entrySet() {
		if (fResultSet == null) {
			fResultSet = new LinkedHashSet<Map.Entry<Key, Value>>();
			for (Map.Entry<Double, LinkedHashMap<Key, Value>> position : fValues.entrySet()) {
				fResultSet.addAll(position.getValue().entrySet());
			}
			fResultSet = Collections.unmodifiableSet(fResultSet);
		}
		return fResultSet;
	}

	@Override
	public Collection<Value> values() {
		throw new UnsupportedOperationException("Use entry set.");
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException("Use entry set.");
	}
}
