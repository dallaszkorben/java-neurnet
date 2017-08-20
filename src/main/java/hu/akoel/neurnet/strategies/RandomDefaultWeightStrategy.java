package hu.akoel.neurnet.strategies;

import java.util.Random;

public class RandomDefaultWeightStrategy implements DefaultWeightStrategy{
	Random rnd = new Random();
	
	public Double getValue() {
		return rnd.nextDouble();
	}

}
