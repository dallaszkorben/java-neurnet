package hu.akoel.neurnet.strategies;

import java.util.Random;

import hu.akoel.neurnet.neuron.ANeuron;

public class RandomDefaultWeightStrategy implements DefaultWeightStrategy{
	Random rnd = new Random();
	
	public Double getValue(ANeuron neuron) {
		return rnd.nextDouble();
	}
}
