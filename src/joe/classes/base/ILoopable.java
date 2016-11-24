package joe.classes.base;

public interface ILoopable {
	String name();
	
	void initialize();
	
	/**
	 * 
	 * @return The number of nanoseconds to sleep between each loop. 0 will immediately start next loop. -1 will stop looping.
	 */
	long run();
	
	void destroy();
}
