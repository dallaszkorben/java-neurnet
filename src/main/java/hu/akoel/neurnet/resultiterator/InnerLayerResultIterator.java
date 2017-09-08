package hu.akoel.neurnet.resultiterator;

import hu.akoel.neurnet.connectors.InnerConnector;
import hu.akoel.neurnet.layer.Layer;
import hu.akoel.neurnet.neuron.Neuron;

public class InnerLayerResultIterator implements IResultIterator{
	public int actualNeuron = -1;
	public int actualWeight = -1;
	public Layer inputLayer;
	public Layer outputLayer;
	public double[][] weights;
	
	public InnerLayerResultIterator( InnerConnector innerConnector ){
		this.inputLayer = innerConnector.getInputLayer();
		this.outputLayer = innerConnector.getOutputLayer();
		
		weights = innerConnector.getWeights();
	}
	
	public void reset(){
		actualNeuron = -1;
		actualWeight = -1;
	}
	
	public boolean hasNextNeuron(){
		if( actualNeuron + 1 < inputLayer.getSize() ){
			return true;
		}
		return false;
	}
	
	public Neuron getNextNeuron(){
		actualWeight = -1;		
		return inputLayer.getNeuron( ++actualNeuron );
	}

	public boolean hasNextWeight(){
		if( actualWeight + 1 < weights.length ){
			return true;
		}
		return false;
	}
	
	public double getNextWeight(){
		return weights[++actualWeight][actualNeuron];
	}
	
	public int getNeuronIndex(){
		return actualNeuron;
	}
	
	public int getWeightIndex(){
		return actualWeight;
	}
	
	public Layer getInputLayer() {
		return inputLayer;
	}	
}
