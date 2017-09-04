package hu.akoel.n_neurnet.connectors;

import java.util.Iterator;

import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import hu.akoel.n_neurnet.strategies.RandomResetWeightStrategy;
import hu.akoel.n_neurnet.weightmessage.WeightInputLayer;
import hu.akoel.n_neurnet.weightmessage.WeightInputNeuron;
import hu.akoel.n_neurnet.weightmessage.WeightOutputNeuron;

public class InnerConnector implements IInputConnector, IOutputConnector{
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private Layer outputLayer;
	private Layer inputLayer;
	private double[][] weights;
	
	public InnerConnector( Layer outputLayer, Layer inputLayer ){
		this.outputLayer = outputLayer;
		this.inputLayer = inputLayer;
				
		weights = new double[outputLayer.getSize()][inputLayer.getSize()];

		resetWeights();
	}
	
	//
	// --- COMMON ---
	//
	
//	public void setWeight(int outputNeuronOrder, int inputNeuronOrder, double weight) {
//		this.weights[outputNeuronOrder][inputNeuronOrder] = weight;
//	}

	
	//
	//--- INPUT ---
	//
	public WeightInputLayer getWeights() {

		WeightInputLayer wil = new WeightInputLayer(outputLayer, inputLayer);
		
		for( int i = 0; i < inputLayer.getSize(); i++ ){
			
			WeightInputNeuron win = new WeightInputNeuron( i );
			
			for( int j = 0; j < outputLayer.getSize(); j++ ){
			
				WeightOutputNeuron won = new WeightOutputNeuron( weights[j][i], j );
				win.addOutputNeuron( won );				
			}
			
			wil.addInputNeuron(win);
		}
		return wil;
	}
	
	public void setResetWeightStrategy( IResetWeightStrategy resetWeightStrategy) {
		this.resetWeightStrategy = resetWeightStrategy;
	}

	public void resetWeights() {
		
		for( int i = 0; i < outputLayer.getSize(); i++){			
			for( int j = 0; j < inputLayer.getSize(); j++ ){			
				weights[i][j] = resetWeightStrategy.getWeight( outputLayer.getNeuron(i), inputLayer.getNeuron(j) );
			}			
		}		
	}

	//TODO possible not needed
	public double getInputWeight(int outputNeuronOrder, int inputNeuronOrder) {
		return weights[outputNeuronOrder][inputNeuronOrder];
	}

	public double getInputSumma(int inputNeuronOrder) {
		double summa = 0;
		Iterator<Neuron> neuronIterator = outputLayer.getNeuronIterator();
		while( neuronIterator.hasNext() ){
			Neuron outputNeuron = neuronIterator.next();
			summa += outputNeuron.getSigma() * weights[outputNeuron.getIndex()][inputNeuronOrder];
		}		
		return summa;	
	}

	/**
	 * After calling this method, every InputNeuron has newly calculated:
	 * -sigma
	 * -derivatedSigmaBySum
	 */
	public void calculateInputSigmas() {
		Iterator<Neuron> inputNeuronIterator = inputLayer.getNeuronIterator();
		while( inputNeuronIterator.hasNext() ){
			Neuron inputNeuron = inputNeuronIterator.next();
			double summa = getInputSumma(inputNeuron.getIndex());
			inputNeuron.calculateActivationFunction(summa);			
		}
	}


	//TODO momentum not implemented
	public void calculateInputWeight(int inputNeuronOrder, double α,  double β) {
		for( int i = 0; i < outputLayer.getSize(); i++ ){
			double w = weights[i][inputNeuronOrder];
			w += α * outputLayer.getNeuron( i ).getSigma() * inputLayer.getNeuron( inputNeuronOrder ).getDelta();
			weights[i][inputNeuronOrder] = w;
		}
	}
	
	public void calculateInputWeights(double α,  double β){
		for( int i = 0; i < inputLayer.getSize(); i++ ){
			calculateInputWeight( i, α, β );
		}		
	}


	
	
	//
	//--- OUTPUT ---
	//	
	public void calculateOutputDelta(int outputNeuronIndex) {
		
		//Neuron outputNeuron
		double sum = 0;
		Iterator<Neuron> inputNeuronIterator = inputLayer.getNeuronIterator();
		while( inputNeuronIterator.hasNext() ){
			Neuron inputNeuron = inputNeuronIterator.next();
			double w = weights[outputNeuronIndex][inputNeuron.getIndex()];
			double delta = inputNeuron.getDelta();
			sum += w * delta;
		}
		Neuron outputNeuron = outputLayer.getNeuron(outputNeuronIndex);
		double delta = sum * outputNeuron.getDerivateSigmaBySum();
		outputNeuron.setDelta(delta);
	}

	public void calculateOutputDeltas() {
		for( int i = 0; i < outputLayer.getSize(); i++ ){
			calculateOutputDelta( i );
		}		
	}

}
