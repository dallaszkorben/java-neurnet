package hu.akoel.neurnet.layer;

import java.util.Iterator;

import hu.akoel.neurnet.neuron.INeuron;
import hu.akoel.neurnet.neuron.INormalNeuron;
import hu.akoel.neurnet.neuron.NeuronValues;

public class InnerLayer extends Layer implements IInnerLayer{
	private ILayer previousLayer;

	@SuppressWarnings("unused")
	private InnerLayer(){}
	
	//It is mandatory to specify the previous layer
	public InnerLayer( ILayer previousLayer ){		
		this.previousLayer = previousLayer;
	}
	
	public void addNeuron(INormalNeuron neuron) {
		neuronList.add( neuron );
		neuron.initializeNeuron(this, previousLayer);
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


	public ILayer getPreviousLayer() {		
		return previousLayer;
	}

	public void calculateWeights(ILayer nextLayer) {

		for( INeuron actualNeuron: neuronList){
			
			//Calculate Delta
			
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