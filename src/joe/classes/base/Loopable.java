package joe.classes.base;

import joe.classes.constants.GlobalConstants;

public class Loopable {
	private Boolean fIsStopping;
	private Boolean fIsRunning;
	private ILoopable fLoop;
	private Thread fThread;

	public Loopable(ILoopable loop) {
		fIsStopping = true;
		fIsRunning = false;
		fLoop = loop;
	}
		
	private void loop() {
		fLoop.initialize();
		while(true) {
			synchronized(fIsStopping) {
				if (fIsStopping) {
					break;
				}
			}
			long sleepTime = 0;
			try {
				sleepTime = fLoop.run();
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			}
			if (sleepTime == -1) {
				break;
			}
			if (sleepTime > 0) {
				try {
					Thread.sleep((sleepTime / (GlobalConstants.NANOSECONDS / GlobalConstants.MILLISECONDS)), (int)(sleepTime % (GlobalConstants.NANOSECONDS / GlobalConstants.MILLISECONDS)));
				} catch (InterruptedException e) {
					// Break on next loop, if required
				}
			}
		}
		fLoop.destroy();
		fIsRunning = false;
	}
	
	public void start() {
		synchronized(fIsStopping) {
			if (fIsStopping) {
				fIsStopping = false;
				fIsRunning = true;
				fThread = new Thread(new RunThis(this), fLoop.name());
				fThread.start();
			}
		}
	}
	
	public void stop() {
		synchronized(fIsStopping) {
			if (!fIsStopping) {
				fIsStopping = true;
				fThread.interrupt();
			}
		}
	}
	
	public boolean isRunning() {
		synchronized(fIsRunning) {
			return fIsRunning;
		}
	}
	
	private static class RunThis implements Runnable {
		private Loopable fLoop;
		
		private RunThis(Loopable loop) {
			fLoop = loop;
		}

		@Override
		public void run() {
			fLoop.loop();
		}
	}
}
