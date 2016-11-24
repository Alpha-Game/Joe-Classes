package joe.classes.language;

import joe.classes.events.ListenerList;
import joe.classes.events.EventIdentifier;

public class Message {
	private String fMessage;
	private ListenerList<IMessageChangeListener> fListeners;
	
	protected Message(String message) {
		fMessage = message;
		fListeners = new ListenerList<IMessageChangeListener>();
	}
	
	protected void setMessage(String newMessage) {
		String oldMessage = fMessage;
		fMessage = newMessage;
		callListener(this, oldMessage);
	}
	
	protected void callListener(Message newMessage, String oldMessage) {
		for (IMessageChangeListener listener : fListeners) {
			listener.onChange(newMessage, oldMessage);
		}
	}
	
	public EventIdentifier<IMessageChangeListener> addListener(IMessageChangeListener listener) {
		return fListeners.addListener(listener);
	}
	
	public boolean removeListener(EventIdentifier<IMessageChangeListener> event) {
		return fListeners.removeListener(event);
	}
	
	public String getMessage() {
		return fMessage;
	}
}
