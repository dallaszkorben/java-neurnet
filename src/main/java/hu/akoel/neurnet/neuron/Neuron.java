package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.layer.ILayer;

public abstract class Neuron implements INeuron {
	protected ILayer containerLayer;
	protected double δ;
	protected double σ;
	
	public void setContainerLayer(ILayer containerLayer ){
		this.containerLayer = containerLayer;
	}
	
	public double getSigma() {
		return σ;
	}
	
	public void setSigma( double σ ) {
		this.σ = σ;
	}
			
	public int getOrder() {
		return containerLayer.getNeuronOrder(this);
	}

	public double getDelta() {
		return this.δ;
	}


}
