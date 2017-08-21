package hu.akoel.neurnet.strategies;

import hu.akoel.neurnet.neuron.ANeuron;

public class StaticDefaultWeightStrategy implements DefaultWeightStrategy{
	private Double value;
	
	public StaticDefaultWeightStrategy( Double value ){
		this.value = value;
	}
	
	public Double getValue(ANeuron neuron) {
		return value;		
	}

	
	
}
