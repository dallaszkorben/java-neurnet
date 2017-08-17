package layer;

import java.util.ArrayList;

import neuron.INormalNeuron;

public interface IOutputLayer extends ILayer{
	public void addNeuron(INormalNeuron neuron );	
	public void calculateWeights( ArrayList<Double> expectedOutputs );
}
