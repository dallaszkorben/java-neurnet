package neuron;

public class InputNeuron extends Neuron implements IInputNeuron{

	private double inputValue;
	private double w_t = defaultWeight;
	
	public void calculateOutput() {
		double S = w_t * inputValue;
		σ = 1 / ( 1 + Math.pow( Math.E, -S ) );		
	}

	public void calculateWeight(double δ) {
		this.δ = δ;
		
		//TODO ez kerdes, hogy ertelmes-e
		w_t = w_t + α * δ * inputValue;
		
	}
	
	public void setInput(double inputValue) {
		this.inputValue = inputValue;		
	}
	
	public String toString(){
		String toIndex = String.valueOf( this.getOrder() );
		String out = "  " + toIndex + ". Neuron δ=" + δ + "\n";
		out += "    w=" + w_t;
		return out;
	}
}
