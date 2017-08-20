package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.INormalNeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public interface IOutputLayer extends ILayer{
	public void addNeuron(INormalNeuron neuron );	
	public void calculateWeights(double[] expectedOutputs, double α, double β);
	public double getMeanSquareError( double[] expectedOutputs );
	public void initializeNeurons( ILayer previousLayer, DefaultWeightStrategy defaultWeightStrategy );
}
