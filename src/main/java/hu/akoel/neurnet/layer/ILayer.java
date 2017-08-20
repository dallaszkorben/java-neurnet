package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.INeuron;

public interface ILayer {	
	public int getOrderOfLayer();
	public int getNumberOfNeurons();
	public Iterator<INeuron> getIterator();
	public void calculateSigmas();
	public ILayer getPreviousLayer();
	public int getNeuronOrder( INeuron neuron );
}
