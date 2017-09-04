package hu.akoel.n_neurnet.weightmessage;

import java.util.ArrayList;

public class WeightInputNeuron {
	public int inputNeuronIndex;
	public ArrayList<WeightOutputNeuron> outputNeurons = new ArrayList<WeightOutputNeuron>();
	
	public WeightInputNeuron( int inputNeuronIndex){
		this.inputNeuronIndex = inputNeuronIndex;
	}
	
	public void addOutputNeuron( WeightOutputNeuron outputNeuron ){
		outputNeurons.add( outputNeuron );
	}
}
