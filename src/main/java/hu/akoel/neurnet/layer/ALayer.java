package hu.akoel.neurnet.layer;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.OutputNeuron;

public abstract class ALayer<L extends ALayer<?, ?>, N extends ANeuron>{
	//protected ArrayList<ANeuron> neuronList = new ArrayList<ANeuron>();
	private Integer orderOfLayer = null;
	
	public abstract ArrayList<N> getNeuronList();
	
//	public abstract ALayer<?, ?> getPreviousLayer();
	
	public void addNeuron(N neuron) {
		getNeuronList().add( neuron );
		neuron.setOrder( getNeuronList().size() - 1);
		//neuron.setContainerLayer(this);
	}
	
	public int getNumberOfNeurons() {
		return getNeuronList().size();
	}
	
	public Iterator<N> getNeuronIterator(){
		return getNeuronList().iterator();
	}
	
	public void calculateSigmas() {
		
		//Go through all neurons
		for( ANeuron actualNeuron: getNeuronList()){

			//Calculate the weight and the sigma
			actualNeuron.calculateOutput();			
			
		}		
	}
	
	public void setOrderOfLayer( Integer orderOfLayer ){
		this.orderOfLayer = orderOfLayer;
	}
	
	public Integer getOrderOfLayer(){
		return orderOfLayer;
	}
	
//	public int getOrderOfLayer() {
//		return getPrevLayer( 0, this );
//	}
	
	/**
	 * Recursive method to find out the actual order of the Layer
	 * @param order
	 * @param actualLayer
	 * @return
	 */
//	//TODO change it if previous layer doesnot exists
//	private int getPrevLayer( int order, ALayer<?, ?> actualLayer ){
//		ALayer<?, ?> previousLayer = actualLayer.getPreviousLayer();
//		if( null != previousLayer ){
//			order = getPrevLayer( order + 1, previousLayer );
//		}
//		return order;
//	}
	
	public int getNeuronOrder( ANeuron neuron ){
		return getNeuronList().indexOf( neuron );
	}
	
}
