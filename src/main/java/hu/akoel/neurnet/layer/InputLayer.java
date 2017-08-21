package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.ANormalNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.neuron.NeuronWeights;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class InputLayer extends ALayer{
	
	public void addNeuron(InputNeuron neuron) {
		neuronList.add(neuron);
		neuron.setContainerLayer( this );
	}

	public void initializeNeurons(DefaultWeightStrategy defaultWeightStrategy) {
		for( ANeuron actualNeuron: neuronList){
			((InputNeuron)actualNeuron).setWeight(defaultWeightStrategy);
		}		
	}
	
	public ALayer getPreviousLayer() {		
		return null;
	}

	public void calculateWeights(ALayer nextLayer, double α, double β) {
		
		//Through the Neurons
		for( ANeuron actualNeuron: neuronList){
			double sigma = actualNeuron.getSigma();
			int actualNeuronOrder = this.getNeuronOrder(actualNeuron);			
			double summaWeightDelta = 0;
			
			Iterator<ANeuron> nextNeuronIterator = nextLayer.getNeuronIterator();
			while( nextNeuronIterator.hasNext()){
				
				ANormalNeuron nextNeuron = (ANormalNeuron)nextNeuronIterator.next();
				
				double nextDelta = nextNeuron.getDelta();
				NeuronWeights nextNeuronValues = nextNeuron.getNeuronValues( actualNeuronOrder );
				double nextWeight = nextNeuronValues.getW_t();
				summaWeightDelta += nextWeight * nextDelta;				
			}
			
			//TODO move it to Neuron
			double delta = summaWeightDelta * sigma * ( 1 - sigma );
			actualNeuron.calculateWeight( delta, α, β );
			//actualNeuron.calculateWeight( summaWeightDelta, α, β );
		}		
	}
	
	@Override
	public String toString(){
		String out = this.getOrderOfLayer() + ". layer (Input)\n";
		
		//Through the Neurons
		for( ANeuron actualNeuron: neuronList){
			out += actualNeuron.toString() + "\n";
		}
		
		return out;
	}
}
