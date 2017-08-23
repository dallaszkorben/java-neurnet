package hu.akoel.neurnet.layer;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.ANormalNeuron;
import hu.akoel.neurnet.neuron.InnerNeuron;
import hu.akoel.neurnet.neuron.NeuronWeights;

public class InnerLayer extends ANormalLayer<InnerLayer, InnerNeuron>{
	private ArrayList<InnerNeuron> neuronList = new ArrayList<InnerNeuron>();
	
	@Override
	public ArrayList<InnerNeuron> getNeuronList() {		
		return neuronList;
	}
	
//	public void addNeuron(InnerNeuron neuron) {
//		neuronList.add( neuron );
//		neuron.setOrder(neuronList.size() - 1);
//		//setContainerLayer(this);
//	}
		
	public void calculateWeights(ALayer<?, ?> nextLayer, double α, double β) {

		for( InnerNeuron actualNeuron: neuronList ){
			
			//Calculate Delta			
			double sigma = actualNeuron.getStoredSigma();
			int actualNeuronOrder = this.getNeuronOrder(actualNeuron);			
			double summaWeightDelta = 0;

			Iterator<? extends ANeuron> nextNeuronIterator = nextLayer.getNeuronIterator();
			while( nextNeuronIterator.hasNext()){
				
				ANormalNeuron nextNeuron = (ANormalNeuron)nextNeuronIterator.next();
				
				double nextDelta = nextNeuron.getDelta();
				NeuronWeights nextNeuronValues = nextNeuron.getNeuronValues( actualNeuronOrder );
				double nextWeight = nextNeuronValues.getW_t();
				summaWeightDelta += nextWeight * nextDelta;				
			}
			double delta = summaWeightDelta * actualNeuron.getActivationFunctionStrategy().getDerivatedSigmaByStoredSigma( sigma ); //sigma * ( 1 - sigma );			
			actualNeuron.calculateWeight( delta, α, β );		
			//actualNeuron.calculateWeight( summaWeightDelta, α, β );
		}
	}
	
	@Override
	public String toString(){
		String out = this.getOrderOfLayer() + ". layer (Inner)\n";
		
		//Through the Neurons
		for( InnerNeuron actualNeuron: neuronList ){
			out += actualNeuron.toString();
		}
		
		return out;
	}
}