package hu.akoel.neurnet.resultiterator;

import hu.akoel.neurnet.layer.Layer;
import hu.akoel.neurnet.neuron.Neuron;

public interface IResultIterator {

	public void reset();
	
	public boolean hasNextNeuron();
	
	public Neuron getNextNeuron();

	public boolean hasNextWeight();
	
	public double getNextWeight();
	
	public int getNeuronIndex();
	
	public int getWeightIndex();
	
	public Layer getInputLayer();

}
