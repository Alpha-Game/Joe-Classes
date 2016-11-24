package joe.classes.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListenerList<T> implements Iterable<T> {
	public class ListenerListIterator<K> implements Iterator<K> {
		private Iterator<EventIdentifier<K>> fIterator;
		
		private ListenerListIterator(Iterator<EventIdentifier<K>> iterator) {
			fIterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return fIterator.hasNext();
		}

		@Override
		public K next() {
			return fIterator.next().getObject();
		}

		@Override
		public void remove() {
			fIterator.remove();
		}
		
	}
	
	private int fNextKey;
	private Map<Integer, EventIdentifier<T>> fListeners;
	
	public ListenerList() {
		fNextKey = 0;
		fListeners = new HashMap<Integer, EventIdentifier<T>>();
	}
	
	public EventIdentifier<T> addListener(T listener) {
		EventIdentifier<T> event = new EventIdentifier<T>(fNextKey++, this, listener);
		fListeners.put(event.getID(), event);
		return event;
	}

	@Override
	public Iterator<T> iterator() {
		return new ListenerListIterator<T>(fListeners.values().iterator());
	}
	
	public boolean removeListener(EventIdentifier<T> listener) {
		return fListeners.remove(listener.getID()) != null;
	}
}
