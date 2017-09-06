package hu.akoel.n_neurnet.connectors;

import hu.akoel.n_neurnet.layer.Layer;

public interface IOutputConnector extends IConnector{
	
	public void calculateOutputDelta( int outputNeuronIndex );
	public void calculateOutputDeltas();
	public Layer getOutputLayer();

}
