package hu.akoel.neurnet.connectors;

import hu.akoel.neurnet.layer.Layer;
import hu.akoel.neurnet.strategies.IResetWeightStrategy;

public interface IInputConnector extends IConnector{
	public void setResetWeightStrategy( IResetWeightStrategy resetWeightStrategy );
	public void resetWeights();
	
	public double getInputSumma( int inputNeuronIndex );
	public void calculateInputWeight( int inputNeuronIndex, double α,  double β );
//	public double getInputWeight(int outputNeuronOrder, int inputNeuronOrder );
	public void calculateInputWeights(double α,  double β);
	public void calculateInputSigmas();
	public Layer getInputLayer();

//	public void setWeight(int outputNeuronOrder, int inputNeuronOrder, double weight );
//	public double[] getWeights( int inputNeuronOrder);
}
