package comp3111.popnames;

import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import org.junit.Test;
import java.util.ArrayList;

public class Activity4QueryTest {
	@Test
	public void testExecuteQuery() {
		Task4 task4= new Task4();
		ArrayList<ArrayList<String>> Records = task4.recommendation("James", 1934, "Mary", 1940, 1999, "NK-T4");
		assertTrue(Records.size() == 2);
		assertTrue(Records.get(0).get(0).equals("Michael"));
		assertTrue(Records.get(1).get(0).equals("Emily"));
		
		Records = task4.recommendation("James", 1934, "Mary", 1940, 1999, "Jaro");
		assertTrue(Records.size() == 2);
		assertTrue(Records.get(0).get(0).equals("Jaymes : 0.577"));
		assertTrue(Records.get(0).get(1).equals("Jayme : 0.549"));
		assertTrue(Records.get(0).get(2).equals("Jamey : 0.549"));
		assertTrue(Records.get(1).get(0).equals("Mayme : 0.574"));
		assertTrue(Records.get(1).get(1).equals("Jarelys : 0.574"));
		assertTrue(Records.get(1).get(2).equals("Marye : 0.56"));
	}
	
	@Test
	public void testExecuteQueryInvalid() {
		Task4 task4= new Task4();
		ArrayList<ArrayList<String>> Records;
		try {
			Records = task4.recommendation("James", 2020, "Mary", 1940, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("invalid dYOB"));            
		}
		try {
			Records = task4.recommendation("James", 1879, "Mary", 1940, 1999, "Jaro");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("invalid dYOB"));            
		}
		
		try {
			Records = task4.recommendation("James", 1943, "Mary", 2020, 1999, "Jaro");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("invalid mYOB"));            
		}
		try {
			Records = task4.recommendation("James", 1943, "Mary", 1879, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("invalid mYOB"));            
		}
		
		try {
			Records = task4.recommendation("James", 1949, "Mary", 1940, 2020, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("invalid vYear"));            
		}
		try {
			Records = task4.recommendation("James", 1956, "Mary", 1940, 1879, "Jaro");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("invalid vYear"));            
		}
		
		try {
			Records = task4.recommendation("James*", 1934, "Mary", 1940, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("dName char"));            
		}
		try {
			Records = task4.recommendation("James", 1934, "Mary#", 1940, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("mName char"));            
		}
		
		try {
			Records = task4.recommendation("J", 1934, "Mary", 1940, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("dName length"));            
		}
		try {
			Records = task4.recommendation("James", 1934, "M", 1940, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("mName length"));            
		}
		
		try {
			Records = task4.recommendation("Jjdiwksnchdusjeu", 1934, "Mary", 1940, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("dName length"));            
		}
		try {
			Records = task4.recommendation("James", 1934, "Jjdiwksnchdusjeu", 1940, 1999, "NK-T4");
			fail();
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("mName length"));            
		}
	}
}
