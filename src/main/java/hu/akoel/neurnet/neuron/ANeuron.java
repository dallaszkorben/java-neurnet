package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.layer.ALayer;

public abstract class ANeuron{
	public static final double defaultDelta = 0.0;
	
	protected ALayer containerLayer;
	protected double δ;
	protected double σ;
	
	public abstract void calculateOutput();
	
	public abstract void calculateWeight(double δ, double α, double β);
	
	public void setContainerLayer(ALayer containerLayer ){
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
