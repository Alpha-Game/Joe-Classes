package joe.classes.controls;

import java.util.LinkedList;
import java.util.List;

public class Controls {
	private final List<Control> fControls;
	
	protected Controls() {
		fControls = new LinkedList<Control>();
	}
	
	public Controls(Control... controls) {
		this();
		for (Control control : controls) {
			fControls.add(control);
		}
	}
	
	public Controls(Iterable<Control> controls) {
		this();
		for (Control control : controls) {
			fControls.add(control);
		}
	}
	
	public Iterable<Control> getControls() {
		return fControls;
	}
}
