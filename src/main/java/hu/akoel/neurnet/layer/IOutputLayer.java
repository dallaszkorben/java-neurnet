package hu.akoel.neurnet.layer;

import java.util.ArrayList;

import hu.akoel.neurnet.neuron.INormalNeuron;

public interface IOutputLayer extends ILayer{
	public void addNeuron(INormalNeuron neuron );	
	public void calculateWeights( ArrayList<Double> expectedOutputs );
}
