package de.sprinteins.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PerformanceTest {

	
	@Test
	public void test_calculate_10_comedy() {
		Performance p = new Performance(10, new Play(PlayType.comedy, "Life of Brian"));
		
		Assertions.assertEquals(330, p.getAmount());
		Assertions.assertEquals(0, p.getCredits());
	}
	
	@Test
	public void test_calculate_25_comedy() {
		Performance p = new Performance(25, new Play(PlayType.comedy, "Life of Brian"));
		
		Assertions.assertEquals(500, p.getAmount());
		Assertions.assertEquals(0, p.getCredits());
	}
	
	@Test
	public void test_calculate_40_comedy() {
		Performance p = new Performance(40, new Play(PlayType.comedy, "Life of Brian"));
		
		Assertions.assertEquals(620, p.getAmount());
		Assertions.assertEquals(10, p.getCredits());
	}
	
	@Test
	public void test_calculate_10_tragedy() {
		Performance p = new Performance(10, new Play(PlayType.tragedy, "Dantes Hell"));
		
		Assertions.assertEquals(400, p.getAmount());
		Assertions.assertEquals(0, p.getCredits());
	}
	
	@Test
	public void test_calculate_25_tragedy() {
		Performance p = new Performance(25, new Play(PlayType.tragedy, "Dantes Hell"));
		
		Assertions.assertEquals(400, p.getAmount());
		Assertions.assertEquals(0, p.getCredits());
	}
	
	@Test
	public void test_calculate_40_tragedy() {
		Performance p = new Performance(40, new Play(PlayType.tragedy, "Dantes Hell"));
		
		Assertions.assertEquals(500, p.getAmount());
		Assertions.assertEquals(10, p.getCredits());
	}
}
