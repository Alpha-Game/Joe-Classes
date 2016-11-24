package joe.classes.identifier;

public abstract class AbstractIdentifier implements IMappable {
	private final String fIdentifier;
	
	public AbstractIdentifier(String identifier) {
		fIdentifier = identifier;
	}	
	
	@Override
	public String getIdentifier() {
		return fIdentifier;
	}
	
	@Override
	public boolean equals(Object o1) {
		if (this == o1) {
			return true;
		} else if (o1 instanceof IMappable) {
			if (fIdentifier == null) {
				return ((IMappable) o1).getIdentifier() == null;
			} else {
				return fIdentifier.equals(((IMappable) o1).getIdentifier());
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return fIdentifier.hashCode();
	}
}
