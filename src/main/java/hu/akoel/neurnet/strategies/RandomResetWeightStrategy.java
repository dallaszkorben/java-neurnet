package hu.akoel.neurnet.strategies;

import java.util.Random;

import hu.akoel.neurnet.neuron.Neuron;

public class RandomResetWeightStrategy implements IResetWeightStrategy{
	Random rnd = new Random();

	public double getWeight(Neuron outputNeuron, Neuron inputNeuron) {
		return rnd.nextDouble();
	}

}
