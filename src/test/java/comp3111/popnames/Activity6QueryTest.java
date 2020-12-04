//package comp3111.popnames;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;
//import java.lang.Float;
//import java.util.*;  
//
//import org.junit.Test;
//
//public class Activity6QueryTest {
//	
//	@Test
//	public void testExecuteQuery() {
//		float score = Activity6Query.executeQuery("Daniel", 0, 1999, "Taylor", 1, true);
//		assertTrue(Float.compare(score, (8 / (float) 9)) == 0);
//
//		score = Activity6Query.executeQuery("Taylor", 1, 2000, "Daniel", 0, false);
//		assertTrue(Float.compare(score, 0.9f) == 0);
//
//		score = Activity6Query.executeQuery("Daniellll", 0, 1999, "Taylorrrrr", 1, true);
//		assertTrue(Float.compare(score, 1.0f) == 0);
//	}
//
//	@Test
//	public void testExecuteQueryInvalid() {
//		float score;
//		try{
//			score = Activity6Query.executeQuery("", 0, 1999, "Taylor", 1, true);
//		} catch (Exception e) {}
//		try{
//			score = Activity6Query.executeQuery("D", 0, 1999, "Taylor", 1, true);
//		} catch (Exception e) {}
//		try{
//			score = Activity6Query.executeQuery("Christiandaniels", 0, 1999, "Taylor", 1, true);
//		} catch (Exception e) {}
//		try{
//			score = Activity6Query.executeQuery("Daniel*", 0, 1999, "Taylor", 1, true);
//		} catch (Exception e) {}
//
//		try{
//			score = Activity6Query.executeQuery("Daniel", 0, 1999, "", 1, true);
//		} catch (Exception e) {}
//		try{
//			score = Activity6Query.executeQuery("Daniel", 0, 1999, "T", 1, true);
//		} catch (Exception e) {}
//		try{
//			score = Activity6Query.executeQuery("Daniel", 0, 1999, "Christiandaniels", 1, true);
//		} catch (Exception e) {}
//		try{
//			score = Activity6Query.executeQuery("Daniel", 0, 1999, "Taylor*", 1, true);
//		} catch (Exception e) {}
//
//		try{
//			score = Activity6Query.executeQuery("Daniel", 0, 1879, "Taylor", 1, true);
//		} catch (Exception e) {}
//		try{
//			score = Activity6Query.executeQuery("Daniel", 0, 2020, "Taylor", 1, true);
//		} catch (Exception e) {}
//	}
//}
