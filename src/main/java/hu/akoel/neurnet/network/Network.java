package hu.akoel.neurnet.network;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.ALayer;
import hu.akoel.neurnet.layer.InnerLayer;
import hu.akoel.neurnet.layer.InputLayer;
import hu.akoel.neurnet.layer.OutputLayer;
import hu.akoel.neurnet.listeners.ICycleListener;
import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;
import hu.akoel.neurnet.strategies.RandomDefaultWeightStrategy;

public class Network {
	public static final double defaultα = 0.2;
	public static final double defaultβ = 0.3;
	public static final int defaultMaxTrainCycle = 10000000;
	
	private double α = defaultα; //Tanulasi rata
	private double β = defaultβ; //momentum	
	private int maxTrainCycle = defaultMaxTrainCycle;
	
	private InputLayer inputLayer;
	private OutputLayer outputLayer;
	private DefaultWeightStrategy defaultWeightStrategy = new RandomDefaultWeightStrategy();
	private ArrayList<InnerLayer> innerLayerList = new ArrayList<InnerLayer>();
	private ICycleListener trainingCycleListener = null;
	
	public Network( InputLayer inputLayer, OutputLayer outputLayer ){
		this.inputLayer = inputLayer;
		this.outputLayer = outputLayer;	
		makeConnections();
	}
	
	public void addInnerLayer( InnerLayer innerLayer ){
		this.innerLayerList.add( innerLayer );
		makeConnections();
	}

	public void setLearningRate( double α ){
		this.α = α;
	}
	
	public void setMomentumCoefficient( double β ){
		this.β = β;
	}
	
	public void setMaxTrainCycle( int maxTrainCycle ){
		this.maxTrainCycle = maxTrainCycle;
	}
	
	public void setTrainingCycleListener( ICycleListener trainingCycleListener ){
		this.trainingCycleListener = trainingCycleListener;
	}
	
	/**
	 * set the previousLayers for the Layers
	 */
	public void makeConnections(){
		ALayer previousLayer = inputLayer;
		
		inputLayer.initializeNeurons(defaultWeightStrategy);
		for( InnerLayer layer: innerLayerList ){
			layer.initializeNeurons(previousLayer, defaultWeightStrategy);
			previousLayer = layer;
		}
		outputLayer.initializeNeurons(previousLayer, defaultWeightStrategy);
	}
	
	public void setStartingWeightStrategy( DefaultWeightStrategy startingWeightStrategy ){
		this.defaultWeightStrategy = startingWeightStrategy;
		makeConnections();
	}
	
	public void training( ArrayList<double[]> trainingInputList, ArrayList<double[]> trainingOutputList, double maxTotalMeanSquareError ){
		int numberOfTestData = Math.max( trainingInputList.size(), trainingOutputList.size() );
		
		//Run the training again and again until the error is less then a certain value
		for( int i = 0; i <= maxTrainCycle; i++ ){
			
			double squareError = 0;
			
			//In One period we train the all training data
			for( int j = 0; j < numberOfTestData; j++ ){
				
				double[] trainingInputArray = trainingInputList.get(j);
				double[] trainingOutputArray = trainingOutputList.get(j);
				
				//Load Inputs for Sigma calculation
				Iterator<ANeuron> iterator = inputLayer.getNeuronIterator();
				int inputOrder = 0;
				while( iterator.hasNext() ){
					InputNeuron neuron = (InputNeuron)iterator.next();
					neuron.setInput( trainingInputArray[inputOrder]);					
					inputOrder++;
				}
				
				// --- 1. step ---
				// Sigma calculation by the NEW Input and the OLD Weight
				inputLayer.calculateSigmas();
				for( InnerLayer layer: innerLayerList ){
					layer.calculateSigmas();
				}
				outputLayer.calculateSigmas();

				// --- 2. step ---
				// Weight calculation by the new Sigma
				// In case of calculation of Weights it is needed: 
				// -OutputLayer: expected Output
				// -InputLayer:  previous Layer
				outputLayer.calculateWeights(trainingOutputArray, α, β );
				ALayer nextLayer = outputLayer;				
				for( InnerLayer layer: innerLayerList ){
					layer.calculateWeights(nextLayer, α, β);
					nextLayer = layer;
				}
				inputLayer.calculateWeights(nextLayer, α, β);
				
				// --- 3. step ---
				// Sigma calculation  by the NEW Input and the NEW Weight
				inputLayer.calculateSigmas();
				for( InnerLayer layer: innerLayerList ){
					layer.calculateSigmas();
				}
				outputLayer.calculateSigmas();
				
				//Error Calculation
				squareError += outputLayer.getMeanSquareError(trainingOutputArray);
			}
		
			squareError /= numberOfTestData;
			
			if( null != trainingCycleListener )
				trainingCycleListener.handlerError( i, squareError);
		
			if( squareError <= maxTotalMeanSquareError ){				
				break;
			}
		}
		
	}
}
