package joe.classes.bundle;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class Bundle {
	private ResourceBundle fBundle;
	
	private class KeysIterable implements Iterable<String> {
		private class KeysIterator implements Iterator<String> {
			private Enumeration<String> fKeys;
			
			KeysIterator(Enumeration<String> keys) {
				fKeys = keys;
			}
			
			@Override
			public boolean hasNext() {
				return fKeys.hasMoreElements();
			}

			@Override
			public String next() {
				return fKeys.nextElement();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException(KeysIterable.class.getName() + ".remove()");
			}
			
		}
		
		private Bundle fBundle;
		
		KeysIterable(Bundle bundle) {
			fBundle = bundle;
		}

		@Override
		public Iterator<String> iterator() {
			return new KeysIterator(fBundle.fBundle.getKeys());
		}
		
	}
	
	public Bundle(Locale locale, String location, String bundleName) {
		try {
			File file = new File(location);
			URL[] urls = {file.toURI().toURL()};
			ClassLoader loader = new URLClassLoader(urls);
			fBundle = ResourceBundle.getBundle(bundleName, Locale.getDefault(), loader);
		} catch (RuntimeException e) {
			throw e;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	public Iterable<String> getKeys() {
		return new KeysIterable(this);
	}
	
	public boolean hasKey(String key) {
		return fBundle.containsKey(key);
	}
	
	public String getRawValue(String key) {
		try {
			return fBundle.getString(key);
		} catch (Throwable t) {
			return null;
		}
	}
	
	public Boolean getBooleanValue(String key, Boolean defaultValue) {
		return Values.getBooleanValue(getRawValue(key), defaultValue);
	}
	
	public String getStringValue(String key, String defaultValue) {
		return Values.getStringValue(getRawValue(key), defaultValue);
	}
	
	public Integer getIntegerValue(String key, Integer defaultValue) {
		return Values.getIntegerValue(getRawValue(key), defaultValue);
	}
	
	public Long getLongValue(String key, Long defaultValue) {
		return Values.getLongValue(getRawValue(key), defaultValue);
	}
	
	public Float getFloatValue(String key, Float defaultValue) {
		return Values.getFloatValue(getRawValue(key), defaultValue);
	}
	
	public Double getDoubleValue(String key, Double defaultValue) {
		return Values.getDoubleValue(getRawValue(key), defaultValue);
	}
	
	public <T extends Enum<T>> T getEnumValue(String key, T defaultValue, Class<T> type) {
		return Values.getEnumValue(getRawValue(key), defaultValue, type);
	}
	
	public <T> T getValue(String key, T defaultValue, Class<T> type) {
		return Values.getValue(getRawValue(key), defaultValue, type);
	}
}
