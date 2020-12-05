package comp3111.popnames;

import static org.junit.Assert.*;
import static junit.framework.TestCase.fail;
import org.junit.Test;
import java.util.ArrayList;

/**
 * This class also tests valid query executions. 
 * 
 * @author Amrutavarsh
 *
 */
public class Activity3QueryTest {
	
	/*
	 * Test standard correct query.
	 */
	@Test
	public void testExecuteQuery() {
		Task3 task3 = new Task3();
		ArrayList<ArrayList<String>> Records = task3.execute3Query(1941, 1950, "M", 7);
		assertTrue(Records.size() == 6);
		
		assertTrue(Records.get(0).get(0).equals("James"));
		assertTrue(Records.get(0).get(1).equals("1950"));
		assertTrue(Records.get(0).get(2).equals("1"));
		assertTrue(Records.get(0).get(3).equals("1950"));
		assertTrue(Records.get(0).get(4).equals("1"));
		assertTrue(Records.get(0).get(5).equals("FLAT"));
		
		assertTrue(Records.get(1).get(0).equals("Robert"));
		assertTrue(Records.get(1).get(1).equals("1950"));
		assertTrue(Records.get(1).get(2).equals("2"));
		assertTrue(Records.get(1).get(3).equals("1950"));
		assertTrue(Records.get(1).get(4).equals("2"));
		assertTrue(Records.get(1).get(5).equals("FLAT"));
		
		assertTrue(Records.get(2).get(0).equals("John"));
		assertTrue(Records.get(2).get(1).equals("1950"));
		assertTrue(Records.get(2).get(2).equals("3"));
		assertTrue(Records.get(2).get(3).equals("1950"));
		assertTrue(Records.get(2).get(4).equals("3"));
		assertTrue(Records.get(2).get(5).equals("FLAT"));
		
		assertTrue(Records.get(3).get(0).equals("William"));
		assertTrue(Records.get(3).get(1).equals("1950"));
		assertTrue(Records.get(3).get(2).equals("6"));
		assertTrue(Records.get(3).get(3).equals("1949"));
		assertTrue(Records.get(3).get(4).equals("4"));
		assertTrue(Records.get(3).get(5).equals("DOWN"));
		
		assertTrue(Records.get(4).get(0).equals("Richard"));
		assertTrue(Records.get(4).get(1).equals("1950"));
		assertTrue(Records.get(4).get(2).equals("7"));
		assertTrue(Records.get(4).get(3).equals("1947"));
		assertTrue(Records.get(4).get(4).equals("5"));
		assertTrue(Records.get(4).get(5).equals("DOWN"));
		
		assertTrue(Records.get(5).get(0).equals("David"));
		assertTrue(Records.get(5).get(1).equals("1941"));
		assertTrue(Records.get(5).get(2).equals("7"));
		assertTrue(Records.get(5).get(3).equals("1950"));
		assertTrue(Records.get(5).get(4).equals("5"));
		assertTrue(Records.get(5).get(5).equals("UP"));
		
		Records = task3.execute3Query(1929, 1945, "F", 14);
		assertTrue(Records.size() == 4);
		
		assertTrue(Records.get(0).get(0).equals("Mary"));
		assertTrue(Records.get(0).get(1).equals("1945"));
		assertTrue(Records.get(0).get(2).equals("1"));
		assertTrue(Records.get(0).get(3).equals("1945"));
		assertTrue(Records.get(0).get(4).equals("1"));
		assertTrue(Records.get(0).get(5).equals("FLAT"));
		
		assertTrue(Records.get(1).get(0).equals("Betty"));
		assertTrue(Records.get(1).get(1).equals("1945"));
		assertTrue(Records.get(1).get(2).equals("11"));
		assertTrue(Records.get(1).get(3).equals("1934"));
		assertTrue(Records.get(1).get(4).equals("2"));
		assertTrue(Records.get(1).get(5).equals("DOWN"));
		
		assertTrue(Records.get(2).get(0).equals("Barbara"));
		assertTrue(Records.get(2).get(1).equals("1929"));
		assertTrue(Records.get(2).get(2).equals("7"));
		assertTrue(Records.get(2).get(3).equals("1944"));
		assertTrue(Records.get(2).get(4).equals("2"));
		assertTrue(Records.get(2).get(5).equals("UP"));
		
		assertTrue(Records.get(3).get(0).equals("Patricia"));
		assertTrue(Records.get(3).get(1).equals("1929"));
		assertTrue(Records.get(3).get(2).equals("10"));
		assertTrue(Records.get(3).get(3).equals("1943"));
		assertTrue(Records.get(3).get(4).equals("3"));
		assertTrue(Records.get(3).get(5).equals("UP"));
		
	}
	
	/*
	 * Test Exceptions thrown for invalid inputs
	 */
	@Test
	public void testExecuteQueryInvalid() {
		Task3 task3 = new Task3();
		ArrayList<ArrayList<String>> Records;
		try{
			Records = task3.execute3Query(1879, 1882, "M", 8);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("start"));            
		}
		try{
			Records = task3.execute3Query(2015, 2020, "F", 6);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("end"));            
		}
		try{
			Records = task3.execute3Query(1879, 2020, "M", 5);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("start end"));            
		}
		try{
			Records = task3.execute3Query(2017, 2010, "F", 5);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("incorrect period order"));            
		}
		try{
			Records = task3.execute3Query(2010, 2015, "M", 0);
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("incorrect TopN"));            
		}
	}
}
