package hu.akoel.neurnet.neuron;

public class InputNeuron extends Neuron implements IInputNeuron{

	private double inputValue;
	private double w = defaultWeight;
	
	public void calculateOutput() {
		double S = w * inputValue;
		σ = 1 / ( 1 + Math.pow( Math.E, -S ) );		
	}

	public void calculateWeight(double δ) {
		this.δ = δ;
		
		//TODO ez kerdes, hogy ertelmes-e
		w = w + α * δ * inputValue;
		
	}
	
	public void setInput(double inputValue) {
		this.inputValue = inputValue;		
	}
	
	public String toString(){
		String toIndex = String.valueOf( this.getOrder() );
		String out = "  " + toIndex + ". Neuron δ=" + δ + "\n";
		out += "    w=" + w;
		return out;
	}

	public void generateRandomWeight() {
		this.w = rnd.nextDouble();		
	}
}
