package neuron;

import layer.ILayer;

public interface INeuron {
	public static final double defaultWeight = 0.3;
	public static final double defaultDelta = 0.0;
	public static final double α = 0.2; //Tanulasi rata
	public static final double β = 0.3; //momentum

	public void initializeNeuron(ILayer actualLayer );
	
	public void calculateOutput();
	
	public double getSigma();
	public void setSigma( double σ);
	
	public int getOrder();
	
	public void calculateWeight( double δ );
	public double getDelta();
	
}
