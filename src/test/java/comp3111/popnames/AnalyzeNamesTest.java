package comp3111.popnames;

import org.junit.Test;
import static org.junit.Assert.*;

public class AnalyzeNamesTest {
	
    @Test 
    public void testGetRankNotFound() {
    	AnalyzeNames a = new AnalyzeNames();
    	int i = a.getRank(2019, "XXX", "M");
		assertEquals(i, -1);
    }
    
    @Test 
    public void testGetRankMale() {
    	AnalyzeNames a = new AnalyzeNames();
    	int i = a.getRank(2019, "David", "M");
    	assertTrue(i==27);
    }
    
    @Test 
    public void testGetRankFemale() {
    	AnalyzeNames a = new AnalyzeNames();
    	int i = a.getRank(2019, "Desire", "F");
    	assertTrue(i==2192);
    }
    	
    @Test 
    public void testGetNameMale() {
    	AnalyzeNames a = new AnalyzeNames();
    	String name = a.getName(2019, 27, "M");
    	assertTrue(name.equals("David"));
    }
    
    @Test 
    public void testGetNameFemale() {
    	AnalyzeNames a = new AnalyzeNames();
    	String name = a.getName(2019, 2192, "F");
    	assertTrue(name.equals("Desire"));
    }

    @Test
    public void testNameLength() {
        assertFalse(AnalyzeNames.checkNameLength(""));
        assertFalse(AnalyzeNames.checkNameLength("A"));
        assertTrue(AnalyzeNames.checkNameLength("Hi"));
        assertTrue(AnalyzeNames.checkNameLength("Christiandaniel"));
        assertFalse(AnalyzeNames.checkNameLength("Christiandaniels"));
    }

    @Test
    public void testNameCharacter() {
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString((char)('a' - 1))));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString('a')));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString((char)('a' + 1))));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString((char)('z' - 1))));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString('z')));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString((char)('z' + 1))));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString((char)('A' - 1))));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString('A')));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString((char)('A' + 1))));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString((char)('Z' - 1))));
        assertTrue(AnalyzeNames.checkNameCharacter(Character.toString('Z')));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString((char)('Z' + 1))));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString(' ')));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString('0')));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString(',')));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString('-')));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString('.')));
        assertFalse(AnalyzeNames.checkNameCharacter(Character.toString('_')));
    }

    @Test
    public void testYearBoundary() {
        assertFalse(AnalyzeNames.checkYear(1879));
        assertTrue(AnalyzeNames.checkYear(1880));
        assertTrue(AnalyzeNames.checkYear(2019));
        assertFalse(AnalyzeNames.checkYear(2020));
    }

    @Test
    public void testFindRankValid() {
        RankRecord record = AnalyzeNames.getRankRecord(2019, "Olivia", "F");
        assertTrue(record.getYear() == 2019);
        assertTrue(record.getRank() == 1);
        assertTrue(record.getCount() == 18451);
        assertTrue(Float.compare(record.getPercentage(), (18451 / (float) 1665373)) == 0);
        
        record = AnalyzeNames.getRankRecord(2019, "Zyrielle", "F");
        assertTrue(record.getYear() == 2019);
        assertTrue(record.getRank() == 17905);
        assertTrue(record.getCount() == 5);
        assertTrue(Float.compare(record.getPercentage(), (5 / (float) 1665373)) == 0);

        record = AnalyzeNames.getRankRecord(2019, "Liam", "M");
        assertTrue(record.getYear() == 2019);
        assertTrue(record.getRank() == 1);
        assertTrue(record.getCount() == 20502);
        assertTrue(Float.compare(record.getPercentage(), (20502 / (float) 1779948)) == 0);

        record = AnalyzeNames.getRankRecord(2019, "Zyran", "M");
        assertTrue(record.getYear() == 2019);
        assertTrue(record.getRank() == 14049);
        assertTrue(record.getCount() == 5);
        assertTrue(Float.compare(record.getPercentage(), (5 / (float) 1779948)) == 0);
    }

    @Test
    public void testFindRankInvalid() {
        RankRecord record = AnalyzeNames.getRankRecord(2019, "Testing", "F");
        assertTrue(record.getYear() == 2019);
        assertTrue(record.getRank() == -1);
        assertTrue(record.getCount() == -1);
        assertTrue(Float.compare(record.getPercentage(), -1) == 0);

        record = AnalyzeNames.getRankRecord(2019, "Testing", "M");
        assertTrue(record.getYear() == 2019);
        assertTrue(record.getRank() == -1);
        assertTrue(record.getCount() == -1);
        assertTrue(Float.compare(record.getPercentage(), -1) == 0);
    }

}
