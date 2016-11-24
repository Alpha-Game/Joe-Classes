package joe.classes.geometry2D;

import java.io.Serializable;
import java.util.Objects;

import joe.classes.bundle.StringParser;

public class Rectangle2D implements IShape2D, Cloneable, Serializable {
	protected static final String XVALUE = "X";
	protected static final String YVALUE = "Y";
	protected static final String X1VALUE = "X1";
	protected static final String Y1VALUE = "Y1";
	protected static final String X2VALUE = "X2";
	protected static final String Y2VALUE = "Y2";
	protected static final String WIDTH = "W";
	protected static final String HEIGHT = "H";
	protected static final String VALUE_CONTAINER_START = "<";
	protected static final String VALUE_CONTAINER_END = ">";
	protected static final String DIVIDER = ",";
	
	private static final long serialVersionUID = 4298473246638458379L;
	
	protected double fX;
	protected double fY;
	protected double fWidth;
	protected double fHeight;
	
	protected Rectangle2D(String stringValue) {
		String[] values = stringValue.split(DIVIDER);
		if (values.length < 4) {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Rectangle2D: Input<" + stringValue + ">");
		}
		
		Double X = null;
		Double Y = null;
		Double X1 = null;
		Double Y1 = null;
		Double X2 = null;
		Double Y2 = null;
		Double Width = null;
		Double Height = null;
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
			} else if (trim.startsWith(X1VALUE + VALUE_CONTAINER_START)) {
				if (X1 != null) {
					throw new IllegalArgumentException(X1VALUE + " is defined twice: Input<" + stringValue + ">");
				}
				X1 = Double.parseDouble(trim.substring(X1VALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(Y1VALUE + VALUE_CONTAINER_START)) {
				if (Y1 != null) {
					throw new IllegalArgumentException(Y1VALUE + " is defined twice: Input<" + stringValue + ">");
				}
				Y1 = Double.parseDouble(trim.substring(Y1VALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(X2VALUE + VALUE_CONTAINER_START)) {
				if (X2 != null) {
					throw new IllegalArgumentException(X2VALUE + " is defined twice: Input<" + stringValue + ">");
				}
				X2 = Double.parseDouble(trim.substring(X2VALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(Y2VALUE + VALUE_CONTAINER_START)) {
				if (Y2 != null) {
					throw new IllegalArgumentException(Y2VALUE + " is defined twice: Input<" + stringValue + ">");
				}
				Y2 = Double.parseDouble(trim.substring(Y2VALUE.length() + VALUE_CONTAINER_START.length(), trim.length() - VALUE_CONTAINER_END.length()));
			} else if (trim.startsWith(WIDTH + VALUE_CONTAINER_START)) {
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
		
		// Check for illegal state
		if (X != null && X1 != null && X != X1) {
			throw new IllegalArgumentException("Both " + XVALUE + " and " + X1VALUE + " are defined: Input<" + stringValue + ">");
		}
		if (Y != null && Y1 != null && Y != Y1) {
			throw new IllegalArgumentException("Both " + YVALUE + " and " + Y1VALUE + " are defined: Input<" + stringValue + ">");
		}
		
		// Normalize values
		if (X != null) {
			X1 = X;
		}
		if (Y != null) {
			Y1 = Y;
		}
		
		if (X1 != null && Y1 != null && Width != null && Height != null) {
			fX = X1;
			fY = Y1;
			fWidth = Width;
			fHeight = Height;
			
			// Check for illegal state
			if (X2 != null && X2 != getX2()) {
				throw new IllegalArgumentException("Calulated X2 is incorrect: Input<" + stringValue + "> Calulated X2<" + getX2() + ">");
			}
			if (Y2 != null && Y2 != getY2()) {
				throw new IllegalArgumentException("Calulated Y2 is incorrect: Input<" + stringValue + "> Calulated Y2<" + getY2() + ">");
			}
		} else if (X1 != null && Y1 != null && X2 != null && Y2 != null) {
			fX = X1;
			fY = Y1;
			fWidth = X2 - X1;
			fHeight = Y2 - Y1;
			
			// Check for illegal state
			if (Width != null && Width != getWidth()) {
				throw new IllegalArgumentException("Calulated Width is incorrect: Input<" + stringValue + "> Calulated Width<" + getWidth() + ">");
			}
			if (Height != null && Height != getHeight()) {
				throw new IllegalArgumentException("Calulated Height is incorrect: Input<" + stringValue + "> Calulated Height<" + getHeight() + ">");
			}
		} else if (X1 != null && Y1 != null && Width != null && Y2 != null) {
			fX = X1;
			fY = Y1;
			fWidth = Width;
			fHeight = Y2 - Y1;
			
			// Check for illegal state
			if (X2 != null && X2 != getX2()) {
				throw new IllegalArgumentException("Calulated X2 is incorrect: Input<" + stringValue + "> Calulated X2<" + getX2() + ">");
			}
			if (Height != null && Height != getHeight()) {
				throw new IllegalArgumentException("Calulated Height is incorrect: Input<" + stringValue + "> Calulated Height<" + getHeight() + ">");
			}
		} else if (X1 != null && Y1 != null && X2 != null && Height != null) {
			fX = X1;
			fY = Y1;
			fWidth = X2 - X1;
			fHeight = Height;
			
			// Check for illegal state
			if (Width != null && Width != getWidth()) {
				throw new IllegalArgumentException("Calulated Width is incorrect: Input<" + stringValue + "> Calulated Width<" + getWidth() + ">");
			}
			if (Y2 != null && Y2 != getY2()) {
				throw new IllegalArgumentException("Calulated Y2 is incorrect: Input<" + stringValue + "> Calulated Y2<" + getY2() + ">");
			}
		} else if (Width != null && Height != null && X2 != null && Y2 != null) {
			fX = X2 - Width;
			fY = Y2 - Height;
			fWidth = Width;
			fHeight = Height;
			
			// Check for illegal state
			if (X1 != null && X1 != getX1()) {
				throw new IllegalArgumentException("Calulated X1 is incorrect: Input<" + stringValue + "> Calulated X1<" + getX1() + ">");
			}
			if (Y1 != null && Y1 != getY1()) {
				throw new IllegalArgumentException("Calulated Y1 is incorrect: Input<" + stringValue + "> Calulated Y1<" + getY1() + ">");
			}
		} else if (Width != null && Y1 != null && X2 != null && Y2 != null) {
			fX = X2 - Width;
			fY = Y1;
			fWidth = Width;
			fHeight = Y2 - Y1;
			
			// Check for illegal state
			if (X1 != null && X1 != getX1()) {
				throw new IllegalArgumentException("Calulated X1 is incorrect: Input<" + stringValue + "> Calulated X1<" + getX1() + ">");
			}
			if (Height != null && Height != getHeight()) {
				throw new IllegalArgumentException("Calulated Height is incorrect: Input<" + stringValue + "> Calulated Height<" + getHeight() + ">");
			}
		} else if (X1 != null && Height != null && X2 != null && Y2 != null) {
			fX = X1;
			fY = Y2 - Height;
			fWidth = X2 - X1;
			fHeight = Height;
			
			// Check for illegal state
			if (Width != null && Width != getWidth()) {
				throw new IllegalArgumentException("Calulated Width is incorrect: Input<" + stringValue + "> Calulated Width<" + getWidth() + ">");
			}
			if (Y1 != null && Y1 != getY1()) {
				throw new IllegalArgumentException("Calulated Y1 is incorrect: Input<" + stringValue + "> Calulated Y1<" + getY1() + ">");
			}
		} else {
			throw new IllegalArgumentException("Not enough parameters provided to correctly calculate a Rectangle2D: Input<" + stringValue + ">");
		}
	}
	
	public Rectangle2D(java.awt.geom.Rectangle2D rectangle) {
		this(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
	}
	
	public Rectangle2D(Point2D point, Dimension2D dimension) {
		this(point.getX(), point.getY(), dimension.getWidth(), dimension.getHeight());
	}
	
	public Rectangle2D(double x, double y, Dimension2D dimension) {
		this(x, y, dimension.getWidth(), dimension.getHeight());
	}
	
	public Rectangle2D(Point2D point, double width, double height) {
		this(point.getX(), point.getY(), width, height);
	}
	
	public Rectangle2D(double x, double y, double width, double height) {
		fX = x;
		fY = y;
		fWidth = width;
		fHeight = height;
	}
	
	public java.awt.geom.Rectangle2D getDimension() {
		return new java.awt.geom.Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
	}
	
	public static Rectangle2D fromBox(double x1, double y1, double x2, double y2) {
		return new Rectangle2D(x1, y1, x2 - x1, y2 - y1);
	}
	
	@StringParser
	public static Rectangle2D fromString(String stringValue) {
		return new Rectangle2D(stringValue);
	}
	
	protected Rectangle2D create(double x, double y, double width, double height) {
		return new Rectangle2D(x, y, width, height);
	}
	
	public double getX() {
		return fX;
	}
	
	public double getY() {
		return fY;
	}
	
	public double getCenterX() {
		return getX() + (getWidth() / 2.0);
	}
	
	public double getCenterY() {
		return getY() + (getHeight() / 2.0);
	}
	
	public double getWidth() {
		return fWidth;
	}
	
	public double getHeight() {
		return fHeight;
	}
	
	public double getX1() {
		return getX();
	}
	
	public double getY1() {
		return getY();
	}
	
	public double getX2() {
		return getX() + getWidth();
	}
	
	public double getY2() {
		return getY() + getHeight();
	}
	
	public double getMinX() {
		if (getWidth() < 0.0) {
			return getX2();
		} else {
			return getX1();
		}
	}
	
	public double getMinY() {
		if (getHeight() < 0.0) {
			return getY2();
		} else {
			return getY1();
		}
	}
	
	public double getMaxX() {
		if (getWidth() < 0.0) {
			return getX1();
		} else {
			return getX2();
		}
	}
	
	public double getMaxY() {
		if (getHeight() < 0.0) {
			return getY1();
		} else {
			return getY2();
		}
	}
	
	public Rectangle2D getAbsoluteRectangle() {
		return create(getMinX(), getMinY(), getMaxX(), getMaxY());
	}
	
	@Override
	public Rectangle2D clone() {
		return create(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle2D) {
			return ((Rectangle2D) obj).getX() == getX() && ((Rectangle2D) obj).getY() == getY() && ((Rectangle2D) obj).getWidth() == getWidth() && ((Rectangle2D) obj).getHeight() == getHeight();
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return XVALUE + VALUE_CONTAINER_START + getX() + VALUE_CONTAINER_END + DIVIDER
				+ YVALUE + VALUE_CONTAINER_START + getY() + VALUE_CONTAINER_END + DIVIDER
				+ WIDTH + VALUE_CONTAINER_START + getWidth() + VALUE_CONTAINER_END + DIVIDER
				+ HEIGHT + VALUE_CONTAINER_START + getHeight() + VALUE_CONTAINER_END;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public Rectangle2D getBoundRectnagle() {
		return this;
	}
}
