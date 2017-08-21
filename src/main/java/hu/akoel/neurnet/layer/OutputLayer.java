package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.OutputNeuron;

public class OutputLayer extends ANormalLayer{
	
	public void addNeuron(OutputNeuron neuron) {
		neuronList.add( neuron );
		neuron.setContainerLayer(this);
	}

	public void calculateWeights(double[] expectedOutputs, double α, double β) {		
		
		for( ANeuron actualNeuron: neuronList){
			
			//Calculate Delta
			double output = actualNeuron.getSigma();
			double expectedValue = expectedOutputs[ this.getNeuronOrder(actualNeuron) ];
			double delta = (expectedValue - output) * output * ( 1 - output );
			
			//Calculate Weight
			actualNeuron.calculateWeight( delta, α, β );
		}		
	}

	public double getMeanSquareError(double[] expectedOutputs) {

		double squareError = 0;
		for( ANeuron actualNeuron: neuronList){
			squareError += Math.pow( actualNeuron.getSigma() - expectedOutputs[this.getNeuronOrder(actualNeuron)], 2 );	
		}
		squareError /= this.getNumberOfNeurons();
		
		return squareError;
	}
	
	@Override
	public String toString(){
		String out = this.getOrderOfLayer() + ". layer (Output)\n";
		
		//Through the Neurons
		for( ANeuron actualNeuron: neuronList){
			out += actualNeuron.toString();
		}
		
		return out;
	}

}