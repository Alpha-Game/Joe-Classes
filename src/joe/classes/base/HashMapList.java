package joe.classes.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;


public class HashMapList<KeyClass, ItemClass> {
	protected Map<KeyClass, Node<KeyClass, ItemClass>> fSet;
	protected Node<KeyClass, ItemClass> fFirstNode;
	protected Node<KeyClass, ItemClass> fLastNode;
	
	public HashMapList() {
		fSet = new HashMap<KeyClass, Node<KeyClass, ItemClass>>();
		fFirstNode = null;
		fLastNode = null;
	}
	
	public ItemClass add(int position, KeyClass key, ItemClass item) {
		if (position < 0 || position > getSize()) {
			throw new ArrayIndexOutOfBoundsException(position);
		}
		
		Node<KeyClass, ItemClass> prevNode = fSet.remove(key);
		if (prevNode != null) {
			removeNode(prevNode);
		} else if (position == 0) {
			if (fFirstNode == null) {
				fFirstNode = createNode(key, item);
				fLastNode = fFirstNode;
			} else {
				fFirstNode = addBefore(fFirstNode, key, item);
			}
			fSet.put(key, fFirstNode);
		} else if (position == getSize()) {
			if (fLastNode == null) {
				fFirstNode = createNode(key, item);
				fLastNode = fFirstNode;
			} else {
				fLastNode = addAfter(fLastNode, key, item);
			}
		} else {
			addAfter(getNode(position), key, item);
		}
		
		return prevNode != null ? prevNode.item : null;
	}
	
	protected Node<KeyClass, ItemClass> createNode(KeyClass key, ItemClass item) {
		Node<KeyClass, ItemClass> newNode = new Node<KeyClass, ItemClass>(key, item);
		fSet.put(key, newNode);
		return newNode;
	}
	
    protected Node<KeyClass, ItemClass> addBefore(Node<KeyClass, ItemClass> nextNode, KeyClass key, ItemClass item) {
    	Node<KeyClass, ItemClass> newNode = createNode(key, item);
    	newNode.prev = nextNode.prev;
    	newNode.next = nextNode;
    	nextNode.prev = newNode;
    	return newNode;
    }
    
    protected Node<KeyClass, ItemClass> addAfter(Node<KeyClass, ItemClass> prevNode, KeyClass key, ItemClass item) {
    	Node<KeyClass, ItemClass> newNode = createNode(key, item);
    	newNode.prev = prevNode;
    	newNode.next = prevNode.next;
    	prevNode.next = newNode;
    	return newNode;
    }
	
	protected Node<KeyClass, ItemClass> getNode(int position) {
		if (position >= getSize()) {
			return null;
		} else if (position < (-1 * getSize())) {
			return null;
		} else if (position < 0) {
			return getNode(position + getSize());
		} else if (position < getSize() / 2) {
			Node<KeyClass, ItemClass> node = fFirstNode;
			for (int i = 0; i < position; i++) {
				node = node.next;
			}
			return node;
		} else {
			Node<KeyClass, ItemClass> node = fLastNode;
			for (int i = getSize() - 1; i > position; i--) {
				node = node.prev;
			}
			return node;
		}
	}
	
	public boolean containsKey(Object key) {
		return fSet.containsKey(key);
	}
	
	public int getSize() {
		return fSet.size();
	}
	
	public KeyClass getKeyByIndex(int position) {
		Node<KeyClass, ItemClass> node = getNode(position);
		if (node != null) {
			return node.key;
		}
		return null;
	}
	
	public ItemClass getItemByIndex(int position) {
		Node<KeyClass, ItemClass> node = getNode(position);
		if (node != null) {
			return node.item;
		}
		return null;
	}
	
	public ItemClass getItemByKey(Object key) {
		Node<KeyClass, ItemClass> node = fSet.get(key);
		if (node != null) {
			return node.item;
		}
		return null;
	}
	
	public ItemClass setItemByIndex(int position, ItemClass item) {
		Node<KeyClass, ItemClass> node = getNode(position);
		if (node != null) {
			ItemClass oldItem = node.item;
			node.item = item;
			return oldItem;
		}
		throw new IllegalArgumentException("Node does not exist");
	}
	
	public ItemClass setItemByKey(Object key, ItemClass item) {
		Node<KeyClass, ItemClass> node = fSet.get(key);
		if (node != null) {
			ItemClass oldItem = node.item;
			node.item = item;
			return oldItem;
		}
		throw new IllegalArgumentException("Node does not exist");
	}
	
	protected ItemClass removeNode(Node<KeyClass, ItemClass> node) {
		fSet.remove(node.key);
		if (node.prev != null) {
			node.prev.next = node.next;
    	}
    	if (node.next != null) {
    		node.next.prev = node.prev;
    	}
		if (node == fFirstNode) {
			fFirstNode = node.next;
		}
		if (node == fLastNode) {
			fLastNode = node.prev;
		}
		return node.item;
	}
	
	public ItemClass removeByIndex(int position) {
		Node<KeyClass, ItemClass> oldNode = getNode(position);
		if (oldNode != null) {
			return removeNode(oldNode);
		}
		return null;
	}
	
	public ItemClass removeByKey(Object key) {
		Node<KeyClass, ItemClass> oldNode = fSet.remove(key);
		if (oldNode != null) {
			return removeNode(oldNode);
		}
		return null;
	}
	
	public HashMapListIterable<KeyClass, ItemClass> iterator() {
		return new HashMapListIterable<KeyClass, ItemClass>(this);
	}
	
	public static class Node<KeyClass, ItemClass> implements Entry<KeyClass, ItemClass> {
		protected final KeyClass key;
		protected ItemClass item;
	    protected Node<KeyClass, ItemClass> next;
	    protected Node<KeyClass, ItemClass> prev;
	    
	    protected Node(KeyClass key, ItemClass item)
	    {
	    	this.key = key;
			this.item = item;
	    }
	    
	    @Override
	    public KeyClass getKey() {
	    	return key;
	    }

		@Override
		public ItemClass getValue() {
			return item;
		}

		@Override
		public ItemClass setValue(ItemClass item) {
			ItemClass oldItem = this.item;
			this.item = item;
			return oldItem;
		}
		
		@Override
		@SuppressWarnings("rawtypes")
		public boolean equals(Object object) {
			if (object instanceof Node) {
				return ((Node) object).getKey().equals(key);
			}
			return key.equals(object);
		}
	    
		@Override
	    public int hashCode() {
	    	return key.hashCode();
	    }
	}
	
	protected static class HashMapListIterable<KeyClass, ItemClass> implements Iterable<Node<KeyClass, ItemClass>> {
		protected static class HashMapListIterator<KeyClass, ItemClass> implements Iterator<Node<KeyClass, ItemClass>> {
			protected HashMapList<KeyClass, ItemClass> fMap;
			protected Node<KeyClass, ItemClass> fCurrentNode;
			
			public HashMapListIterator(HashMapList<KeyClass, ItemClass> map) {
				fMap = map;
				fCurrentNode = null;
			}

			public boolean hasPrevious() {
				if (fCurrentNode == null) {
					return fMap.fLastNode != null;
				}
				return fCurrentNode.prev != null;
			}
			
			@Override
			public boolean hasNext() {
				if (fCurrentNode == null) {
					return fMap.fFirstNode != null;
				}
				return fCurrentNode.next != null;
			}

			public Node<KeyClass, ItemClass> previous() {
				if (!hasPrevious()) {
					throw new NoSuchElementException();
			    }
				if (fCurrentNode == null) {
					fCurrentNode = fMap.fLastNode;
				} else {
					fCurrentNode = fCurrentNode.prev;
				}
				return fCurrentNode;
			}

			@Override
			public Node<KeyClass, ItemClass> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
			    }
				if (fCurrentNode == null) {
					fCurrentNode = fMap.fFirstNode;
				} else {
					fCurrentNode = fCurrentNode.next;
				}
				return fCurrentNode;
			}

			@Override
			public void remove() {
				if (fCurrentNode == null) {
					throw new NoSuchElementException();
				}
				fMap.removeNode(fCurrentNode);
				fCurrentNode = fCurrentNode.prev;
			}
		}
		
		protected HashMapList<KeyClass, ItemClass> fMap;
		
		public HashMapListIterable(HashMapList<KeyClass, ItemClass> map) {
			fMap = map;
		}
		
		@Override
		public Iterator<Node<KeyClass, ItemClass>> iterator() {
			return new HashMapListIterator<KeyClass, ItemClass>(fMap);
		}
	}
}
