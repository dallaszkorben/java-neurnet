package hu.akoel.neurnet.layer;

import hu.akoel.neurnet.neuron.INeuron;
import hu.akoel.neurnet.neuron.INormalNeuron;

public class OutputLayer extends Layer implements IOutputLayer{
	private ILayer previousLayer;
	
	public void addNeuron(INormalNeuron neuron) {
		neuronList.add( neuron );
		neuron.setContainerLayer(this);
	}

	/**
	 * set the previousLayer and connect every Neurons in the layer
	 * to the previous layer
	 */
	public void setPreviousLayer(ILayer previousLayer) {
		this.previousLayer = previousLayer;
		
		for( INeuron actualNeuron: neuronList){
			((INormalNeuron)actualNeuron).connectToPreviousNeuron(previousLayer);
		}
	}

	public ILayer getPreviousLayer() {		
		return previousLayer;
	}

	public void calculateWeights(double[] expectedOutputs) {		
		
		for( INeuron actualNeuron: neuronList){
			
			//Calculate Delta
			double output = actualNeuron.getSigma();
			double expectedValue = expectedOutputs[ this.getNeuronOrder(actualNeuron) ];
			double delta = (expectedValue - output) * output * ( 1 - output );
			
			//Calculate Weight
			actualNeuron.calculateWeight( delta );
		}		
	}

	public double getMeanSquareError(double[] expectedOutputs) {

		double squareError = 0;
		for( INeuron actualNeuron: neuronList){
			squareError += Math.pow( actualNeuron.getSigma() - expectedOutputs[this.getNeuronOrder(actualNeuron)], 2 );	
		}
		squareError /= this.getNumberOfNeurons();
		
		return squareError;
	}
	
	@Override
	public String toString(){
		String out = this.getOrderOfLayer() + ". layer (Output)\n";
		
		//Through the Neurons
		for( INeuron actualNeuron: neuronList){
			out += actualNeuron.toString();
		}
		
		return out;
	}

}