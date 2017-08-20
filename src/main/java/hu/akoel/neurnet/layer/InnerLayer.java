package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.INeuron;
import hu.akoel.neurnet.neuron.INormalNeuron;
import hu.akoel.neurnet.neuron.NeuronWeights;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class InnerLayer extends Layer implements IInnerLayer{
	private ILayer previousLayer;
	//private ILayer nextLayer;
	
	public void addNeuron(INormalNeuron neuron) {
		neuronList.add( neuron );
		neuron.setContainerLayer(this);
	}
	
	/**
	 * set the previousLayer and connect every Neurons in the layer
	 * to the previous layer
	 */
	public void initializeNeurons(ILayer previousLayer, DefaultWeightStrategy defaultWeightStrategy) {
		this.previousLayer = previousLayer;
		
		for( INeuron actualNeuron: neuronList){
			((INormalNeuron)actualNeuron).connectToPreviousNeuron(previousLayer, defaultWeightStrategy);
		}
	}

	//public void setNextLayer(ILayer nextLayer) {
	//	this.nextLayer = nextLayer;
	//}

	public ILayer getPreviousLayer() {		
		return previousLayer;		
	}

	public void calculateWeights(ILayer nextLayer, double α, double β) {

		for( INeuron actualNeuron: neuronList){
			
			//Calculate Delta
			
			double sigma = actualNeuron.getSigma();
			int actualNeuronOrder = this.getNeuronOrder(actualNeuron);			
			double summaWeightDelta = 0;
			
			Iterator<INeuron> nextNeuronIterator = nextLayer.getIterator();
			while( nextNeuronIterator.hasNext()){
				
				INormalNeuron nextNeuron = (INormalNeuron)nextNeuronIterator.next();
				
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
		for( INeuron actualNeuron: neuronList){
			out += actualNeuron.toString();
		}
		
		return out;
	}
}