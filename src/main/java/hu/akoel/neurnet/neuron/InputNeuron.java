package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class InputNeuron extends Neuron implements IInputNeuron{

	private Double inputValue;
	private Double w = null;
	
	public void calculateOutput() {
		double S = w * inputValue;
		σ = 1 / ( 1 + Math.pow( Math.E, -S ) );		
	}

	public void calculateWeight(double δ, double α, double β) {
		this.δ = δ;
		
		//TODO ez kerdes, hogy ertelmes-e
		w = w + α * δ * inputValue;
		
	}
	
	public void setInput(double inputValue) {
		this.inputValue = inputValue;		
	}
	
	public void setWeight(DefaultWeightStrategy defaultWeightStrategy) {
		this.w = defaultWeightStrategy.getValue();
	}

	public String toString(){
		String toIndex = String.valueOf( this.getOrder() );
		String out = "  " + toIndex + ". Neuron δ=" + δ + "\n";
		out += "    w=" + w;
		return out;
	}

}
