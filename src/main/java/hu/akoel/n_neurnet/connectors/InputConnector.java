package hu.akoel.n_neurnet.connectors;

import java.util.Iterator;

import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.listeners.InputListener;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import hu.akoel.n_neurnet.strategies.RandomResetWeightStrategy;

public class InputConnector implements IInputConnector{
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private InputListener inputListener;
	private Layer inputLayer;
	private double[] weights;

	public InputConnector( InputListener inputListener, Layer inputLayer ){
		this.inputListener = inputListener;
		this.inputLayer = inputLayer;
		weights = new double[inputLayer.getSize()];
	}
	
	public double getInputValue( int inputNeuronIndex ){
		return inputListener.getInput( inputNeuronIndex );
	}
	
	public void setResetWeightStrategy(IResetWeightStrategy resetWeightStrategy) {
		this.resetWeightStrategy = resetWeightStrategy;		
	}

	public void resetWeights() {
		for( int j = 0; j < inputLayer.getSize(); j++ ){			
			weights[j] = resetWeightStrategy.getWeight( null, inputLayer.getNeuron(j) );
		}					
	}

	public double getInputSumma(int inputNeuronIndex){
		return getInputValue(inputNeuronIndex) * weights[ inputNeuronIndex ];
	}
	
	public void calculateInputWeight(int inputNeuronIndex, double α, double β) {
		double w = weights[inputNeuronIndex];
		w += α * getInputValue( inputNeuronIndex ) * inputLayer.getNeuron( inputNeuronIndex ).getDelta();
		weights[inputNeuronIndex] = w;				
	}
	
	public void calculateInputWeights(double α,  double β){
		for( int i = 0; i < inputLayer.getSize(); i++ ){
			calculateInputWeight( i, α, β );
		}		
	}

	public void calculateInputSigmas() {
		Iterator<Neuron> inputNeuronIterator = inputLayer.getNeuronIterator();
		while( inputNeuronIterator.hasNext() ){
			Neuron inputNeuron = inputNeuronIterator.next();
			double summa = getInputSumma(inputNeuron.getIndex());
			inputNeuron.calculateActivationFunction(summa);			
		}		
	}
	
	public double getInputWeight(int inputNeuronIndex) {
		return weights[inputNeuronIndex];
	}


}
