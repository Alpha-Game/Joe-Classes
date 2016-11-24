package joe.classes.language;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import joe.classes.bundle.Bundle;

public abstract class AbstractMessages implements IMessages {
	private final Map<String, Message> fMessages;
	
	private Locale fLocale;
	private Bundle fBundle;
	
	protected AbstractMessages() {
		fMessages = new HashMap<String, Message>();
	}
	
	protected AbstractMessages(Locale locale) {
		this();
		setLocale(locale);
	}
	
	@Override
	public void setLocale(Locale locale) {
		fLocale = locale;
	}
	
	@Override
	public Locale getLocale() {
		return fLocale;
	}
	
	protected void setBundle(Locale locale, String location, String bundleName) {
		fBundle = new Bundle(locale, location, bundleName);
	}
	
	protected Message setMessage(Message message, String messageID, String messageDefault) {
		message = setMessage(message, fBundle.getStringValue(messageID, messageDefault));
		fMessages.put(messageID, message);
		return message;
	}
	
	private  Message setMessage(Message message, String value) {
		if (message == null) {
			return new Message(value);
		} else {
			message.setMessage(value);
			return message;
		}
	}
	
	@Override
	public Message getMessage(String messageID) {
		return fMessages.get(messageID);
	}
}
