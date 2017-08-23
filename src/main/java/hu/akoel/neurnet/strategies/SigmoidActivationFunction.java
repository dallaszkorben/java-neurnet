package hu.akoel.neurnet.strategies;

public class SigmoidActivationFunction implements ActivationFunctionStrategy{

	public double getSigma(double S) {
		return 1 / ( 1 + Math.pow( Math.E, -S ) );	
	}

	public double getDerivatedSigmaByStoredSigma( double σ ) {
		return σ * ( 1 - σ );
	}


}
