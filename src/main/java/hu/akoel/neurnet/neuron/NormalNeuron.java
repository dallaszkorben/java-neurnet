package hu.akoel.neurnet.neuron;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.ILayer;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class NormalNeuron extends Neuron implements INormalNeuron{	

	private ArrayList<NeuronWeights> weightList;
	//private ILayer previousLayer;

	/**
	 * This method is called when the Layer added to the Network
	 */
	public void connectToPreviousNeuron( ILayer previousLayer, DefaultWeightStrategy defaultWeightStrategy ) {
		
		weightList = new ArrayList<NeuronWeights>();
		
		//this.previousLayer = previousLayer;		
		Iterator<INeuron> previousNeuronIterator = previousLayer.getIterator();
		while( previousNeuronIterator.hasNext() ){	
			NeuronWeights values = new NeuronWeights(previousNeuronIterator.next(), defaultWeightStrategy.getValue() );
			weightList.add( values );
		}
	}
	
	public void calculateOutput() {
		double S = 0;
		for(NeuronWeights values:weightList){
			S += values.getW_t()* values.getPreviousNeuron().getSigma();
		}
		σ = 1 / ( 1 + Math.pow( Math.E, -S ) );
	}

	public void calculateWeight(double δ, double α, double β) {
		this.δ = δ;
		for( NeuronWeights neuronValues: weightList ){
			INeuron previousNeuron = neuronValues.getPreviousNeuron();
			double weight = neuronValues.getW_t();
			
			weight = weight + α * δ * previousNeuron.getSigma();
			
			neuronValues.setW_t( weight );
		}	
	}
	
	public NeuronWeights getNeuronValues(int neuronOrder) {
		return weightList.get( neuronOrder );
	}
	
	public void setWeight(DefaultWeightStrategy defaultWeightStrategy) {
		for( NeuronWeights weightValue: weightList ){
			weightValue.setW_t( defaultWeightStrategy.getValue() );
		}
	}
	
	@Override
	public String toString(){
		String toIndex = String.valueOf( this.getOrder() );
		String out = "  " + toIndex + ". Neuron δ=" + δ + "  σ=" + σ + "\n";
		String fromIndex;		
				
		for( NeuronWeights neuronValues: weightList ){
			
			fromIndex = String.valueOf( neuronValues.getPreviousNeuron().getOrder() );
			out += "    w" + toIndex + fromIndex + "=" + neuronValues.getW_t() + "\n";
		}
		
		return out;
	}



}
