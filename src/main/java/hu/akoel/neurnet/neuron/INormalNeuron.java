package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.layer.ILayer;

public interface INormalNeuron extends INeuron{
	
	//TODO probably the Neuron should know nothing about the Layer which contains it
	//public void initializeNeuron( ILayer actualLayer, ILayer previousLayer );
	
	public void connectToPreviousNeuron(ILayer previousLayer);
	public NeuronValues getNeuronValues( int neuronOrder );
	
}
