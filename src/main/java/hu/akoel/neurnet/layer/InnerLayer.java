package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.ANormalNeuron;
import hu.akoel.neurnet.neuron.InnerNeuron;
import hu.akoel.neurnet.neuron.NeuronWeights;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class InnerLayer extends ANormalLayer{
	
	public void addNeuron(InnerNeuron neuron) {
		neuronList.add( neuron );
		neuron.setContainerLayer(this);
	}
		
	public void calculateWeights(ALayer nextLayer, double α, double β) {

		for( ANeuron actualNeuron: neuronList){
			
			//Calculate Delta
			
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
			double delta = summaWeightDelta * sigma * ( 1 - sigma );			
			actualNeuron.calculateWeight( delta, α, β );		
			//actualNeuron.calculateWeight( summaWeightDelta, α, β );
		}
	}
	
	@Override
	public String toString(){
		String out = this.getOrderOfLayer() + ". layer (Inner)\n";
		
		//Through the Neurons
		for( ANeuron actualNeuron: neuronList){
			out += actualNeuron.toString();
		}
		
		return out;
	}
}