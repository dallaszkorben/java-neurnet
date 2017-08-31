package hu.akoel.n_neurnet.activationfunctions;

public class SigmoidActivationFunction implements IActivationFunction{
	private double σ;
	private double derivatedSigmaBySum;

	public void calculateDetails(double summa) {
		σ = 1 / ( 1 + Math.pow( Math.E, -summa ) );
		derivatedSigmaBySum = σ * ( 1 - σ );
	}

	public double getSigma() {
		return σ;
	}

	public double getDerivateSigmaBySum() {
		return derivatedSigmaBySum;
	}

}
