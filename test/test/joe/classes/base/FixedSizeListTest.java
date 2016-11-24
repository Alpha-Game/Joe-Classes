package test.joe.classes.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Collection;

import joe.classes.base.FixedSizedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class FixedSizeListTest {
	@Parameters(name= "Size: {0}")
    public static Collection<Object[]> data() {
    	return Arrays.asList(new Object[][] {
    			{ 0 },
    			{ 1 },
    			{ 2 },
    			{ 3 },
    			{ 5 },
    	});
    }
    
    @Parameter(0)
    public int fSize;
    
    @Test
    public void test_FixedSizedList_Constructor_Basic() {
    	if (fSize < 1) {
    		try {
    			new FixedSizedList<Integer>(fSize);
    			fail();
    		} catch (IllegalArgumentException e) {
    			//Success
    		}
    	} else {
    		assertEquals(fSize, new FixedSizedList<Integer>(fSize).size());
    	}
    }
    
    @Test
    public void test_FixedSizedList_Constructor_Prefill_Array_Small() {
    	assumeTrue(fSize > 0);
    	
    	Integer[] array = new Integer[fSize - 1];
		for (int i = 0; i < fSize - 1; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, array);
		assertEquals(fSize, list.size());
		
		for (int i = 0; i < fSize - 1; i++) {
			assertEquals((Integer)(fSize - (i + 1)), list.get(i));
		}
		assertNull(list.get(fSize - 1));
    }
    
    @Test
    public void test_FixedSizedList_Constructor_Prefill_Array_Full() {
    	assumeTrue(fSize > 0);
    	
    	Integer[] array = new Integer[fSize];
		for (int i = 0; i < fSize; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, array);
		assertEquals(fSize, list.size());
		
		for (int i = 0; i < fSize; i++) {
			assertEquals((Integer)(fSize - i), list.get(i));
		}
    }
    
    @Test
    public void test_FixedSizedList_Constructor_Prefill_Array_Extra() {
    	if (fSize < 1) {
    		try {
    			new FixedSizedList<Integer>(fSize, 2, 3, 4);
    			fail();
    		} catch (IllegalArgumentException e) {
    			//Success
    		}
    	} else {
    		Integer[] array = new Integer[fSize + 1];
    		for (int i = 0; i < fSize + 1; i++) {
    			array[i] = i + 1;
    		}
    		
    		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, array);
    		assertEquals(fSize, list.size());
    		
    		for (int i = 0; i < fSize; i++) {
    			assertEquals((Integer)(fSize - (i - 1)), list.get(i));
    		}
    	}
    }
    
    @Test
    public void test_FixedSizedList_Constructor_Prefill_Collection_Small() {
    	assumeTrue(fSize > 0);
    	
    	Integer[] array = new Integer[fSize - 1];
		for (int i = 0; i < fSize - 1; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, Arrays.asList(array));
		assertEquals(fSize, list.size());
		
		for (int i = 0; i < fSize - 1; i++) {
			assertEquals((Integer)(fSize - (i + 1)), list.get(i));
		}
		assertNull(list.get(fSize - 1));
    }
    
    @Test
    public void test_FixedSizedList_Constructor_Prefill_Collection_Full() {
    	assumeTrue(fSize > 0);
    	
    	Integer[] array = new Integer[fSize];
		for (int i = 0; i < fSize; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, Arrays.asList(array));
		assertEquals(fSize, list.size());
		
		for (int i = 0; i < fSize; i++) {
			assertEquals((Integer)(fSize - i), list.get(i));
		}
    }
    
    @Test
    public void test_FixedSizedList_Constructor_Prefill_Collection_Extra() {
    	if (fSize < 1) {
    		try {
    			new FixedSizedList<Integer>(fSize, Arrays.asList(2, 3, 4));
    			fail();
    		} catch (IllegalArgumentException e) {
    			//Success
    		}
    	} else {
    		Integer[] array = new Integer[fSize + 1];
    		for (int i = 0; i < fSize + 1; i++) {
    			array[i] = i + 1;
    		}
    		
    		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, Arrays.asList(array));
    		assertEquals(fSize, list.size());
    		
    		for (int i = 0; i < fSize; i++) {
    			assertEquals((Integer)(fSize - (i - 1)), list.get(i));
    		}
    	}
    }
    
    @Test
    public void test_FixedSizedList_Set_NoneAdded() {
    	assumeTrue(fSize > 1);
    	FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize);
    	assertNull(list.set(1, -1));
    	
    	for (int i = 0; i < fSize; i++) {
    		if (i != 1) {
    			assertNull(list.get(i));
    		} else {
    			assertEquals((Integer)(-1), list.get(i));
    		}
    	}
    }
    
    @Test
    public void test_FixedSizedList_Set_SomeAdded() {
    	assumeTrue(fSize > 2);
    	Integer[] array = new Integer[fSize - 1];
		for (int i = 0; i < fSize - 1; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, Arrays.asList(array));
    	assertEquals((Integer)(fSize - 2), list.set(1, -1));
    	
    	for (int i = 0; i < fSize; i++) {
    		if (i == fSize - 1) {
    			assertNull(list.get(i));
    		} else if (i != 1) {
    			assertEquals((Integer)(fSize - (i + 1)), list.get(i));
    		} else {
    			assertEquals((Integer)(-1), list.get(i));
    		}
    	}
    }
    
    @Test
    public void test_FixedSizedList_Set_AllAdded() {
    	assumeTrue(fSize > 1);
    	Integer[] array = new Integer[fSize];
		for (int i = 0; i < fSize; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, Arrays.asList(array));
		assertEquals((Integer)(fSize - 1), list.set(1, -1));
    	
    	for (int i = 0; i < fSize; i++) {
    		if (i != 1) {
    			assertEquals((Integer)(fSize - i), list.get(i));
    		} else {
    			assertEquals((Integer)(-1), list.get(i));
    		}
    	}
    }
    
    @Test
    public void test_FixedSizedList_Set_ExtraAdded() {
    	assumeTrue(fSize > 1);
    	Integer[] array = new Integer[fSize + 1];
		for (int i = 0; i < fSize + 1; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, Arrays.asList(array));
		assertEquals((Integer)(fSize), list.set(1, -1));
    	
    	for (int i = 0; i < fSize; i++) {
    		if (i != 1) {
    			assertEquals((Integer)(fSize - (i - 1)), list.get(i));
    		} else {
    			assertEquals((Integer)(-1), list.get(i));
    		}
    	}
    }
    
    @Test
    public void test_FixedSizedList_Set_OutOfBounds_Positive() {
    	assumeTrue(fSize > 0);
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize);
		try {
			list.set(fSize, -1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Success
		}
    }
    
    @Test
    public void test_FixedSizedList_Set_OutOfBounds_Negative() {
    	assumeTrue(fSize > 0);
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize);
		try {
			list.set(fSize, -1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Success
		}
    }
    
    @Test
    public void test_FixedSizedList_Get_OutOfBounds_Positive() {
    	assumeTrue(fSize > 0);
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize);
		try {
			list.get(fSize);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Success
		}
    }
    
    @Test
    public void test_FixedSizedList_Get_OutOfBounds_Negative() {
    	assumeTrue(fSize > 0);
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize);
		try {
			list.get(fSize);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Success
		}
    }
    
    @Test
    public void test_FixedSizedList_Clear() {
    	assumeTrue(fSize > 0);
    	Integer[] array = new Integer[fSize + 1];
		for (int i = 0; i < fSize + 1; i++) {
			array[i] = i + 1;
		}
		
		FixedSizedList<Integer> list = new FixedSizedList<Integer>(fSize, Arrays.asList(array));
		list.clear();
		for (int i = 0; i < fSize; i++) {
			assertNull(list.get(i));
		}
    }
}
