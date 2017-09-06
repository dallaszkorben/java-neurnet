package hu.akoel.n_neurnet.network;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.connectors.InnerConnector;
import hu.akoel.n_neurnet.connectors.InputConnector;
import hu.akoel.n_neurnet.connectors.OutputConnector;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.handlers.DataHandler;
import hu.akoel.n_neurnet.listeners.ICycleListener;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.resultiterator.IResultIterator;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import hu.akoel.n_neurnet.strategies.RandomResetWeightStrategy;

public class Network {
	private ICycleListener trainingCycleListener = null;
	private IResetWeightStrategy resetWeightStrategy = new RandomResetWeightStrategy();
	private ArrayList<Layer> layerList = new ArrayList<Layer>();	
	private double α = 0.3;
	private double β = 1;
	private int maxTrainCycle = 1;
	
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
	
	public void setMaxTrainCycle( int maxTrainCycle ){
		this.maxTrainCycle = maxTrainCycle;
	}
	
	public int getMaxTrainCycle(){
		return this.maxTrainCycle;
	}
	
	public void addLayer( Layer layer ){
		layerList.add( layer );
		hasBeenInitialized = false;
	}	

	public void setTrainingCycleListener( ICycleListener cycleListener ){
		this.trainingCycleListener = cycleListener;
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

	public void executeTraining( DataHandler trainingDataHandler, double maxTotalMeanSquareError ){
		executeTraining(true, trainingDataHandler, maxTotalMeanSquareError);
	}
	
	public void executeTraining( boolean needResetWeights, DataHandler trainingDataHandler, double maxTotalMeanSquareError ){
		
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
		for( int i = 0; i < maxTrainCycle; i++ ){
			
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
		}
	}
	
	public void executeTest( DataHandler testDataHandler ){
		
		//It returns if there was no Initialization
		if( !hasBeenInitialized ){
			return;
		}
		
		//Sets the necessary DataHandler
		setDataHandler( testDataHandler );
		
		//Resets tge test datas
		testDataHandler.reset();

System.out.println("Test result");			
		
		while( testDataHandler.hasNext() ){

			//Takes the next Training data
			testDataHandler.takeNext();		
		
			//--- Calculate Sigma
			inputConnector.calculateInputSigmas();
			for( InnerConnector innerConnector: innerConnectorList ){
				innerConnector.calculateInputSigmas();
			}
		
			Iterator<Neuron> neuronIterator = outputConnector.getOutputLayer().getNeuronIterator();
			while( neuronIterator.hasNext() ){
				Neuron actualNeuron = neuronIterator.next();
System.out.println("input: " + testDataHandler.getInput( actualNeuron.getIndex() ) + " expected value: " + testDataHandler.getExpectedOutput( actualNeuron.getIndex()) + " actual value: " + actualNeuron.getSigma() );
				
			}
		}
	}
}
