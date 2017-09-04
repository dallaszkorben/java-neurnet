package hu.akoel.n_neurnet.handlers;

public abstract class DataHandler implements InputDataHandler, OutputDataHandler {

	abstract public boolean hasNext();
	
	abstract public void reset();
	
	abstract public void takeNext();
	
	abstract public double getSize();
}
