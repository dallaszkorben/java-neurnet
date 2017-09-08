package hu.akoel.neurnet.activationfunctions;

public interface IActivationFunction {
	
	public void calculateDetails( double summa );
	
	public double getSigma();
	
	public double getDerivateSigmaBySum();

}
