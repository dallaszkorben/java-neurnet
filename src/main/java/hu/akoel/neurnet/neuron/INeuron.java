package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.layer.ILayer;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public interface INeuron {
	public static final double defaultDelta = 0.0;

	public void setContainerLayer(ILayer containerLayer );
	
	public void calculateOutput();
	
	public double getSigma();
	public void setSigma( double σ);
	
	public int getOrder();
	
	public void calculateWeight( double delta, double α, double β );
	public double getDelta();
	
	public void setWeight(DefaultWeightStrategy defaultWeightStrategy);
}
