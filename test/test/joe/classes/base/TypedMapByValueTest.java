package test.joe.classes.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import joe.classes.base.TypedMapByValue;
import joe.classes.identifier.AbstractIdentifier;
import joe.classes.identifier.IMappable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TypedMapByValueTest.put.class
	// TODO: Tests for putAll, remove, and clear
})
public class TypedMapByValueTest {
	public static interface I1 extends IMappable {}
	public static interface I1_1 extends I1 {}
	public static interface I1_2 extends I1 {}
	public static interface I2 {}
	public static class C1 extends AbstractIdentifier implements I1 {
		public C1(String identifier) { super(identifier); }
	}	
	public static class C1_1 extends C1 {
		public C1_1(String identifier) { super(identifier); }
	}	
	public static class C1_1_1 extends C1_1 implements I1_1 {
		public C1_1_1(String identifier) { super(identifier); }
	}	
	public static class C1_2 extends C1 {
		public C1_2(String identifier) { super(identifier); }
	}	
	public static class C2 extends AbstractIdentifier implements I2, I1_1, I1_2 {
		public C2(String identifier) { super(identifier); }
	}
	
	@RunWith(Parameterized.class)
	public static class put {
		public static class InputParameterCollection {
			private final Object fKey;
			private final I1 fNewValue;
			private final I1 fOldValue;
			private InputParameterCollection(I1 value) { fKey = value.getIdentifier(); fNewValue = value; fOldValue = null; }
			private InputParameterCollection(I1 value, I1 oldValue) { fKey = value.getIdentifier(); fNewValue = value; fOldValue = oldValue; if (fNewValue.getIdentifier() != fOldValue.getIdentifier()) throw new IllegalArgumentException(); }
		}
		
		@Parameter(0)
		public String fTestName;
		
		@Parameter(1)
		public Iterable<InputParameterCollection> fParameters;
		
		@Parameter(2)
		public Map<?, Object> fObjectMap;
		
		@Parameter(3)
		public Map<?, I1> fI1Map;
		
		@Parameter(4)
		public Map<?, I1_1> fI1_1Map;
		
		@Parameter(5)
		public Map<?, I1_2> fI1_2Map;
		
		@Parameter(6)
		public Map<?, I2> fI2Map;
		
		@Parameter(7)
		public Map<?, C1> fC1Map;
		
		@Parameter(8)
		public Map<?, C1_1> fC1_1Map;
		
		@Parameter(9)
		public Map<?, C1_1_1> fC1_1_1Map;
		
		@Parameter(10)
		public Map<?, C1_2> fC1_2Map;
		
		@Parameter(11)
		public Map<?, C2> fC2Map;
		
		@Parameters(name= "{0}")
	    public static Collection<Object[]> data() {
	    	return Arrays.asList(new Object[][] {
	    			{ 	
	    				"No Input",
	    				Arrays.asList(new InputParameterCollection[] {}),
	    				TypedMapByValueTest.<Object>createMap(),
	    				TypedMapByValueTest.<I1>createMap(),
	    				TypedMapByValueTest.<I1_1>createMap(),
	    				TypedMapByValueTest.<I1_2>createMap(),
	    				TypedMapByValueTest.<I2>createMap(),
	    				TypedMapByValueTest.<C1>createMap(),
	    				TypedMapByValueTest.<C1_1>createMap(),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(),
	    			},
	    			{ 	
	    				"Single C1 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C1("C1-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C1("C1-1")),
	    				TypedMapByValueTest.<I1>createMap(new C1("C1-1")),
	    				TypedMapByValueTest.<I1_1>createMap(),
	    				TypedMapByValueTest.<I1_2>createMap(),
	    				TypedMapByValueTest.<I2>createMap(),
	    				TypedMapByValueTest.<C1>createMap(new C1("C1-1")),
	    				TypedMapByValueTest.<C1_1>createMap(),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(),
	    			},
	    			{ 	
	    				"Single C1_1 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C1_1("C1_1-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C1_1("C1_1-1")),
	    				TypedMapByValueTest.<I1>createMap(new C1_1("C1_1-1")),
	    				TypedMapByValueTest.<I1_1>createMap(),
	    				TypedMapByValueTest.<I1_2>createMap(),
	    				TypedMapByValueTest.<I2>createMap(),
	    				TypedMapByValueTest.<C1>createMap(new C1_1("C1_1-1")),
	    				TypedMapByValueTest.<C1_1>createMap(new C1_1("C1_1-1")),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(),
	    			},
	    			{ 	
	    				"Single C1_2 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C1_2("C1_2-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C1_2("C1_2-1")),
	    				TypedMapByValueTest.<I1>createMap(new C1_2("C1_2-1")),
	    				TypedMapByValueTest.<I1_1>createMap(),
	    				TypedMapByValueTest.<I1_2>createMap(),
	    				TypedMapByValueTest.<I2>createMap(),
	    				TypedMapByValueTest.<C1>createMap(new C1_2("C1_2-1")),
	    				TypedMapByValueTest.<C1_1>createMap(),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(new C1_2("C1_2-1")),
	    				TypedMapByValueTest.<C2>createMap(),
	    			},
	    			{ 	
	    				"Single C1_1_1 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C1_1_1("C1_1_1-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<I1>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<I1_1>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<I1_2>createMap(),
	    				TypedMapByValueTest.<I2>createMap(),
	    				TypedMapByValueTest.<C1>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<C1_1>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<C1_1_1>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(),
	    			},
	    			{ 	
	    				"Single C2 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C2("C2-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I1>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I1_1>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I1_2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<C1>createMap(),
	    				TypedMapByValueTest.<C1_1>createMap(),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(new C2("C2-1")),
	    			},
	    			{ 	
	    				"MultiInput C2 C2 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C2("C2-1")), new InputParameterCollection(new C2("C2-2")) }),
	    				TypedMapByValueTest.<Object>createMap(new C2("C2-1"), new C2("C2-2")),
	    				TypedMapByValueTest.<I1>createMap(new C2("C2-1"), new C2("C2-2")),
	    				TypedMapByValueTest.<I1_1>createMap(new C2("C2-1"), new C2("C2-2")),
	    				TypedMapByValueTest.<I1_2>createMap(new C2("C2-1"), new C2("C2-2")),
	    				TypedMapByValueTest.<I2>createMap(new C2("C2-1"), new C2("C2-2")),
	    				TypedMapByValueTest.<C1>createMap(),
	    				TypedMapByValueTest.<C1_1>createMap(),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(new C2("C2-1"), new C2("C2-2")),
	    			},
	    			{ 	
	    				"MultiInput C1_2 C2 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C1_2("C1_2-1")), new InputParameterCollection(new C2("C2-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C1_2("C1_2-1"), new C2("C2-1")),
	    				TypedMapByValueTest.<I1>createMap(new C1_2("C1_2-1"), new C2("C2-1")),
	    				TypedMapByValueTest.<I1_1>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I1_2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<C1>createMap(new C1_2("C1_2-1")),
	    				TypedMapByValueTest.<C1_1>createMap(),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(new C1_2("C1_2-1")),
	    				TypedMapByValueTest.<C2>createMap(new C2("C2-1")),
	    			},
	    			{ 	
	    				"MultiInput C1_2 C2 C1_1_1 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C1_2("C1_2-1")), new InputParameterCollection(new C2("C2-1")), new InputParameterCollection(new C1_1_1("C1_1_1-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C1_2("C1_2-1"), new C2("C2-1"), new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<I1>createMap(new C1_2("C1_2-1"), new C2("C2-1"), new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<I1_1>createMap(new C2("C2-1"), new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<I1_2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<C1>createMap(new C1_2("C1_2-1"), new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<C1_1>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<C1_1_1>createMap(new C1_1_1("C1_1_1-1")),
	    				TypedMapByValueTest.<C1_2>createMap(new C1_2("C1_2-1")),
	    				TypedMapByValueTest.<C2>createMap(new C2("C2-1")),
	    			},
	    			{ 	
	    				"Override C2 -> C2 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C2("C2-1")), new InputParameterCollection(new C2("C2-1"), new C2("C2-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I1>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I1_1>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I1_2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<I2>createMap(new C2("C2-1")),
	    				TypedMapByValueTest.<C1>createMap(),
	    				TypedMapByValueTest.<C1_1>createMap(),
	    				TypedMapByValueTest.<C1_1_1>createMap(),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(new C2("C2-1")),
	    			},
	    			{ 	
	    				"Override C2 -> C1_1_1 Input",
	    				Arrays.asList(new InputParameterCollection[] { new InputParameterCollection(new C2("C2-1")), new InputParameterCollection(new C1_1_1("C2-1"), new C2("C2-1")) }),
	    				TypedMapByValueTest.<Object>createMap(new C1_1_1("C2-1")),
	    				TypedMapByValueTest.<I1>createMap(new C1_1_1("C2-1")),
	    				TypedMapByValueTest.<I1_1>createMap(new C1_1_1("C2-1")),
	    				TypedMapByValueTest.<I1_2>createMap(),
	    				TypedMapByValueTest.<I2>createMap(),
	    				TypedMapByValueTest.<C1>createMap(new C1_1_1("C2-1")),
	    				TypedMapByValueTest.<C1_1>createMap(new C1_1_1("C2-1")),
	    				TypedMapByValueTest.<C1_1_1>createMap(new C1_1_1("C2-1")),
	    				TypedMapByValueTest.<C1_2>createMap(),
	    				TypedMapByValueTest.<C2>createMap(),
	    			},
	    	});
	    }
		
		@Test
		public void test_ObjectMap() {
			doTest(Object.class);
		}
		
		@Test
		public void test_I1Map() {
			doTest(I1.class);
		}
		
		@Test
		public void test_I1_1Map() {
			doTest(I1_1.class);
		}
		
		@Test
		public void test_I1_2Map() {
			doTest(I1_2.class);
		}
		
		@Test
		public void test_I2Map() {
			doTest(I2.class);
		}
		
		@Test
		public void test_C1Map() {
			doTest(C1.class);
		}
		
		@Test
		public void test_C1_1Map() {
			doTest(C1_1.class);
		}
		
		@Test
		public void test_C1_1_1Map() {
			doTest(C1_1_1.class);
		}
		
		@Test
		public void test_C1_2Map() {
			doTest(C1_2.class);
		}
		
		@Test
		public void test_C2Map() {
			doTest(C2.class);
		}
		
		private <V1> void doTest(Class<V1> className) {
			TypedMapByValue<Object, V1> map = new TypedMapByValue<Object, V1>(className);
			doPut(className, fParameters, map);
			doAssert(className, map);
		}
		
		private <V1> void doPut(Class<V1> className, Iterable<InputParameterCollection> parameters, TypedMapByValue<Object, V1> map) {
			for (InputParameterCollection entry : parameters) {
				if (className.isInstance(entry.fNewValue)) {
					if (className.isInstance(entry.fOldValue)) {
						assertEquals(entry.fOldValue, map.put(entry.fKey, className.cast(entry.fNewValue)));
					} else {
						assertEquals(null, map.put(entry.fKey, className.cast(entry.fNewValue)));
					}
				} else if (className.isInstance(entry.fOldValue)) {
					assumeTrue("Invalid Test Case", false);
				}
			}
		}
		
		private <V1> void doAssert(Class<V1> className, TypedMapByValue<?, V1> map) {
			TypedMapByValueTest.doAssert(className, map, fObjectMap, fI1Map, fI1_1Map, fI1_2Map, fI2Map, fC1Map, fC1_1Map, fC1_1_1Map, fC1_2Map, fC2Map);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <V> Map<Object, V> createMap(IMappable... entries) {
		Map<Object, V> map = new HashMap<Object, V>();
		for (IMappable entry : entries) {
			map.put(entry.getIdentifier(), (V)entry);
		}
		return map;
	}
	
	private static <V1> void doAssert(Class<V1> className, TypedMapByValue<?, V1> map, 
			Map<?, Object> objectMap, Map<?, I1> I1Map, Map<?, I1_1> I1_1Map, Map<?, I1_2> I1_2Map, Map<?, I2> I2Map,
			Map<?, C1> C1Map, Map<?, C1_1> C1_1Map, Map<?, C1_1_1> C1_1_1Map, Map<?, C1_2> C1_2Map, Map<?, C2> C2Map) {
		doAssert(Object.class, className, objectMap, map);
		doAssert(I1.class, className, I1Map, map);
		doAssert(I1_1.class, className, I1_1Map, map);
		doAssert(I1_2.class, className, I1_2Map, map);
		doAssert(I2.class, className, I2Map, map);
		doAssert(C1.class, className, C1Map, map);
		doAssert(C1_1.class, className, C1_1Map, map);
		doAssert(C1_1_1.class, className, C1_1_1Map, map);
		doAssert(C1_2.class, className, C1_2Map, map);
		doAssert(C2.class, className, C2Map, map);
	}
	
	private static <V1, V2> void doAssert(Class<V1> expectedMapClass, Class<V2> actualMapClass, Map<?, V1> expectedMap, TypedMapByValue<?, V2> actualMap) {
		if (actualMapClass.isAssignableFrom(expectedMapClass)) {
			assertMapEquals(expectedMapClass, expectedMap, actualMap);
			if (expectedMapClass.equals(actualMapClass)) {
				assertMapEquals(expectedMap, actualMap);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <V1, V2> void assertMapEquals(Class<V2> classType, Map<?, ?> expected, TypedMapByValue<?, V1> actual) {
		assertEquals(expected.size(), actual.size(classType));
		assertEquals(expected.isEmpty(), actual.isEmpty(classType));
		
		for (Map.Entry<?, ?> entry : expected.entrySet()) {
			assertTrue(actual.containsKey(classType, entry.getKey()));
			assertTrue(actual.containsValue(classType, entry.getValue()));
			
			Object value = actual.get(classType, entry.getKey());
			assertEquals(entry.getValue(), value);
			assertEquals(entry.getValue().getClass(), value.getClass());
		}
		
		for (Object key : actual.keySet((Class<? extends V1>)classType)) {
			assertTrue(expected.containsKey(key));
		}
		
		for (Object value : actual.values((Class<? extends V1>)classType)) {
			assertTrue(expected.containsValue(value));
		}
		
		for (Map.Entry<?, ?> entry : actual.entrySet((Class<? extends V1>)classType)) {
			assertTrue(expected.containsKey(entry.getKey()));
			assertTrue(expected.containsValue(entry.getValue()));
			
			Object value = expected.get(entry.getKey());
			assertEquals(entry.getValue(), value);
			assertEquals(entry.getValue().getClass(), value.getClass());
		}
	}
	
	private static void assertMapEquals(Map<?, ?> expected, Map<?, ?> actual) {
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.isEmpty(), actual.isEmpty());
		
		for (Map.Entry<?, ?> entry : expected.entrySet()) {
			assertTrue(actual.containsKey(entry.getKey()));
			assertTrue(actual.containsValue(entry.getValue()));
			
			Object value = actual.get(entry.getKey());
			assertEquals(entry.getValue(), value);
			assertEquals(entry.getValue().getClass(), value.getClass());
		}
		
		for (Object key : actual.keySet()) {
			assertTrue(expected.containsKey(key));
		}
		
		for (Object value : actual.values()) {
			assertTrue(expected.containsValue(value));
		}
		
		for (Map.Entry<?, ?> entry : actual.entrySet()) {
			assertTrue(expected.containsKey(entry.getKey()));
			assertTrue(expected.containsValue(entry.getValue()));
			
			Object value = expected.get(entry.getKey());
			assertEquals(entry.getValue(), value);
			assertEquals(entry.getValue().getClass(), value.getClass());
		}
	}
}
