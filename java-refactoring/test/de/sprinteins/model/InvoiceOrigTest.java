package de.sprinteins.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceOrigTest extends Invoice {

	@Test
	public void test_addandcalculate() {
		
		addAndCalculate( new Performance(55, new Play(PlayType.tragedy, "Hamlet")));
		addAndCalculate(new Performance(35, new Play(PlayType.comedy, "As You Like It")));
		addAndCalculate(new Performance(40, new Play(PlayType.tragedy, "Othello")));
		
		Assertions.assertEquals(1730, getTotalAmount());
		Assertions.assertEquals(40, getVolumeCredits());
	}
}
