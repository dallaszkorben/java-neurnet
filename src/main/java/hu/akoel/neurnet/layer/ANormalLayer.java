package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.ANormalNeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public abstract class ANormalLayer<L extends ALayer<?, ?>, N extends ANeuron> extends ALayer<L, N>{
//	private ALayer<?, ?> previousLayer;

//	@Override
//	public ALayer<?, ?> getPreviousLayer() {		
//		return previousLayer;
//	}
	
	/**
	 * set the previousLayer and connect every Neurons in the layer
	 * to the previous layer
	 */
	public void initializeNeurons(ALayer<?, ?> previousLayer, DefaultWeightStrategy defaultWeightStrategy) {
//		this.previousLayer = previousLayer;		
		
		for( ANeuron actualNeuron: getNeuronList()){
			Iterator<? extends ANeuron> previousNeutronIterator = previousLayer.getNeuronIterator();
			((ANormalNeuron)actualNeuron).connectToPreviousNeuron(previousNeutronIterator, defaultWeightStrategy);
		}
	}

}
