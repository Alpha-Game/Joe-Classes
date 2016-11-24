package joe.classes.geometry2D;

import java.io.Serializable;
import java.util.Objects;

import joe.classes.bundle.StringParser;

public class Vector2D implements Cloneable, Serializable {
	protected static final String YVALUE = "Y";
	protected static final String XVALUE = "X";
	protected static final String MAGNITUDE = "M";
	protected static final String ANGLE = "A";
	protected static final String VALUE_CONTAINER_START = "<";
	protected static final String VALUE_CONTAINER_END = ">";
	protected static final String DIVIDER = ",";
	
	private static final long serialVersionUID = 4978854626490886757L;
	
	protected double fValueX;
	protected double fValueY;
	
	protected Vector2D(String stringValue) {
		String[] values = stringValue.split(DIVIDER);
		if (values.length < 2) {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Vector2D: Input<" + stringValue + ">");
		}
		
		Double X = null;
		Double Y = null;
		Double Magnitude = null;
		Double Angle = null;
		for (String value : values) {
			String trim = value.trim();
			if (!trim.endsWith(VALUE_CONTAINER_END)) {
				throw new IllegalArgumentException("Invalid parameter: Input<" + stringValue + "> Parameter<" + trim + ">");
			}
			if (trim.startsWith(XVALUE + VALUE_CONTAINER_START)) {
				if (X != null) {
					throw new IllegalArgumentException(XVALUE + " is defined twice: Input<" + stringValue + ">");
				}
				X = Double.parseDouble(trim.substring(XVALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(YVALUE + VALUE_CONTAINER_START)) {
				if (Y != null) {
					throw new IllegalArgumentException(YVALUE + " is defined twice: Input<" + stringValue + ">");
				}
				Y = Double.parseDouble(trim.substring(YVALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(MAGNITUDE + VALUE_CONTAINER_START)) {
				if (Magnitude != null) {
					throw new IllegalArgumentException(MAGNITUDE + " is defined twice: Input<" + stringValue + ">");
				}
				Magnitude = Double.parseDouble(trim.substring(MAGNITUDE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(ANGLE + VALUE_CONTAINER_START)) {
				if (Angle != null) {
					throw new IllegalArgumentException(ANGLE + " is defined twice: Input<" + stringValue + ">");
				}
				Angle = Double.parseDouble(trim.substring(ANGLE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else {
				throw new IllegalArgumentException("Invalid parameter: Input<" + stringValue + "> Parameter<" + trim + ">");
			}
		}
		
		if (X != null && Y != null) {
			fValueX = X;
			fValueY = Y;
			if (Magnitude != null && Magnitude != getMagnitude(getValueX(), getValueY())) {
				throw new IllegalArgumentException("Calulated Magnitude is incorrect: Input<" + stringValue + "> Calulated Magnitude<" + getMagnitude(getValueX(), getValueY()) + ">");
			}
			if (Angle != null && Angle != getAngle(getValueX(), getValueY())) {
				throw new IllegalArgumentException("Calulated Angle is incorrect: Input<" + stringValue + "> Calulated Angle<" + getAngle(getValueX(), getValueY()) + ">");
			}
		} else if (Magnitude != null && Angle != null) {
			fValueX = getValueX(Magnitude, Angle);
			fValueY = getValueY(Magnitude, Angle);
			if (X != null && X != getValueX()) {
				throw new IllegalArgumentException("Calulated X is incorrect: Input<" + stringValue + "> Calulated X<" + getValueX() + ">");
			}
			if (Y != null && Y != getValueY()) {
				throw new IllegalArgumentException("Calulated Y is incorrect: Input<" + stringValue + "> Calulated Y<" + getValueY() + ">");
			}
		} else {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Vector2D: Input<" + stringValue + ">");
		}
	}
	
	public Vector2D(double valueX, double valueY) {
		fValueX = valueX;
		fValueY = valueY;
	}
	
	public static Vector2D fromMagnitude(double magnitude, double angleInDegreesFromXAxis) {
		return new Vector2D(getValueX(magnitude, angleInDegreesFromXAxis), getValueY(magnitude, angleInDegreesFromXAxis));
	}
	
	@StringParser
	public static Vector2D fromString(String stringValue) {
		return new Vector2D(stringValue);
	}
	
	public static double getAngle(double valueX, double valueY) {
		return Math.toDegrees(Math.atan(valueY/valueX));
	}
	
	public static double getMagnitude(double valueX, double valueY) {
		return Math.pow((valueX * valueX) + (valueY * valueY), 0.5);
	}
	
	public static double getValueX(double magnitude, double angleInDegreesFromXAxis) {
		if ((angleInDegreesFromXAxis % 90 == 0) && (angleInDegreesFromXAxis % 180 != 0)) {
			return 0.0;
		}
		return magnitude * Math.cos(Math.toRadians(angleInDegreesFromXAxis));
	}
	
	public static double getValueY(double magnitude, double angleInDegreesFromXAxis) {
		if (angleInDegreesFromXAxis % 180 == 0) {
			return 0.0;
		}
		return magnitude * Math.sin(Math.toRadians(angleInDegreesFromXAxis));
	}
	
	public static Vector2D addVectors(Vector2D vector1, Vector2D vector2) {
		return new Vector2D(vector1.getValueX() + vector2.getValueX(), vector1.getValueY() + vector2.getValueY());
	}
	
	public static Vector2D multiply(Vector2D vector, double value) {
		return new Vector2D(value * vector.getValueX(), value * vector.getValueY());
	}
	
	public static Vector2D multiply(Vector2D vector1, Vector2D vector2) {
		return new Vector2D(vector1.getValueX() * vector2.getValueX(), vector1.getValueY() * vector2.getValueY());
	}
	
	public static Vector2D inverse(Vector2D vector) {
		return multiply(vector, -1.0);
	}
	
	public double getAngle() {
		return getAngle(getValueX(), getValueY());
	}
	
	public double getMagnitude() {
		return getMagnitude(getValueX(), getValueY());
	}	
	
	public double getValueX() {
		return fValueX;
	}
	
	public double getValueY() {
		return fValueY;
	}
	
	public Vector2D addVector(Vector2D vector) {
		return addVectors(this, vector);
	}
	
	public Vector2D multiply(double value) {
		return multiply(this, value);
	}
	
	public Vector2D multiply(Vector2D vector) {
		return multiply(this, vector);
	}
	
	public Vector2D inverse() {
		return inverse(this);
	}
	
	@Override
	public Vector2D clone() {
		return multiply(this, 1.0);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2D) {
			return ((Vector2D) obj).getValueX() == getValueX() && ((Vector2D) obj).getValueY() == getValueY();
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return XVALUE + VALUE_CONTAINER_START + getValueX() + VALUE_CONTAINER_END + DIVIDER
				+ YVALUE + VALUE_CONTAINER_START + getValueY() + VALUE_CONTAINER_END; 
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getValueX(), getValueY());
	}
}
