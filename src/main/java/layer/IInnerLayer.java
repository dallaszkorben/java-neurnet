package layer;

import neuron.INormalNeuron;

public interface IInnerLayer extends ILayer{
	public void addNeuron(INormalNeuron neuron );	
	public void calculateWeights( ILayer nextLayer );
}
