package joe.classes.bundle;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Values {
	public static String getStringValue(String value, String defaultValue) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return value;
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static String getStringValue(Object value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof String) {
			return getStringValue((String)value, defaultValue);
		} else {
			return getStringValue(value.toString(), defaultValue);
		}
	}
	
	public static Boolean getBooleanValue(String value, Boolean defaultValue) {
		try {
			if ("FALSE".equalsIgnoreCase(value)) {
				return false;
			} else if ("TRUE".equalsIgnoreCase(value)) {
				return true;
			}
			return defaultValue;
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Boolean getBooleanValue(Object value, Boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		} else {
			return getBooleanValue(value.toString(), defaultValue);
		}
	}
	
	public static Character getCharacterValue(String value, Character defaultValue) {
		try {
			if (value.length() == 1) {
				return value.charAt(0);
			}
			return defaultValue;
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Character getCharacterValue(Object value, Character defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Character) {
			return (Character) value;
		} else if (value instanceof String) {
			return getCharacterValue((String)value, defaultValue);
		} else {
			return getCharacterValue(value.toString(), defaultValue);
		}
	}
	
	public static Byte getByteValue(String value, Byte defaultValue) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return Byte.parseByte(value);
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Byte getByteValue(Object value, Byte defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Number) {
			return ((Number) value).byteValue();
		} else if (value instanceof Byte) {
			return getByteValue((String)value, defaultValue);
		} else {
			return getByteValue(value.toString(), defaultValue);
		}
	}
	
	public static Short getShortValue(String value, Short defaultValue) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return Short.parseShort(value);
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Short getShortValue(Object value, Short defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Number) {
			return ((Number) value).shortValue();
		} else if (value instanceof String) {
			return getShortValue((String)value, defaultValue);
		} else {
			return getShortValue(value.toString(), defaultValue);
		}
	}
	
	public static Integer getIntegerValue(String value, Integer defaultValue) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return Integer.parseInt(value);
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Integer getIntegerValue(Object value, Integer defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Number) {
			return ((Number) value).intValue();
		} else if (value instanceof String) {
			return getIntegerValue((String)value, defaultValue);
		} else {
			return getIntegerValue(value.toString(), defaultValue);
		}
	}
	
	public static Long getLongValue(String value, Long defaultValue) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return Long.parseLong(value);
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Long getLongValue(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Number) {
			return ((Number) value).longValue();
		} else if (value instanceof String) {
			return getLongValue((String)value, defaultValue);
		} else {
			return getLongValue(value.toString(), defaultValue);
		}
	}
	
	public static Float getFloatValue(String value, Float defaultValue) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return Float.parseFloat(value);
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Float getFloatValue(Object value, Float defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Number) {
			return ((Number) value).floatValue();
		} else if (value instanceof String) {
			return getFloatValue((String)value, defaultValue);
		} else {
			return getFloatValue(value.toString(), defaultValue);
		}
	}
	
	public static Double getDoubleValue(String value, Double defaultValue) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return Double.parseDouble(value);
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static Double getDoubleValue(Object value, Double defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Number) {
			return ((Number) value).doubleValue();
		} else if (value instanceof String) {
			return getDoubleValue((String)value, defaultValue);
		} else {
			return getDoubleValue(value.toString(), defaultValue);
		}
	}
	
	public static <T extends Enum<T>> T getEnumValue(String value, T defaultValue, Class<T> type) {
		try {
			if (value == null) {
				return defaultValue;
			}
			return Enum.valueOf(type, value);
		} catch (Throwable t) {
			return defaultValue;
		}
	}
	
	public static <T extends Enum<T>> T getEnumValue(Object value, T defaultValue, Class<T> type) {
		if (value == null) {
			return defaultValue;
		} else {
			return getEnumValue(value.toString(), defaultValue, type);
		}
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getValue(Object value, T defaultValue, Class<T> type) {
		try {
			if (value == null) {
				return defaultValue;
			} else if (type.isInstance(value)) {
				return type.cast(value);
			} else if (type.isEnum()) {
				return (T) getEnumValue(value, (Enum)defaultValue, (Class<Enum>)type);
			} else if (type.equals(String.class)) {
				return (T) getStringValue(value, (String)defaultValue);
			} else if (type.isPrimitive()) {
				if (type.equals(Boolean.class)) {
					return (T) getBooleanValue(value, (Boolean)defaultValue);
				} else if (type.equals(Character.class)) {
					return (T) getCharacterValue(value, (Character)defaultValue);
				} else if (type.equals(Byte.class)) {
					return (T) getByteValue(value, (Byte)defaultValue);
				} else if (type.equals(Short.class)) {
					return (T) getShortValue(value, (Short)defaultValue);
				} else if (type.equals(Integer.class)) {
					return (T) getIntegerValue(value, (Integer)defaultValue);
				} else if (type.equals(Long.class)) {
					return (T) getLongValue(value, (Long)defaultValue);
				} else if (type.equals(Float.class)) {
					return (T) getFloatValue(value, (Float)defaultValue);
				} else if (type.equals(Double.class)) {
					return (T) getDoubleValue(value, (Double)defaultValue);
				}
			}
			
			// Check if class has a StringParser method
			for (Method method : type.getMethods()) {
				if (Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(type)) {
					return (T) method.invoke(null, value.toString());
				}
			}
			
			return defaultValue;
		} catch (Throwable t) {
			return defaultValue;
		}
	}
}
