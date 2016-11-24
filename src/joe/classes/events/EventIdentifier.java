package joe.classes.events;

public class EventIdentifier<T> {
	private int fID;
	private ListenerList<T> fParent;
	private T fEventObject;
	
	protected EventIdentifier(int id, ListenerList<T> parent, T eventObject) {
		fID = id;
		fParent = parent;
		fEventObject = eventObject;
	}
	
	public int getID() {
		return fID;
	}
	
	public ListenerList<T> getList() {
		return fParent;
	}
	
	public T getObject() {
		return fEventObject;
	}
}
