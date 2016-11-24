package joe.classes.geometry2D;

import java.io.Serializable;
import java.util.Objects;

import joe.classes.bundle.StringParser;

public class Dimension2D implements Cloneable, Serializable {
	protected static final String WIDTH = "W";
	protected static final String HEIGHT = "H";
	protected static final String VALUE_CONTAINER_START = "<";
	protected static final String VALUE_CONTAINER_END = ">";
	protected static final String DIVIDER = ",";

	private static final long serialVersionUID = -3475498910333801993L;

	protected double fWidth;
	protected double fHeight;
	
	protected Dimension2D(String stringValue) {
		String[] values = stringValue.split(DIVIDER);
		if (values.length < 2) {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Dimension2D: Input<" + stringValue + ">");
		}
		
		Double Width = null;
		Double Height = null;
		for (String value : values) {
			String trim = value.trim();
			if (!trim.endsWith(VALUE_CONTAINER_END)) {
				throw new IllegalArgumentException("Invalid parameter: Input<" + stringValue + "> Parameter<" + trim + ">");
			}
			if (trim.startsWith(WIDTH + VALUE_CONTAINER_START)) {
				if (Width != null) {
					throw new IllegalArgumentException(WIDTH + " is defined twice: Input<" + stringValue + ">");
				}
				Width = Double.parseDouble(trim.substring(WIDTH.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(HEIGHT + VALUE_CONTAINER_START)) {
				if (Height != null) {
					throw new IllegalArgumentException(HEIGHT + " is defined twice: Input<" + stringValue + ">");
				}
				Height = Double.parseDouble(trim.substring(HEIGHT.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else {
				throw new IllegalArgumentException("Invalid parameter: Input<" + stringValue + "> Parameter<" + trim + ">");
			}
		}
		
		if (Width != null && Height != null) {
			fWidth = Width;
			fHeight = Height;
		} else {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Dimension2D: Input<" + stringValue + ">");
		}
	}
	
	public Dimension2D(java.awt.geom.Dimension2D dimension) {
		this(dimension.getWidth(), dimension.getHeight());
	}
	
	public Dimension2D(double width, double height) {
		fWidth = width;
		fHeight = height;
	}
	
	public java.awt.geom.Dimension2D getDimension() {
		return new Dimension2DDouble(getWidth(), getHeight());
	}
	
	@StringParser
	public static Dimension2D fromString(String stringValue) {
		return new Dimension2D(stringValue);
	}
	
	protected Dimension2D create(double width, double height) {
		return new Dimension2D(width, height);
	}
	
	public double getWidth() {
		return fWidth;
	}
	
	public double getHeight() {
		return fHeight;
	}
	
	@Override
	public Dimension2D clone() {
		return create(getWidth(), getHeight());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Dimension2D) {
			return ((Dimension2D) obj).getWidth() == getWidth() && ((Dimension2D) obj).getHeight() == getHeight();
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return WIDTH + VALUE_CONTAINER_START + getWidth() + VALUE_CONTAINER_END + DIVIDER
				+ HEIGHT + VALUE_CONTAINER_START + getHeight() + VALUE_CONTAINER_END;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getWidth(), getHeight());
	}
	
	private static class Dimension2DDouble extends java.awt.geom.Dimension2D {
		private double fWidth;
		private double fHeight;
		
		public Dimension2DDouble(double width, double height) {
			fWidth = width;
			fHeight = height;
		}

		@Override
		public double getWidth() {
			return fWidth;
		}

		@Override
		public double getHeight() {
			return fHeight;
		}

		@Override
		public void setSize(double width, double height) {
			fWidth = width;
			fHeight = height;
		}
	}
}
