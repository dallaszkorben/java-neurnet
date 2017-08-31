package hu.akoel.n_neurnet.connectors;

import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;

public interface IInputConnector extends IConnector{
	public void setResetWeightStrategy( IResetWeightStrategy resetWeightStrategy );
	public void resetWeights();
	
	public double getInputSumma( int inputNeuronOrder );
	public void calculateInputWeight( int inputNeuronOrder, double α,  double β );
	public double getInputWeight(int outputNeuronOrder, int inputNeuronOrder );
	public void calculateInputSigmas();

//	public void setWeight(int outputNeuronOrder, int inputNeuronOrder, double weight );
//	public double[] getWeights( int inputNeuronOrder);
}
