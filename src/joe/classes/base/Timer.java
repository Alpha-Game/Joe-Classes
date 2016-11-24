package joe.classes.base;

public class Timer {
	private long fStartTime;
	private long fLastLapTime;
	
	public Timer() {
		start();
	}
	
	protected long getTime() {
		return System.nanoTime();
	}
	
	public void start() {
		fStartTime = getTime();
		fLastLapTime = fStartTime;
	}
	
	public long lap() {
		long time = fLastLapTime;
		fLastLapTime = getTime();
		return fLastLapTime - time;
	}
	
	public long stop() {
		return fStartTime - getTime();
	}
}
