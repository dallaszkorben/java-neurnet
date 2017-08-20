package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.INormalNeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public interface IInnerLayer extends ILayer{
	public void addNeuron(INormalNeuron neuron );	
	public void calculateWeights(ILayer nextLayer, double α, double β);
	public void initializeNeurons( ILayer previousLayer,  DefaultWeightStrategy defaultWeightStrategy );
	//public void setNextLayer( ILayer nextLayer );
}
