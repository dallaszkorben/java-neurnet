package hu.akoel.n_neurnet.connectors;

public interface IOutputConnector extends IConnector{
	
	public void calculateOutputDelta( int outputNeuronIndex );
	public void calculateOutputDeltas();

}
