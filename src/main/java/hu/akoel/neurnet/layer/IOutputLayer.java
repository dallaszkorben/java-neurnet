package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.INormalNeuron;

public interface IOutputLayer extends ILayer{
	public void addNeuron(INormalNeuron neuron );	
	public void calculateWeights( double[] expectedOutputs );
	public double getMeanSquareError( double[] expectedOutputs );
	public void setPreviousLayer( ILayer previousLayer );
}
