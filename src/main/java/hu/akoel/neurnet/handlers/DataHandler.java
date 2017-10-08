package hu.akoel.neurnet.handlers;

public abstract class DataHandler implements InputDataHandler, OutputDataHandler {

	abstract public boolean hasNext();
	
	abstract public void reset();
	
	abstract public void takeNext();
	
	abstract public int getSize();
	
	abstract public int getInputSize();
	
	abstract public int getOutputSize();
}
