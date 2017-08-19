package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.layer.ILayer;

public interface INeuron {
	public static final double defaultWeight = 0.3;
	public static final double defaultDelta = 0.0;
	public static final double α = 0.2; //Tanulasi rata
	public static final double β = 0.3; //momentum

	public void setContainerLayer(ILayer containerLayer );
	
	public void calculateOutput();
	
	public double getSigma();
	public void setSigma( double σ);
	
	public int getOrder();
	
	public void calculateWeight( double δ );
	public void generateRandomWeight();
	public double getDelta();
	
}
