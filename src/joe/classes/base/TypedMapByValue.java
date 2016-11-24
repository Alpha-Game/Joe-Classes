package joe.classes.base;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TypedMapByValue<Key, Value extends Object> implements Map<Key, Value> {
	private final Class<Value> fSmallestClass;
	private final Map<Class<?>, Map<Key, Value>> fValues;

	public TypedMapByValue(Class<Value> className) {
		fSmallestClass = className;
		fValues = new HashMap<Class<?>, Map<Key, Value>>();
	}

	private void getFullInterfaceList(Set<Class<?>> allClasses, Class<?> interfaceName) {
		if (fSmallestClass.isAssignableFrom(interfaceName)) {
			allClasses.add(interfaceName);
			for (Class<?> childInterface : interfaceName.getInterfaces()) {
				if (!allClasses.contains(childInterface)) {
					getFullInterfaceList(allClasses, childInterface);
				}
			}
		}
	}

	protected Set<Class<?>> getFullClassList(Class<?> className) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		
		while (className != null && fSmallestClass.isAssignableFrom(className)) {
			classes.add(className);

			for (Class<?> interfaceName : className.getInterfaces()) {
				getFullInterfaceList(classes, interfaceName);
			}
			className = className.getSuperclass();
		}

		return classes;
	}

	@Override
	public int size() {
		return size(fSmallestClass);
	}

	public int size(Class<?> className) {
		Map<Key, Value> values = fValues.get(className);
		if (values != null) {
			return values.size();
		}
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return isEmpty(fSmallestClass);
	}

	public boolean isEmpty(Class<?> className) {
		Map<Key, Value> values = fValues.get(className);
		if (values != null) {
			return values.isEmpty();
		}
		return true;
	}

	@Override
	public boolean containsKey(Object key) {
		return containsKey(fSmallestClass, key);
	}

	public boolean containsKey(Class<?> className, Object key) {
		Map<Key, Value> values = fValues.get(className);
		if (values != null) {
			return values.containsKey(key);
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return containsValue(fSmallestClass, value);
	}

	public boolean containsValue(Class<?> className, Object value) {
		Map<Key, Value> values = fValues.get(className);
		if (values != null) {
			return values.containsValue(value);
		}
		return false;
	}

	@Override
	public Value get(Object key) {
		return get(fSmallestClass, key);
	}

	public Value get(Class<?> className, Object key) {
		Map<Key, Value> values = fValues.get(fSmallestClass);
		if (values != null) {
			return values.get(key);
		}
		return null;
	}

	@Override
	public Value put(Key key, Value value) {
		if (key == null) {
			throw new IllegalArgumentException("Key of null is not allowed");
		} else if (value == null) {
			throw new IllegalArgumentException("Value of null is not allowed");
		} else {
			Value oldValue = remove(key);
	
			for (Class<?> className : getFullClassList(value.getClass())) {
				Map<Key, Value> values = fValues.get(className);
				if (values == null) {
					values = new HashMap<Key, Value>();
					fValues.put(className, values);
				}
				values.put(key, value);
			}
	
			return oldValue;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void putAll(Map<? extends Key, ? extends Value> m) {
		for (Map.Entry<?, ?> entry : m.entrySet()) {
			put((Key) entry.getKey(), (Value) entry.getValue());
		}
	}

	@Override
	public Value remove(Object key) {
		Map<Key, Value> values = fValues.get(fSmallestClass);
		if (values != null) {
			Value oldValue = values.remove(key);

			if (values.isEmpty()) {
				clear();
			} else if (oldValue != null) {
				for (Class<?> className : getFullClassList(oldValue.getClass())) {
					if (className.equals(fSmallestClass)) {
						continue;
					}

					values = fValues.get(className);
					if (values == null) {
						throw new IllegalStateException("Should not be possible.");
					} else if (values.remove(key) != oldValue) {
						throw new IllegalStateException("Should not be possible.");
					} else if (values.isEmpty()) {
						fValues.remove(className);
					}

				}
			}

			return oldValue;
		}
		return null;
	}

	@Override
	public void clear() {
		fValues.clear();
	}

	@Override
	public Set<Key> keySet() {
		return keySet(fSmallestClass);
	}

	@SuppressWarnings("unchecked")
	public <V1 extends Value> Set<Key> keySet(Class<V1> className) {
		Map<Key, V1> values = (Map<Key, V1>) fValues.get(className);
		if (values != null) {
			return Collections.unmodifiableSet(values.keySet());
		}
		return Collections.unmodifiableSet(new HashSet<Key>());
	}

	@Override
	public Collection<Value> values() {
		return values(fSmallestClass);
	}

	@SuppressWarnings("unchecked")
	public <V1 extends Value> Collection<V1> values(Class<V1> className) {
		Map<Key, V1> values = (Map<Key, V1>) fValues.get(className);
		if (values != null) {
			return Collections.unmodifiableCollection(values.values());
		}
		return Collections.unmodifiableCollection(new HashSet<V1>());
	}

	@Override
	public Set<Map.Entry<Key, Value>> entrySet() {
		return entrySet(fSmallestClass);
	}

	@SuppressWarnings("unchecked")
	public <V1 extends Value> Set<Map.Entry<Key, V1>> entrySet(Class<V1> className) {
		Map<Key, V1> values = (Map<Key, V1>) fValues.get(className);
		if (values != null) {
			return Collections.unmodifiableMap(values).entrySet();
		}
		return Collections.unmodifiableSet(new HashSet<Map.Entry<Key, V1>>());
	}
}
