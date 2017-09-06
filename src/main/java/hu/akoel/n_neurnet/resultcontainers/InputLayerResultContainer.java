package hu.akoel.n_neurnet.resultcontainers;

import hu.akoel.n_neurnet.connectors.InputConnector;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;

public class InputLayerResultContainer implements IResultContainer{
	public int actualNeuron = -1;
	public int actualWeight = -1;
	public Layer inputLayer;
	public double[] weights;
	
	public InputLayerResultContainer( InputConnector inputConnector ){
		this.inputLayer = inputConnector.getInputLayer();
		weights = inputConnector.getWeights();
	}

	public void reset() {		
		actualNeuron = -1;
		actualWeight = -1;
	}

	public boolean hasNextNeuron() {
		if( actualNeuron + 1 < inputLayer.getSize() ){
			return true;
		}
		return false;
	}

	public Neuron getNextNeuron() {
		actualWeight = -1;		
		return inputLayer.getNeuron( ++actualNeuron );
	}

	public boolean hasNextWeight() {
		if( actualWeight == -1 ){
			return true;
		}
		return false;
	}

	public double getNextWeight() {
		return weights[++actualWeight];
	}

	public int getNeuronIndex() {
		return actualNeuron + 1;
	}

	public int getWeightIndex() {
		return actualWeight + 1;
	}

	public Layer getInputLayer() {
		return inputLayer;
	}	
}
