package hu.akoel.neurnet.network;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.connectors.InnerConnector;
import hu.akoel.neurnet.connectors.InputConnector;
import hu.akoel.neurnet.connectors.OutputConnector;
import hu.akoel.neurnet.handlers.DataHandler;
import hu.akoel.neurnet.layer.Layer;
import hu.akoel.neurnet.listeners.IActivityListener;
import hu.akoel.neurnet.listeners.ICycleListener;
import hu.akoel.neurnet.resultiterator.IResultIterator;
import hu.akoel.neurnet.strategies.IResetWeightStrategy;
import hu.akoel.neurnet.strategies.RandomResetWeightStrategy;

public class Network {
	private ICycleListener trainingCycleListener = null;
	private ICycleListener testCycleListener = null;
	private IActivityListener activityListener = null;
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private ArrayList<Layer> layerList = new ArrayList<Layer>();	
	private double α = 0.3;
	private double β = 0.0;
	private int maxTrainingLoop = 1;
	private double maxTotalMeanSquareError = 0.001;
	private boolean stopTraining = false;
	
	private InputConnector inputConnector;
	private OutputConnector outputConnector;
	private ArrayList<InnerConnector> innerConnectorList = new ArrayList<InnerConnector>();
	
	private boolean hasBeenInitialized = false;

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
	
	public void setMaxTrainingLoop( int maxTrainingLoop ){
		this.maxTrainingLoop = maxTrainingLoop;
	}
	
	public int getMaxTrainCycle(){
		return this.maxTrainingLoop;
	}
	
	public void setMaxTotalMeanSquareError( double maxTotalMeanSquareError ){
		this.maxTotalMeanSquareError = maxTotalMeanSquareError;
	}
	
	public double getMaxTotalMeanSquareError(){
		return this.maxTotalMeanSquareError;
	}
	
	public void addLayer( Layer layer ){
		layerList.add( layer );
		hasBeenInitialized = false;
	}	

	public void setTrainingCycleListener( ICycleListener cycleListener ){
		this.trainingCycleListener = cycleListener;
	}
	
	public void setTestCycleListener( ICycleListener cycleListener ){
		this.testCycleListener = cycleListener;
	}
	
	public void setActivityListener( IActivityListener activityListener ){
		this.activityListener = activityListener;
	}
	
	public IActivityListener getActivityListener(){
		return this.activityListener;
	}
	
	public void setResetWeightStrategy( IResetWeightStrategy resetWeightStrategy ){
		this.resetWeightStrategy = resetWeightStrategy;
	}
	
	private void resetWeights(){
		inputConnector.resetWeights();
		
		Iterator<InnerConnector> innerConnectorIterator = innerConnectorList.iterator();
		while( innerConnectorIterator.hasNext() ){
			innerConnectorIterator.next().resetWeights();
		}
	}
	
	private void setDataHandler( DataHandler dataHandler ){
		inputConnector.setInputDataHandler( dataHandler );
		outputConnector.setOutputDataHandler( dataHandler );			
	}
	
	public ArrayList<IResultIterator> getResultIteratorArray(){		
		
		ArrayList<IResultIterator> outWeights = new ArrayList<IResultIterator>();
	
		outWeights.add( inputConnector.getResultIterator() );
		Iterator<InnerConnector> innerConnectorIterator = innerConnectorList.iterator();
		while( innerConnectorIterator.hasNext() ){
			InnerConnector ic = innerConnectorIterator.next();
			outWeights.add( ic.getResultIterator() );
		}
		return outWeights;
	}
	
	public void stopTraining(){
		this.stopTraining = true;
	}
	
	/**
	 * Must be called when added or removed Layer
	 * @param dataHandler
	 */
	private void initialize(){
	
		//inputConnector = new InputConnector( dataHandler, layerList.get(0) );
		inputConnector = new InputConnector( layerList.get(0) );
		inputConnector.setResetWeightStrategy(resetWeightStrategy);
		inputConnector.resetWeights();
		
		//outputConnector = new OutputConnector( layerList.get( layerList.size() - 1 ), dataHandler );
		outputConnector = new OutputConnector( layerList.get( layerList.size() - 1 ) );
		
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
		hasBeenInitialized = true;
	}

	public void executeTraining( DataHandler trainingDataHandler ){
		executeTraining(true, trainingDataHandler );
	}
	
	public void executeTraining( boolean needResetWeights, DataHandler trainingDataHandler ){
	
		if( null != activityListener ){
			activityListener.started();
		}

		stopTraining = false;
		
		//Makes the necessary connections between Layers
		if( !hasBeenInitialized ){
			initialize();
		}
		
		//Resets the Weights if needed
		if( needResetWeights ){
			resetWeights();
		}

		//Sets the necessary DataHandler
		setDataHandler( trainingDataHandler );
		
		//Run the training again and again until the error is less then a certain value
		for( int i = 0; i < maxTrainingLoop; i++ ){
			
			double squareError = 0;

			//Starts Training data from begining
			trainingDataHandler.reset();
			while( trainingDataHandler.hasNext() ){

				//Takes the next Training data
				trainingDataHandler.takeNext();		
		
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
			
			squareError /= trainingDataHandler.getSize();
			
			if( null != trainingCycleListener )
				trainingCycleListener.handlerError( i, squareError, getResultIteratorArray());
		
			if( squareError <= maxTotalMeanSquareError ){				
				break;
			}
			
			if( stopTraining ){
				break;
			}
		}
		
		if( null != activityListener ){
			activityListener.stopped();
		}
	}
	
	public void executeTest( DataHandler testDataHandler ){
		
		//It returns if there was no Initialization
		if( !hasBeenInitialized ){
			return;
		}
		
		//Sets the necessary DataHandler
		setDataHandler( testDataHandler );
		
		//Resets the test data
		testDataHandler.reset();
		
		int i = 0;
		while( testDataHandler.hasNext() ){

			//Takes the next Training data
			testDataHandler.takeNext();		
		
			//--- Calculate Sigma
			inputConnector.calculateInputSigmas();
			for( InnerConnector innerConnector: innerConnectorList ){
				innerConnector.calculateInputSigmas();
			}

			if( null != testCycleListener ){
				testCycleListener.handlerError( i, outputConnector.getMeanSquareError(), getResultIteratorArray());
			}
			
			i++;
						
			/*Iterator<Neuron> neuronIterator = outputConnector.getOutputLayer().getNeuronIterator();
			while( neuronIterator.hasNext() ){
				Neuron actualNeuron = neuronIterator.next();
		
				System.out.println("input: " + testDataHandler.getInput( actualNeuron.getIndex() ) + " expected value: " + testDataHandler.getExpectedOutput( actualNeuron.getIndex()) + " actual value: " + actualNeuron.getSigma() );
				
			}*/
		}
	}
}
