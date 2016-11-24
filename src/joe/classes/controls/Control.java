package joe.classes.controls;

public class Control {
	private final String fDeviceID;
	private final String fComponentID;
	
	public Control(String deviceID, String componentID) {
		fDeviceID = deviceID;
		fComponentID = componentID;
	}
	
	public String getDevice() {
		return fDeviceID;
	}
	
	public String getComponent() {
		return fComponentID;
	}
}
