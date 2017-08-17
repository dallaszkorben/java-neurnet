package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.INeuron;

public interface ILayer {
	public int getOrder();
	public int getSize();
	public Iterator<INeuron> getIterator();
	public void calculateOutputs();
	public ILayer getPreviousLayer();
	public int getNeuronOrder( INeuron neuron );
}
