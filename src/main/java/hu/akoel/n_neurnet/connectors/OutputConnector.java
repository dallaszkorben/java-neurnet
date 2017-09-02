package hu.akoel.n_neurnet.connectors;

import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.listeners.OutputListener;
import hu.akoel.n_neurnet.neuron.Neuron;

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


}
