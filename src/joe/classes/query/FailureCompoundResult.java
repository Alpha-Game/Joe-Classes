package joe.classes.query;

public class FailureCompoundResult<V> extends AbstractCompoundReturn<V, Boolean>{

	public FailureCompoundResult() {
		super();
		fTotalValue = true;
	}
	
	@Override
	protected void processTotalValue(Boolean returnValue) {
		if (returnValue != null) {
			fTotalValue = fTotalValue && returnValue;
		}
	}
}
