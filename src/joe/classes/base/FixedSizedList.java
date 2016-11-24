package joe.classes.base;

public class FixedSizedList<V> {
	private final Object[] fList; 
	private int fIndex;
	
	public FixedSizedList(int size) throws IllegalArgumentException {
		if (size < 1) {
			throw new IllegalArgumentException(String.format("Size <%d> cannot be a value less than one.", size));
		}
		
		fIndex = 0;
		fList = new Object[size];
	}
	
	@SuppressWarnings("unchecked")
	public FixedSizedList(int size, V... elements) {
		this(size);
		for (V element : elements) {
			add(element);
		}
	}
	
	public <V1 extends V> FixedSizedList(int size, Iterable<V1> elements) {
		this(size);
		for (V element : elements) {
			add(element);
		}
	}

	public int size() {
		return fList.length;
	}

	public void clear() {
		for (int i = 0; i < fList.length; i++) {
			fList[i] = null;
		}
	}

	@SuppressWarnings("unchecked")
	public V add(V newElement) {
		V oldElement = (V) fList[fIndex];
		
		fList[fIndex++] = newElement;
		if (fIndex >= fList.length) {
			fIndex = 0;
		}
		
		return oldElement;
	}

	@SuppressWarnings("unchecked")
	public V set(int position, V newElement) throws IndexOutOfBoundsException {
		if (position >= fList.length || position < 0) {
			throw new IndexOutOfBoundsException(String.format("Position <%d> is out of bounds compared to size() <%d>.", position, fList.length));
		}
		int index = (fIndex - (position + 1)) % fList.length;
		if (index < 0) {
			index += fList.length;
		}
		
		V oldElement = (V) fList[index];
		fList[index] = newElement;
		
		return oldElement;
	}
	
	@SuppressWarnings("unchecked")
	public V get(int position) throws IndexOutOfBoundsException {
		if (position >= fList.length || position < 0) {
			throw new IndexOutOfBoundsException(String.format("Position <%d> is out of bounds compared to size() <%d>.", position, fList.length));
		}
		int index = (fIndex - (position + 1)) % fList.length;
		if (index < 0) {
			index += fList.length;
		}
		
		return (V) fList[index];
	}
}
