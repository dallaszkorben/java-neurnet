package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.layer.ILayer;

public abstract class Neuron implements INeuron {
	protected ILayer actualLayer;
	protected double δ;
	protected double σ;
	
	public void initializeNeuron(ILayer actualLayer ){
		this.actualLayer = actualLayer;
	}
	
	public double getSigma() {
		return σ;
	}
	
	public void setSigma( double σ ) {
		this.σ = σ;
	}
			
	public int getOrder() {
		return actualLayer.getNeuronOrder(this);
	}

	public double getDelta() {
		return this.δ;
	}


}
