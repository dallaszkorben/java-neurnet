package hu.akoel.neurnet.neuron;

public class NeuronWeights {
	private INeuron previousNeuron;
	private Double w_t;
//	private double δ_t;
//	private double δ_t_1;
	
//	public NeuronValues(INeuron previousNeuron, double w_t, double δ_t, double δ_t_1){
	public NeuronWeights(INeuron previousNeuron, double w_t){		
		this.previousNeuron = previousNeuron;
		this.w_t = w_t;
//		this.δ_t = δ_t;
//		this.δ_t_1 = δ_t_1;
	}
	
	public INeuron getPreviousNeuron() {
		return previousNeuron;
	}

	public double getW_t() {
		return w_t;
	}
	public void setW_t(double w_t) {
		this.w_t = w_t;
	}
/*	public double getδ_t() {
		return δ_t;
	}
	public void setδ_t(double δ_t) {
		this.δ_t = δ_t;
	}
	public double getδ_t_1() {
		return δ_t_1;
	}
	public void setδ_t_1(double δ_t_1) {
		this.δ_t_1 = δ_t_1;
	}
*/	
}
