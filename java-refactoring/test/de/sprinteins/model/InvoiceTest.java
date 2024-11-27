package de.sprinteins.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceTest extends Invoice {

	@Test
	public void test_addandcalculate() {

		addAndCalculate(new Performance(40, new Play(PlayType.COMEDY, "Life of Brian")));
		addAndCalculate(new Performance(55, new Play(PlayType.TRAGEDY, "Dantes Hell")));
		
		Assertions.assertEquals(1270, getTotalAmount());
		Assertions.assertEquals(35, getVolumeCredits());
	}
}
