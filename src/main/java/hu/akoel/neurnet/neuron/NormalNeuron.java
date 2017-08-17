package hu.akoel.neurnet.neuron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import hu.akoel.neurnet.layer.ILayer;


public class NormalNeuron extends Neuron implements INormalNeuron{	

	private ArrayList<NeuronValues> valuesList = new ArrayList<NeuronValues>();
	private ILayer previousLayer;

	public void initializeNeuron(ILayer actualLayer, ILayer previousLayer) {
Random rnd = new Random();		
		
		initializeNeuron(actualLayer);
		this.previousLayer = previousLayer;		
		Iterator<INeuron> previousNeuronIterator = previousLayer.getIterator();
		while( previousNeuronIterator.hasNext() ){	
//NeuronValues values = new NeuronValues(previousNeuronIterator.next(), defaultWeight );
						
			NeuronValues values = new NeuronValues(previousNeuronIterator.next(), rnd.nextDouble() );
			valuesList.add( values );
		}
	}
	
	public void calculateOutput() {
		double S = 0;
		for(NeuronValues values:valuesList){
			S += values.getW_t()* values.getPreviousNeuron().getSigma();
		}
		σ = 1 / ( 1 + Math.pow( Math.E, -S ) );
	}

	public void calculateWeight(double δ) {
		this.δ = δ;
		for( NeuronValues neuronValues: valuesList ){
			INeuron previousNeuron = neuronValues.getPreviousNeuron();
			double weight = neuronValues.getW_t();
			
			weight = weight + α * δ * previousNeuron.getSigma();
			
			neuronValues.setW_t( weight );
		}	
	}
	
	@Override
	public String toString(){
		String toIndex = String.valueOf( this.getOrder() );
		String out = "  " + toIndex + ". Neuron δ=" + δ + "  σ=" + σ + "\n";
		String fromIndex;		
				
		for( NeuronValues neuronValues: valuesList ){
			
			fromIndex = String.valueOf( neuronValues.getPreviousNeuron().getOrder() );
			out += "    w" + toIndex + fromIndex + "=" + neuronValues.getW_t() + "\n";
		}
		
		return out;
	}

	public NeuronValues getNeuronValues(int neuronOrder) {
		return valuesList.get( neuronOrder );
	}


	
}
