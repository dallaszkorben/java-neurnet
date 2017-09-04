package hu.akoel.n_neurnet.neuron;

import hu.akoel.n_neurnet.activationfunctions.IActivationFunction;
import hu.akoel.n_neurnet.activationfunctions.SigmoidActivationFunction;

public class Neuron {
	protected double δ=0;
	//protected double σ;
	
	int index = -1;
	IActivationFunction activationFunction = new SigmoidActivationFunction();

	public void setActivationFunction(IActivationFunction activationFunction){
		this.activationFunction = activationFunction;
	}

	public void setIndex( int order ){
		this.index = order;
	}
	
	public int getIndex(){
		return index;
	}

	public double getDelta(){
		return δ;
	}
	
	public void setDelta( double δ ){
		this.δ = δ;
	}

	public void calculateActivationFunction( double summa ){
		activationFunction.calculateDetails(summa);
	}
	
	public double getDerivateSigmaBySum(){
		return activationFunction.getDerivateSigmaBySum();
	}
	
	public double getSigma(){
		return activationFunction.getSigma();
	}

}
