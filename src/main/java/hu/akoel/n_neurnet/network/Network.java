package hu.akoel.n_neurnet.network;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.connectors.InnerConnector;
import hu.akoel.n_neurnet.connectors.InputConnector;
import hu.akoel.n_neurnet.connectors.OutputConnector;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.listeners.DataListener;
import hu.akoel.n_neurnet.listeners.ICycleListener;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import hu.akoel.n_neurnet.strategies.RandomResetWeightStrategy;
import hu.akoel.neurnet.neuron.OutputNeuron;


public class Network {
	private ICycleListener trainingCycleListener = null;
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private ArrayList<Layer> layerList = new ArrayList<Layer>();	
	private DataListener dataListener;
	private double α = 0.3;
	private double β = 1;
	private int maxTrainCycle = 1;
	
	private InputConnector inputConnector;
	private OutputConnector outputConnector;
	private ArrayList<InnerConnector> innerConnectorList = new ArrayList<InnerConnector>();

	public Network( DataListener dataListener ){
		this.dataListener = dataListener;
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
		
/*		if( null != inputConnector ){
			this.inputConnector.setResetWeightStrategy(resetWeightStrategy);
		}
		for( InnerConnector innerConnector: innerConnectorList ){
			innerConnector.setResetWeightStrategy(resetWeightStrategy);
		}
*/				
	}

	public void setTrainingCycleListener( ICycleListener cycleListener ){
		this.trainingCycleListener = cycleListener;
	}
	
	public void initialize(){
		inputConnector = new InputConnector( dataListener, layerList.get(0) );
		inputConnector.setResetWeightStrategy(resetWeightStrategy);
		inputConnector.resetWeights();
		
		outputConnector = new OutputConnector( layerList.get( layerList.size() - 1 ), dataListener );
		innerConnectorList = new ArrayList<InnerConnector>();
		
		Iterator<Layer> layerIterator = layerList.iterator();
		Layer previousLayer = null;
		while( layerIterator.hasNext() ){
			Layer actualLayer = layerIterator.next();
			if( null != previousLayer ){
				InnerConnector innerConnector = new InnerConnector( previousLayer, actualLayer );
				innerConnector.setResetWeightStrategy(resetWeightStrategy);
				innerConnector.resetWeights();
				
				innerConnectorList.add(innerConnector);
			}
			previousLayer = actualLayer;
		}
	}
	
	public void executeTraining( double maxTotalMeanSquareError ){
	
		//Run the training again and again until the error is less then a certain value
		for( int i = 0; i <= maxTrainCycle; i++ ){
			
			double squareError = 0;

			//Starts Training data from begining
			dataListener.reset();
			while( dataListener.hasNext() ){

				//Takes the next Training data
				dataListener.takeNext();		
		
				//--- 1. step ---
				//--- Calculate Sigma
				inputConnector.calculateInputSigmas();
				for( InnerConnector innerConnector: innerConnectorList ){
					innerConnector.calculateInputSigmas();
				}

				//--- Step 2. ---
				//--- Mean Square Error calculation

				//--- Step 3. ---
				//--- Calculate Delta ---
				outputConnector.calculateOutputDeltas();
				for( int j = innerConnectorList.size() - 1; j >= 0; j--){			
					//for( InnerConnector innerConnector: innerConnectorList ){
					InnerConnector innerConnector = innerConnectorList.get( j );
					innerConnector.calculateOutputDeltas();
				}
		
				//--- Step 4. ---
				//--- Calculate Weight ---
				inputConnector.calculateInputWeights(α, β);
				for( InnerConnector innerConnector: innerConnectorList ){
					innerConnector.calculateInputWeights(α, β);
				}
				
				//Error Calculation
				squareError += outputConnector.getMeanSquareError();

			}
			
			squareError /= dataListener.getSize();
			
			if( null != trainingCycleListener )
				trainingCycleListener.handlerError( i, squareError);
		
			if( squareError <= maxTotalMeanSquareError ){				
				break;
			}
		}
	}
	
	public void executeTest( DataListener dataListener ){
		
	}
}
