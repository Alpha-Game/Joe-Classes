package joe.classes.identifier;

public abstract class AbstractTypedIdentifier<V1> extends AbstractIdentifier implements ITypable<V1> {
	private final Class<V1> fType;
	
	public AbstractTypedIdentifier(String identifier, Class<V1> type) {
		super(identifier);
		fType = type;
	}
	
	@Override
	public Class<V1> getType() {
		return fType;
	}
}
