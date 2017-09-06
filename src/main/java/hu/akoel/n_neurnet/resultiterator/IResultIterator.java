package hu.akoel.n_neurnet.resultiterator;

import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;

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
