package neuron;

import java.util.ArrayList;

import layer.ILayer;

public interface INormalNeuron extends INeuron{
	
	//TODO probably the Neuron should know nothing about the Layer which contains it
	public void initializeNeuron( ILayer actualLayer, ILayer previousLayer );
	
	public NeuronValues getNeuronValues( int neuronOrder );
	
}
