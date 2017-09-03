package hu.akoel.n_neurnet.listeners;

public abstract class DataListener implements InputListener, OutputListener {

	abstract public boolean hasNext();
	
	abstract public void reset();
	
	abstract public void takeNext();
	
	abstract public double getSize();
}
