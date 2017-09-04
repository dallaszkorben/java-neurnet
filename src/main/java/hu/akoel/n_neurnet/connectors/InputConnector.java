package hu.akoel.n_neurnet.connectors;

import java.util.Iterator;

import hu.akoel.n_neurnet.handlers.InputDataHandler;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import hu.akoel.n_neurnet.strategies.RandomResetWeightStrategy;
import hu.akoel.n_neurnet.weightmessage.WeightInputLayer;
import hu.akoel.n_neurnet.weightmessage.WeightInputNeuron;
import hu.akoel.n_neurnet.weightmessage.WeightOutputNeuron;

public class InputConnector implements IInputConnector{
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private InputDataHandler inputDataHandler;
	private Layer inputLayer;
	private double[] weights;

	public InputConnector( Layer inputLayer ){
		//this.inputDataHandler = inputDataHandler;
		this.inputLayer = inputLayer;
		weights = new double[inputLayer.getSize()];
	}
	
	public WeightInputLayer getWeights() {
		WeightInputLayer wil = new WeightInputLayer(null, inputLayer);
		
		for( int i = 0; i < inputLayer.getSize(); i++ ){
			WeightInputNeuron win = new WeightInputNeuron( i );
			
			WeightOutputNeuron won = new WeightOutputNeuron(weights[i], -1);
			win.addOutputNeuron(won);
			
			wil.addInputNeuron(win);
		}
		return wil;
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
