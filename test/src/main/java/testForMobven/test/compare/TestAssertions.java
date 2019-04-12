package testForMobven.test.compare;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestAssertions {
	
	

	@Test
	public void testAssert(Double firstPrice, Double secondPrice) {
		
		try {
			
			
			assertTrue(firstPrice < secondPrice);
            
		} catch (Exception e) {
			
			System.out.println("error : " + e.getMessage());
		}
	}
}
