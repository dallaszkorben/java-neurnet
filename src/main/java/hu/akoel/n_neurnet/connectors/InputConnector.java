package hu.akoel.n_neurnet.connectors;

import java.util.Iterator;

import hu.akoel.n_neurnet.handlers.InputDataHandler;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.resultiterator.IResultIterator;
import hu.akoel.n_neurnet.resultiterator.InputLayerResultIterator;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import hu.akoel.n_neurnet.strategies.RandomResetWeightStrategy;

public class InputConnector implements IInputConnector{
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private InputDataHandler inputDataHandler;
	private Layer inputLayer;
	private double[] weights;

	public InputConnector( Layer inputLayer ){
		this.inputLayer = inputLayer;
		weights = new double[inputLayer.getSize()];
		resetWeights();
	}

	public Layer getInputLayer() {		
		return inputLayer;
	}
	
	public double[] getWeights(){
		return weights;
	}
	
	public IResultIterator getResultIterator() {
		return new InputLayerResultIterator(this);
	}
	
	public void setInputDataHandler( InputDataHandler inputDataHandler ){
		this.inputDataHandler = inputDataHandler;
	}
	
	public double getInputValue( int inputNeuronIndex ){
		return inputDataHandler.getInput( inputNeuronIndex );
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
