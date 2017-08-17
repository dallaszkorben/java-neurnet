package layer;

import neuron.IInputNeuron;

public interface IInputLayer extends ILayer{
	public void addNeuron(IInputNeuron neuron );
	public void calculateWeights( ILayer nextLayer );
}