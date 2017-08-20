package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.IInputNeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public interface IInputLayer extends ILayer{
	public void addNeuron(IInputNeuron neuron );
	public void calculateWeights( ILayer nextLayer, double α, double β );
	public void initializeNeurons( DefaultWeightStrategy defaultWeightStrategy );
	//public void setNextLayer( ILayer nextLayer );
}