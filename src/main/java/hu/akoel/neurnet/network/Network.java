package hu.akoel.neurnet.network;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.IInnerLayer;
import hu.akoel.neurnet.layer.IInputLayer;
import hu.akoel.neurnet.layer.ILayer;
import hu.akoel.neurnet.layer.IOutputLayer;
import hu.akoel.neurnet.neuron.IInputNeuron;
import hu.akoel.neurnet.neuron.INeuron;

public class Network {
	IInputLayer inputLayer;
	IOutputLayer outputLayer;
	ArrayList<IInnerLayer> innerLayerList = new ArrayList<IInnerLayer>();

	public Network( IInputLayer inputLayer, IOutputLayer outputLayer ){
		this.inputLayer = inputLayer;
		this.outputLayer = outputLayer;
	}
	
	//TODO meg kell szuntetni az eddigi kapcsolatokat
	public void addInnerLayer( IInnerLayer innerLayer ){
		this.innerLayerList.add( innerLayer );
	}

	/**
	 * set the previousLayers for the Layers
	 */
	public void makeConnections(){
		ILayer previousLayer = inputLayer;
		for( IInnerLayer layer: innerLayerList ){
			layer.setPreviousLayer(previousLayer);
			previousLayer = layer;
		}
		outputLayer.setPreviousLayer(previousLayer);
	}
	
	//TODO meg kell nezni, hogy a kapcsolat felepult-e (makeConnections())
	public void training( ArrayList<double[]> trainingInputList, ArrayList<double[]> trainingOutputList, double maxTotalMeanSquareError ){
		int numberOfTestData = Math.max( trainingInputList.size(), trainingOutputList.size() );
		
		//Initialize WEIGHTs with RANDOM values
		inputLayer.generateRandomWeights();
		for( IInnerLayer layer: innerLayerList ){
			layer.generateRandomWeights();
		}
		outputLayer.generateRandomWeights();
		
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
				
				// Sigma calculation by the NEW Input and the OLD Weight
				inputLayer.calculateSigmas();
				for( IInnerLayer layer: innerLayerList ){
					layer.calculateSigmas();
				}
				outputLayer.calculateSigmas();

				// Weight calculation by the new Sigma
				// In case of calculation of Weights it is needed: 
				// -OutputLayer: expected Output
				// -InputLayer:  previous Layer
				outputLayer.calculateWeights(trainingOutputArray);
				ILayer nextLayer = outputLayer;				
				for( IInnerLayer layer: innerLayerList ){
					layer.calculateWeights(nextLayer);
					nextLayer = layer;
				}
				inputLayer.calculateWeights(nextLayer);
				

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
			if( i % 10000 == 0 ){
				System.err.println( "Total Mean Square Error: " + squareError );
			}
			
			if( squareError <= maxTotalMeanSquareError ){
				break;
			}
		}
		
	}
}
