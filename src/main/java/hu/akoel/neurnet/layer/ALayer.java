package hu.akoel.neurnet.layer;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.neuron.ANeuron;

public abstract class ALayer{
	protected ArrayList<ANeuron> neuronList = new ArrayList<ANeuron>();
	
	public abstract ALayer getPreviousLayer();
	
	public int getNumberOfNeurons() {
		return neuronList.size();
	}
	
	public Iterator<ANeuron> getNeuronIterator(){
		return neuronList.iterator();
	}
	
	public void calculateSigmas() {
		
		//Go through all neurons
		for( ANeuron actualNeuron: neuronList){

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
	private int getPrevLayer( int order, ALayer actualLayer ){
		ALayer previousLayer = actualLayer.getPreviousLayer();
		if( null != previousLayer ){
			order = getPrevLayer( order + 1, previousLayer );
		}
		return order;
	}
	
	public int getNeuronOrder( ANeuron neuron ){
		return neuronList.indexOf( neuron );
	}
	
}
