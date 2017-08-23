package hu.akoel.neurnet.strategies;

public interface ActivationFunctionStrategy {

	public double getSigma( double S );
	
	public double getDerivatedSigmaByStoredSigma( double σ );
	
}
