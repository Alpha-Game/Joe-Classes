package joe.classes.identifier;

public interface IMappable {
	String getIdentifier();
	
	public static class Mappable {
		public static int nextIdentifier = 0;
		
		public static String generateUniqueIdentifier() {
			return String.format("%1d-%2d", nextIdentifier++, System.nanoTime());
		}
	}
}
