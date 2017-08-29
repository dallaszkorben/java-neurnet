package hu.akoel.neurnet.layer;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.neuron.ANeuron;

public abstract class ALayer<L extends ALayer<?, ?>, N extends ANeuron>{

	private Integer orderOfLayer = null;
	
	public abstract ArrayList<N> getNeuronList();
	
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

	public int getNeuronOrder( ANeuron neuron ){
		return getNeuronList().indexOf( neuron );
	}
	
}
