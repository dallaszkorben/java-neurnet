package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.strategies.ActivationFunctionStrategy;
import hu.akoel.neurnet.strategies.SigmoidActivationFunction;

public abstract class ANeuron{
	public static final double defaultDelta = 0.0;
	
	private ActivationFunctionStrategy activationFunctionStrategy = new SigmoidActivationFunction();
	
	private int order = -1;
	
	protected double δ;
	protected double σ;
	
	public abstract void calculateOutput();

	public abstract void calculateWeight(double δ, double α, double β);

	public void setActivationFunctionStrategy( ActivationFunctionStrategy activationFunctionStrategy ){
		this.activationFunctionStrategy = activationFunctionStrategy;
	}
	
//	protected ALayer containerLayer;
	
//	public void setContainerLayer(ALayer containerLayer ){
//		this.containerLayer = containerLayer;
//	}
	
//	public ALayer getContainerLayer(){
//		return containerLayer;
//	}
	
	public ActivationFunctionStrategy getActivationFunctionStrategy(){
		return activationFunctionStrategy;
	}
	
	public double getStoredSigma() {
		return σ;
	}
	
	public void setSigma( double σ ) {
		this.σ = σ;
	}
			
//	public int getOrder() {
//		return containerLayer.getNeuronOrder(this);
//	}

	public void setOrder( int order ){
		this.order = order;
	}
	
	public int getOrder(){
		return order;
	}
	
	public double getDelta() {
		return this.δ;
	}

	public void setDelta( double δ ) {
		this.δ = δ;
	}

}
