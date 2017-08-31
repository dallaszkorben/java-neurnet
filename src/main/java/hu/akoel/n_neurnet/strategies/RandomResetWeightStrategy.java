package hu.akoel.n_neurnet.strategies;

import java.util.Random;

public class RandomResetWeightStrategy implements IResetWeightStrategy{
	Random rnd = new Random();

	public double getWeight(int outputNeuronOrder, int inputNeuronOrder) {
		return rnd.nextDouble();
	}

}
