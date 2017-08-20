package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.IInputNeuron;
import hu.akoel.neurnet.neuron.INeuron;
import hu.akoel.neurnet.neuron.INormalNeuron;
import hu.akoel.neurnet.neuron.NeuronWeights;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class InputLayer extends Layer implements IInputLayer {
	//private ILayer nextLayer;
	
	public void addNeuron(IInputNeuron neuron) {
		neuronList.add(neuron);
		neuron.setContainerLayer( this );
	}
	
	//public void setNextLayer(ILayer nextLayer) {
	//	this.nextLayer = nextLayer;		
	//}

	public void initializeNeurons(DefaultWeightStrategy defaultWeightStrategy) {
		for( INeuron actualNeuron: neuronList){
			((IInputNeuron)actualNeuron).setWeight(defaultWeightStrategy);
		}		
	}
	
	public ILayer getPreviousLayer() {		
		return null;
	}

	public void calculateWeights(ILayer nextLayer, double α, double β) {
		
		//Through the Neurons
		for( INeuron actualNeuron: neuronList){
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
		for( INeuron actualNeuron: neuronList){
			out += actualNeuron.toString() + "\n";
		}
		
		return out;
	}
}
