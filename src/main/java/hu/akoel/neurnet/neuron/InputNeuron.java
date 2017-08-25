package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class InputNeuron extends ANeuron{

	private Double inputValue;
	private Double w = null;
	
	@Override
	public void calculateOutput() {
		double S = w * inputValue;
		σ = getActivationFunctionStrategy().getSigma(S);	//1 / ( 1 + Math.pow( Math.E, -S ) );		
	}

	@Override
	public void calculateWeight(double δ, double α, double β) {
		this.δ = δ;
		
		//TODO ez kerdes, hogy ertelmes-e
		w = w + α * δ * inputValue;		
	}
	
	public Double getInput(){
		return inputValue;
	}
	
	public void setInput(Double inputValue) {
		this.inputValue = inputValue;		
	}
	
	public void setWeight(DefaultWeightStrategy defaultWeightStrategy) {
		this.w = defaultWeightStrategy.getValue( null, this );
	}

	public double getWeight() {
		return this.w;
	}
	
	@Override
	public String toString(){
		String toIndex = String.valueOf( this.getOrder() );
		String out = "  " + toIndex + ". Neuron (Input) δ=" + δ + "\n";
		out += "    w=" + w;
		return out;
	}

}
