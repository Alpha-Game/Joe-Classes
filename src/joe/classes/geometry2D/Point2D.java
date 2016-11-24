package joe.classes.geometry2D;

import java.io.Serializable;
import java.util.Objects;

import joe.classes.bundle.StringParser;

public class Point2D implements Cloneable, Serializable {
	protected static final String XVALUE = "X";
	protected static final String YVALUE = "Y";
	protected static final String VALUE_CONTAINER_START = "<";
	protected static final String VALUE_CONTAINER_END = ">";
	protected static final String DIVIDER = ",";

	private static final long serialVersionUID = -3475498910333801993L;

	protected double fX;
	protected double fY;
	
	protected Point2D(String stringValue) {
		String[] values = stringValue.split(DIVIDER);
		if (values.length < 2) {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Point2D: Input<" + stringValue + ">");
		}
		
		Double Width = null;
		Double Height = null;
		for (String value : values) {
			String trim = value.trim();
			if (!trim.endsWith(VALUE_CONTAINER_END)) {
				throw new IllegalArgumentException("Invalid parameter: Input<" + stringValue + "> Parameter<" + trim + ">");
			}
			if (trim.startsWith(XVALUE + VALUE_CONTAINER_START)) {
				if (Width != null) {
					throw new IllegalArgumentException(XVALUE + " is defined twice: Input<" + stringValue + ">");
				}
				Width = Double.parseDouble(trim.substring(XVALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(YVALUE + VALUE_CONTAINER_START)) {
				if (Height != null) {
					throw new IllegalArgumentException(YVALUE + " is defined twice: Input<" + stringValue + ">");
				}
				Height = Double.parseDouble(trim.substring(YVALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else {
				throw new IllegalArgumentException("Invalid parameter: Input<" + stringValue + "> Parameter<" + trim + ">");
			}
		}
		
		if (Width != null && Height != null) {
			fX = Width;
			fY = Height;
		} else {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Point2D: Input<" + stringValue + ">");
		}
	}
	
	public Point2D(java.awt.geom.Point2D point) {
		this(point.getX(), point.getY());
	}
	
	public Point2D(double x, double y) {
		fX = x;
		fY = y;
	}
	
	public java.awt.geom.Point2D getPoint() {
		return new java.awt.geom.Point2D.Double(getX(), getY());
	}
	
	@StringParser
	public static Point2D fromString(String stringValue) {
		return new Point2D(stringValue);
	}
	
	protected Point2D create(double x, double y) {
		return new Point2D(x, y);
	}
	
	public double getX() {
		return fX;
	}
	
	public double getY() {
		return fY;
	}
	
	@Override
	public Point2D clone() {
		return create(getX(), getY());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point2D) {
			return ((Point2D) obj).getX() == getX() && ((Point2D) obj).getY() == getY();
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return XVALUE + VALUE_CONTAINER_START + getX() + VALUE_CONTAINER_END + DIVIDER
				+ YVALUE + VALUE_CONTAINER_START + getY() + VALUE_CONTAINER_END;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY());
	}

}
