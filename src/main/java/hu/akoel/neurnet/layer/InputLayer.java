package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.IInputNeuron;
import hu.akoel.neurnet.neuron.INeuron;
import hu.akoel.neurnet.neuron.INormalNeuron;
import hu.akoel.neurnet.neuron.NeuronValues;

public class InputLayer extends Layer implements IInputLayer {

	public void addNeuron(IInputNeuron neuron) {
		neuronList.add(neuron);
		neuron.initializeNeuron( this );
	}
	
	@Override
	public String toString(){
		String out = this.getOrder() + ". layer (Input)\n";
		
		//Through the Neurons
		for( INeuron actualNeuron: neuronList){
			out += actualNeuron.toString() + "\n";
		}
		
		return out;
	}

	public ILayer getPreviousLayer() {		
		return null;
	}

	public void calculateWeights(ILayer nextLayer) {
		
		//Through the Neurons
		for( INeuron actualNeuron: neuronList){
			double sigma = actualNeuron.getSigma();
			int actualNeuronOrder = this.getNeuronOrder(actualNeuron);			
			double summaWeightDelta = 0;
			
			Iterator<INeuron> nextNeuronIterator = nextLayer.getIterator();
			while( nextNeuronIterator.hasNext()){
				
				INormalNeuron nextNeuron = (INormalNeuron)nextNeuronIterator.next();
				
				double nextDelta = nextNeuron.getDelta();
				NeuronValues nextNeuronValues = nextNeuron.getNeuronValues( actualNeuronOrder );
				double nextWeight = nextNeuronValues.getW_t();
				summaWeightDelta += nextWeight * nextDelta;				
			}
			double delta = summaWeightDelta * sigma * ( 1 - sigma );
			actualNeuron.calculateWeight( delta );			
		}
		
	}
}
