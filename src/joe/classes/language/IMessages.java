package joe.classes.language;

import java.util.Locale;

public interface IMessages {
	Locale getLocale();
	void setLocale(Locale locale);
	
	Message getMessage(String messageID);
}
