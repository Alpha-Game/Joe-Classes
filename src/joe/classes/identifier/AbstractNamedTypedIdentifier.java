package joe.classes.identifier;

public abstract class AbstractNamedTypedIdentifier<V1> extends AbstractTypedIdentifier<V1> implements INamable {
	private final String fNameIdentifier;
	
	public AbstractNamedTypedIdentifier(String identifier, String nameIdentifier, Class<V1> type) {
		super(identifier, type);
		fNameIdentifier = nameIdentifier;
	}

	@Override
	public String getDisplayNameIdentifier() {
		return fNameIdentifier;
	}
}
