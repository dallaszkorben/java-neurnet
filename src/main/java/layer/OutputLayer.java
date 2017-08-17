package layer;

import java.util.ArrayList;

import neuron.INeuron;
import neuron.INormalNeuron;

//TODO mivel az OutputLayer majdnem azonos az InnerLayer-rel ezert nem a Layerbol, hanem egy ujabb Layerbol kellene szarmaztatnom
public class OutputLayer extends Layer implements IOutputLayer{
	private ILayer previousLayer;

	@SuppressWarnings("unused")
	private OutputLayer(){}
	
	//It is mandatory to specify the previous layer
	public OutputLayer( ILayer previousLayer ){		
		this.previousLayer = previousLayer;
	}
	
	public void addNeuron(INormalNeuron neuron) {
		neuronList.add( neuron );
		
		//Initialize the Neuron with previous layer
		neuron.initializeNeuron(this, previousLayer);
	}
	
	@Override
	public String toString(){
		String out = this.getOrder() + ". layer (Output)\n";
		
		//Through the Neurons
		for( INeuron actualNeuron: neuronList){
			out += actualNeuron.toString();
		}
		
		return out;
	}

	public ILayer getPreviousLayer() {		
		return previousLayer;
	}

	public void calculateWeights(ArrayList<Double> expectedOutputs) {		
		
		for( INeuron actualNeuron: neuronList){
			
			//Calculate Delta
			double output = actualNeuron.getSigma();
			double expectedValue = expectedOutputs.get( this.getNeuronOrder(actualNeuron) );
			double delta = (expectedValue - output) * output * ( 1 - output );
			
			//Calculate Weight
			actualNeuron.calculateWeight( delta );
		}		
		
	}
}