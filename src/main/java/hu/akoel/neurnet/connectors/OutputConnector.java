package hu.akoel.neurnet.connectors;

import java.util.Iterator;

import hu.akoel.neurnet.handlers.OutputDataHandler;
import hu.akoel.neurnet.layer.Layer;
import hu.akoel.neurnet.neuron.Neuron;

public class OutputConnector implements IOutputConnector{
	private OutputDataHandler outputDataHandler;
	private Layer outputLayer;

	public OutputConnector( Layer outputLayer ){	
		this.outputLayer = outputLayer;
		//this.outputDataHandler = outputDataHandler;
	}
	
	public void setOutputDataHandler( OutputDataHandler outputDataHandler ){
		this.outputDataHandler = outputDataHandler;
	}
	
	public double getExpectedValue( int outputNeuronIndex ){
		return outputDataHandler.getExpectedOutput(outputNeuronIndex);
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
			squareError += Math.pow( actualNeuron.getSigma() - outputDataHandler.getExpectedOutput( actualNeuron.getIndex() ), 2 );
			
		}
		squareError /= outputLayer.getSize();
	
		return squareError;
	}
	
	public Layer getOutputLayer(){
		return outputLayer;
	}

}
