package hu.akoel.neurnet.layer;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.neuron.INeuron;

public abstract class Layer implements ILayer{
	protected ArrayList<INeuron> neuronList = new ArrayList<INeuron>();
	
	public int getNumberOfNeurons() {
		return neuronList.size();
	}
	
	public Iterator<INeuron> getIterator(){
		return neuronList.iterator();
	}
	
	public void calculateSigmas() {
		
		//Go through all neurons
		for( INeuron actualNeuron: neuronList){

			//Calculate the weight and the sigma
			actualNeuron.calculateOutput();			
			
		}		
	}
	
	public int getOrderOfLayer() {
		return getPrevLayer( 0, this );
	}
	
	/**
	 * Recursive method to find out the actual order of the Layer
	 * @param order
	 * @param actualLayer
	 * @return
	 */
	//TOD change it if previous layer doesnot exists
	private int getPrevLayer( int order, ILayer actualLayer ){
		ILayer previousLayer = actualLayer.getPreviousLayer();
		if( null != previousLayer ){
			order = getPrevLayer( order + 1, previousLayer );
		}
		return order;
	}
	
	public int getNeuronOrder( INeuron neuron ){
		return neuronList.indexOf( neuron );
	}
	
	public void generateRandomWeights(){
		for( INeuron actualNeuron: neuronList){
			actualNeuron.generateRandomWeight();
		}
	}
	
}
