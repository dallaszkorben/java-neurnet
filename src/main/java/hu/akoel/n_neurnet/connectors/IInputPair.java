package hu.akoel.n_neurnet.connectors;

public interface IInputPair {
	public double getInputValue();
	public double getWeight();
	public void setWeight( double weight );
}
