package hu.akoel.neurnet.layer;

import java.util.ArrayList;

import hu.akoel.neurnet.neuron.OutputNeuron;

public class OutputLayer extends ANormalLayer<OutputLayer, OutputNeuron>{
	private ArrayList<OutputNeuron> neuronList = new ArrayList<OutputNeuron>();
	
	@Override
	public ArrayList<OutputNeuron> getNeuronList() {
		return neuronList;
	}
	
//	public void addNeuron(OutputNeuron neuron) {
//		neuronList.add( neuron );
//		neuron.setOrder( neuronList.size() - 1);
//		//neuron.setContainerLayer(this);
//	}

	public void calculateWeights(double[] expectedOutputs, double α, double β) {		
		
		for( OutputNeuron actualNeuron: neuronList){
			
			//Calculate Delta
			double output = actualNeuron.getStoredSigma();
			double expectedValue = expectedOutputs[ this.getNeuronOrder(actualNeuron) ];
			
			double delta = (expectedValue - output) * actualNeuron.getActivationFunctionStrategy().getDerivatedSigmaByStoredSigma(actualNeuron.getStoredSigma()); //output * ( 1 - output );
			actualNeuron.calculateWeight( delta, α, β );
			
			//Calculate Weight
			actualNeuron.calculateWeight( delta, α, β );
			
		}		
	}

	public double getMeanSquareError(double[] expectedOutputs) {

		double squareError = 0;
		for( OutputNeuron actualNeuron: neuronList){
			squareError += Math.pow( actualNeuron.getStoredSigma() - expectedOutputs[this.getNeuronOrder(actualNeuron)], 2 );	
		}
		squareError /= this.getNumberOfNeurons();
		
		return squareError;
	}
	
	@Override
	public String toString(){
		String out = this.getOrderOfLayer() + ". layer (Output)\n";
		
		//Through the Neurons
		for( OutputNeuron actualNeuron: neuronList){
			out += actualNeuron.toString();
		}
		
		return out;
	}
}