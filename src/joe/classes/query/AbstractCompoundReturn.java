package joe.classes.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCompoundReturn<V1, V2> {
	public class ParameterReturn<V3, V4> {
		private V3 parameterValue;
		private V4 returnValue;
		
		public ParameterReturn(V3 parameterValue, V4 returnValue) {
			this.parameterValue = parameterValue;
			this.returnValue = returnValue;
		}
		
		public V3 getParameterValue() {
			return parameterValue;
		}
		
		public V4 getReturnValue() {
			return returnValue;
		}
	}
	
	protected List<ParameterReturn<V1, V2>> fList;
	protected V2 fTotalValue;
	
	public AbstractCompoundReturn() {
		fList = new ArrayList<ParameterReturn<V1, V2>>();
	}
	
	public List<ParameterReturn<V1, V2>> getList() {
		return Collections.unmodifiableList(fList);
	}
	
	public int getSize() {
		return fList.size();
	}
	
	public ParameterReturn<V1, V2> getResult(int i) {
		return fList.get(i);
	}
	
	public V1 getParameterValue(int i) {
		return getResult(i).getParameterValue();
	}
	
	public V2 getReturnValue(int i) {
		return getResult(i).getReturnValue();
	}
	
	public V2 getTotalValue() {
		return fTotalValue;
	}
	
	public void lock() {
		fList = Collections.unmodifiableList(fList);
	}
	
	protected abstract void processTotalValue(V2 returnValue);
	
	public void addResult(ParameterReturn<V1, V2> result) {
		processTotalValue(result.getReturnValue());
		fList.add(result);
	}
	
	public void addResult(V1 parameter, V2 returnValue) {
		addResult(new ParameterReturn<V1, V2>(parameter, returnValue));
	}
}
