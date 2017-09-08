package hu.akoel.neurnet.neuron;

import hu.akoel.neurnet.activationfunctions.IActivationFunction;
import hu.akoel.neurnet.activationfunctions.SigmoidActivationFunction;

public class Neuron {
	protected double δp=0;	//δ(t-1)
	protected double δ=0;	//δ(t)
	
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
	
	public double getPreviousDelta(){
		return δp;
	}
	
	public void setDelta( double δ ){
		this.δp = this.δ;
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
