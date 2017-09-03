package hu.akoel.n_neurnet.connectors;

import java.util.Iterator;

import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.listeners.OutputListener;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.neurnet.neuron.OutputNeuron;

public class OutputConnector implements IOutputConnector{
	private OutputListener outputListener;
	private Layer outputLayer;

	public OutputConnector( Layer outputLayer, OutputListener outputListener ){	
		this.outputLayer = outputLayer;
		this.outputListener = outputListener;
	}
	
	public double getExpectedValue( int outputNeuronIndex ){
		return outputListener.getExpectedOutput(outputNeuronIndex);
	}
	
	public void calculateOutputDelta(int outputNeuronIndex) {
		Neuron outputNeuron = outputLayer.getNeuron(outputNeuronIndex);
		double diff = ( getExpectedValue(outputNeuronIndex) - outputNeuron.getSigma() );
		double delta = diff * outputNeuron.getDerivateSigmaBySum();
		outputNeuron.setDelta(delta);
	}
	
	public void calculateOutputDeltas() {
		for( int i = 0; i < outputLayer.getSize(); i++ ){
			calculateOutputDelta( i );
		}		
	}

	public double getMeanSquareError(){
		double squareError = 0;
	
		Iterator<Neuron> neuronIterator = outputLayer.getNeuronIterator();
		while( neuronIterator.hasNext() ){
			Neuron actualNeuron = neuronIterator.next();			
			squareError += Math.pow( actualNeuron.getSigma() - outputListener.getExpectedOutput( actualNeuron.getIndex() ), 2 );
			
		}
		squareError /= outputLayer.getSize();
	
		return squareError;
	}

}
