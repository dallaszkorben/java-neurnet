package layer;

import java.util.ArrayList;
import java.util.Iterator;

import neuron.INeuron;

public abstract class Layer implements ILayer{
	protected ArrayList<INeuron> neuronList = new ArrayList<INeuron>();
	
	public int getSize() {
		return neuronList.size();
	}
	
	public Iterator<INeuron> getIterator(){
		return neuronList.iterator();
	}
	
	public void calculateOutputs() {
		
		//Go through all neurons
		for( INeuron actualNeuron: neuronList){

			//Calculate the weight and the sigma
			actualNeuron.calculateOutput();			
			
		}		
	}
	
	public int getOrder() {
		return getPrevLayer( 0, this );
	}
	
	/**
	 * Recursive method to find out the actual order of the Layer
	 * @param order
	 * @param actualLayer
	 * @return
	 */
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
	
}
