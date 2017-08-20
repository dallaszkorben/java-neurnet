package hu.akoel.neurnet.network;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.IInnerLayer;
import hu.akoel.neurnet.layer.IInputLayer;
import hu.akoel.neurnet.layer.ILayer;
import hu.akoel.neurnet.layer.IOutputLayer;
import hu.akoel.neurnet.neuron.IInputNeuron;
import hu.akoel.neurnet.neuron.INeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;
import hu.akoel.neurnet.strategies.RandomDefaultWeightStrategy;

public class Network {
	double α = 0.2; //Tanulasi rata
	double β = 0.3; //momentum
	
	IInputLayer inputLayer;
	IOutputLayer outputLayer;
	DefaultWeightStrategy defaultWeightStrategy = new RandomDefaultWeightStrategy();
	ArrayList<IInnerLayer> innerLayerList = new ArrayList<IInnerLayer>();

	public Network( IInputLayer inputLayer, IOutputLayer outputLayer ){
		this.inputLayer = inputLayer;
		this.outputLayer = outputLayer;	
		makeConnections();
	}
	
	//TODO meg kell szuntetni az eddigi kapcsolatokat
	public void addInnerLayer( IInnerLayer innerLayer ){
		this.innerLayerList.add( innerLayer );
		makeConnections();
	}

	/**
	 * set the previousLayers for the Layers
	 */
	public void makeConnections(){
		ILayer previousLayer = inputLayer;
		
		inputLayer.initializeNeurons(defaultWeightStrategy);
		for( IInnerLayer layer: innerLayerList ){
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
		for( int i = 0; i <= 10000000; i++ ){
			
			double squareError = 0;
			
			//In One period we train the all training data
			for( int j = 0; j < numberOfTestData; j++ ){
				
				double[] trainingInputArray = trainingInputList.get(j);
				double[] trainingOutputArray = trainingOutputList.get(j);
				
				//Load Inputs for Sigma calculation
				Iterator<INeuron> iterator = inputLayer.getIterator();
				int inputOrder = 0;
				while( iterator.hasNext() ){
					IInputNeuron neuron = (IInputNeuron)iterator.next();
					neuron.setInput( trainingInputArray[inputOrder]);					
					inputOrder++;
				}
				
				// --- 1. step ---
				// Sigma calculation by the NEW Input and the OLD Weight
				inputLayer.calculateSigmas();
				for( IInnerLayer layer: innerLayerList ){
					layer.calculateSigmas();
				}
				outputLayer.calculateSigmas();

				// --- 2. step ---
				// Weight calculation by the new Sigma
				// In case of calculation of Weights it is needed: 
				// -OutputLayer: expected Output
				// -InputLayer:  previous Layer
				outputLayer.calculateWeights(trainingOutputArray, α, β );
				ILayer nextLayer = outputLayer;				
				for( IInnerLayer layer: innerLayerList ){
					layer.calculateWeights(nextLayer, α, β);
					nextLayer = layer;
				}
				inputLayer.calculateWeights(nextLayer, α, β);
				
				// --- 3. step ---
				// Sigma calculation  by the NEW Input and the NEW Weight
				inputLayer.calculateSigmas();
				for( IInnerLayer layer: innerLayerList ){
					layer.calculateSigmas();
				}
				outputLayer.calculateSigmas();
				
				//Error Calculation
				squareError += outputLayer.getMeanSquareError(trainingOutputArray);
			}
			
			//
			// Total Mean Square Error Calculation
			//
			squareError /= numberOfTestData;
			if( i % 50000 == 0 ){
				System.err.println( "Total Mean Square Error: " + squareError );
			}
			
			if( squareError <= maxTotalMeanSquareError ){				
				break;
			}
		}
		
	}
}
