package hu.akoel.neurnet.connectors;

import hu.akoel.neurnet.layer.Layer;

public interface IOutputConnector extends IConnector{
	
	public void calculateOutputDelta( int outputNeuronIndex );
	public void calculateOutputDeltas();
	public Layer getOutputLayer();

}
