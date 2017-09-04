package hu.akoel.n_neurnet.weightmessage;

import java.util.ArrayList;

import hu.akoel.n_neurnet.layer.Layer;

public class WeightInputLayer {
	public Layer inputLayer;
	public Layer outputLayer;
	public ArrayList<WeightInputNeuron> inputNeuronList = new ArrayList<WeightInputNeuron>();
	
	public WeightInputLayer( Layer outputLayer, Layer inputLayer ){
		this.inputLayer = inputLayer;
		this.outputLayer = outputLayer;
	}
	
	public void addInputNeuron( WeightInputNeuron inputNeuron ){
		this.inputNeuronList.add( inputNeuron );
	}
	
}
