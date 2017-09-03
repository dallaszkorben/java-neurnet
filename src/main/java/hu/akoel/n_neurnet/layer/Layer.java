package hu.akoel.n_neurnet.layer;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.neuron.Neuron;

public class Layer {
	ArrayList<Neuron> neuronList = new ArrayList<Neuron>();

	public void addNeuron( Neuron neuron ){
		neuron.setIndex( neuronList.size() );
		neuronList.add( neuron );
	}
	
	public Iterator<Neuron> getNeuronIterator(){
		return neuronList.iterator();
	}
	
	public int getSize(){
		return neuronList.size();
	}
	
	public Neuron getNeuron( int order ){
		return neuronList.get(order);
	}
	
	public int getNeuronIndex( Neuron neuron ){
		return neuronList.indexOf( neuron );
	}
}
