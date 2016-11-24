package joe.classes.identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TypedValueMap<V1 extends ITypable<?> & IMappable> {
	private final Map<String, V1> fTypes;
	private final Map<String, Object> fValues;
	
	public TypedValueMap() {
		fTypes = new LinkedHashMap<String, V1>();
		fValues = new HashMap<String, Object>();
	}
	
	public void clear() {
		fTypes.clear();
		fValues.clear();
	}
	
	public void clearValues() {
		fValues.clear();
	}
	
	/*
	 * Type Functions
	 */
	
	public V1 setType(V1 type) {
		V1 returnType = fTypes.put(type.getIdentifier(), type);
		
		if (returnType != null) {
			removeValue(returnType);
		}
		return returnType;
	}
	
	@SafeVarargs
	public final Collection<V1> setType(V1... types) {
		Collection<V1> returnTypes = new ArrayList<V1>(types.length);
		for(V1 type : types) {
			returnTypes.add(setType(type));
		}
		return returnTypes;
	}
	
	public Collection<V1> setType(Collection<V1> types) {
		Collection<V1> returnTypes = new ArrayList<V1>(types.size());
		for(V1 type : types) {
			returnTypes.add(setType(type));
		}
		return returnTypes;
	}
	
	public V1 getType(Object type) {
		if (type instanceof String) {
			return fTypes.get(type);
		} else if (type instanceof IMappable) {
			return fTypes.get(((IMappable) type).getIdentifier());
		} else if (type != null) {
			return fTypes.get(type.toString());
		}
		return null;
	}
	
	public Collection<V1> getType(Object... types) {
		Collection<V1> returnTypes = new ArrayList<V1>(types.length);
		for(Object type : types) {
			returnTypes.add(getType(type));
		}
		return returnTypes;
	}

	public Collection<V1> getType(Collection<Object> types) {
		Collection<V1> returnTypes = new ArrayList<V1>(types.size());
		for(Object type : types) {
			returnTypes.add(getType(type));
		}
		return returnTypes;
	}

	public Collection<V1> getType() {
		Collection<V1> returnTypes = new ArrayList<V1>(fTypes.size());
		for(V1 type : fTypes.values()) {
			returnTypes.add(type);
		}
		return returnTypes;
	}
	
	public V1 removeType(Object type) {
		V1 returnType = null;
		if (type instanceof String) {
			returnType = fTypes.remove(type);
		} else if (type instanceof IMappable) {
			returnType = fTypes.remove(((IMappable) type).getIdentifier());
		} else if (type != null) {
			returnType = fTypes.remove(type.toString());
		}
		
		if (returnType != null) {
			removeValue(returnType.getIdentifier());
		}
		return returnType;
	}
	
	public Collection<V1> removeType(Object... types) {
		Collection<V1> returnTypes = new ArrayList<V1>(types.length);
		for(Object type : types) {
			returnTypes.add(removeType(type));
		}
		return returnTypes;
	}
	
	public Collection<V1> removeType(Collection<Object> types) {
		Collection<V1> returnTypes = new ArrayList<V1>(types.size());
		for(Object type : types) {
			returnTypes.add(removeType(type));
		}
		return returnTypes;
	}
	
	/*
	 * 
	 * Value Functions
	 * 
	 */
	
	public <FV1 extends ITypable<FV2> & IMappable, FV2> FV2 setValue(FV1 type, FV2 value) {
		if (type != null) {
			return type.getType().cast(fValues.put(type.getIdentifier(), value));
		}
		return null;
	}
	
	public Object setValue(Object type, Object value) {
		V1 valueType = getType(type);
		if (valueType != null) {
			return fValues.put(valueType.getIdentifier(), value);
		}
		return null;
	}

	public <FV1 extends ITypable<FV2> & IMappable, FV2> FV2 getValue(FV1 type) {
		if (type != null) {
			return type.getType().cast(fValues.get(type.getIdentifier()));
		}
		return null;
	}

	public Object getValue(Object type) {
		return getValue(getType(type));
	}

	public Collection<Object> getValue(Object... types) {
		Collection<Object> values = new ArrayList<Object>(types.length);
		for(Object type : types) {
			values.add(getValue(type));
		}
		return values;
	}

	public Collection<Object> getValue(Collection<Object> types) {
		Collection<Object> values = new ArrayList<Object>(types.size());
		for(Object type : types) {
			values.add(getValue(type));
		}
		return values;
	}

	public Map<V1, Object> getValue() {
		Map<V1, Object> statistics = new LinkedHashMap<V1, Object>();
		for(V1 type : fTypes.values()) {
			if (fValues.containsKey(type.getIdentifier())) {
				statistics.put(type, fValues.get(type.getIdentifier()));
			}
		}
		return statistics;
	}
	
	public <FV1 extends ITypable<FV2> & IMappable, FV2> FV2 removeValue(FV1 type) {
		if (type != null) {
			return type.getType().cast(fValues.remove(type.getIdentifier()));
		}
		return null;
	}
	
	public Object removeValue(Object type) {
		V1 valueType = getType(type);
		if (valueType != null) {
			return fValues.remove(valueType.getIdentifier());
		}
		return null;
	}
	
	public Collection<Object> removeValue(Object... types) {
		Collection<Object> values = new ArrayList<Object>(types.length);
		for(Object type : types) {
			values.add(removeValue(type));
		}
		return values;
	}
	
	public Collection<Object> removeValue(Collection<Object> types) {
		Collection<Object> values = new ArrayList<Object>(types.size());
		for(Object type : types) {
			values.add(removeValue(type));
		}
		return values;
	}
}
