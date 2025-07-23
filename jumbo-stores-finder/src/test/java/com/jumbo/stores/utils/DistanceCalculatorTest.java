package com.jumbo.stores.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceCalculatorTest {

	@Test
	public void calculate_shouldReturnCorrectDistance() {
		// Distance between Amsterdam and Utrecht is approximately 35 km
		double distance = DistanceCalculator.calculate(52.3676, 4.9041, 52.0907, 5.1214);
		assertEquals(35.0, distance, 1.0);
	}
}