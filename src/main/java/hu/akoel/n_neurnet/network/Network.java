package hu.akoel.n_neurnet.network;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.connectors.InnerConnector;
import hu.akoel.n_neurnet.connectors.InputConnector;
import hu.akoel.n_neurnet.connectors.OutputConnector;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.listeners.InputListener;
import hu.akoel.n_neurnet.listeners.OutputListener;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import hu.akoel.n_neurnet.strategies.RandomResetWeightStrategy;

public class Network {
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private ArrayList<Layer> layerList = new ArrayList<Layer>();	
	private InputListener inputListener;
	private OutputListener outputListener;
	private double α = 0.3;
	private double β = 1;
	private int maxTrainCycle = 1;
	
	private InputConnector inputConnector;
	private OutputConnector outputConnector;
	private ArrayList<InnerConnector> innerConnectorList;

	public Network( InputListener inputListener, OutputListener outputListener ){
		this.inputListener = inputListener;
		this.outputListener = outputListener;
	}

	public void setLearningRate( double α ){
		this.α = α;
	}
	
	public double getLearningRate(){
		return this.α;
	}
	
	public void setMomentum( double β ){
		this.β = β;
	}
	
	public double getMomentum(){
		return this.β;
	}
	
	public void setMaxTrainCycle( int maxTrainCycle ){
		this.maxTrainCycle = maxTrainCycle;
	}
	
	public int getMaxTrainCycle(){
		return this.maxTrainCycle;
	}
	
	public void addLayer( Layer layer ){
		layerList.add( layer );
	}
	
	public void setResetWeightStrategy(IResetWeightStrategy resetWeightStrategy) {
		this.resetWeightStrategy = resetWeightStrategy;
		
		if( null != inputConnector ){
			this.inputConnector.setResetWeightStrategy(resetWeightStrategy);
		}
		for( InnerConnector innerConnector: innerConnectorList ){
			innerConnector.setResetWeightStrategy(resetWeightStrategy);
		}
	}
	
	public void fixConnections(){
		inputConnector = new InputConnector( inputListener, layerList.get(0) );
		inputConnector.setResetWeightStrategy(resetWeightStrategy);
		outputConnector = new OutputConnector( layerList.get( layerList.size() - 1 ), outputListener );
		innerConnectorList = new ArrayList<InnerConnector>();
		
		Iterator<Layer> layerIterator = layerList.iterator();
		Layer previousLayer = null;
		while( layerIterator.hasNext() ){
			Layer actualLayer = layerIterator.next();
			if( null != previousLayer ){
				InnerConnector innerConnector = new InnerConnector( previousLayer, actualLayer );
				innerConnector.setResetWeightStrategy(resetWeightStrategy);
				innerConnectorList.add(innerConnector);
			}
			previousLayer = actualLayer;
		}
	}
	
	public void executeOneCycle(){
		
		//--- 1. step ---
		//--- Calculate Sigma

		//--- Step 2. ---
		//--- Mean Square Error calculation

		//--- Step 3. ---
		//--- Calculate Delta ---
		
		//--- Step 4. ---
		//--- Calculate Weight ---
		
		
	}
}
