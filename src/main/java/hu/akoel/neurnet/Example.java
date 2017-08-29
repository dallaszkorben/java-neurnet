package hu.akoel.neurnet;

import java.util.ArrayList;

import hu.akoel.neurnet.layer.InnerLayer;
import hu.akoel.neurnet.layer.InputLayer;
import hu.akoel.neurnet.layer.OutputLayer;
import hu.akoel.neurnet.listeners.ICycleListener;
import hu.akoel.neurnet.network.Network;
import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.InnerNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.neuron.OutputNeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;

public class Example {
/*   	double[][] trainingData = {

			//--- 1 ---
			{0.0, 0.0, 1.0},
			{0.0, 0.25, 1.0},
			{0.0, 0.5, 1.0},    			
			{0.0, 0.75, 1.0},
			{0.0, 1.0, 1.0},    			
			
			{0.25, 0.0, 1.0},
			{0.25, 0.25, 1.0},
			{0.25, 0.5, 1.0},    			
			{0.25, 0.75, 1.0},
			{0.25, 1.0, 1.0},

			{0.5, 0.0, 1.0},
			{0.5, 0.25, 1.0},
			{0.5, 0.5, 1.0},   			
			{0.5, 0.75, 1.0},
			{0.5, 1.0, 1.0},

			{0.75, 0.0, 1.0},
			{0.75, 0.25, 1.0},
			{0.75, 0.5, 1.0},    			
			{0.75, 0.75, 1.0},
			{0.75, 1.0, 1.0},

			{1.0, 0.0, 1.0},
			{1.0, 0.25, 1.0},
			{1.0, 0.5, 1.0},
			{1.0, 0.75, 1.0},
			{1.0, 1.0, 1.0},

			
			//--- 1 ---
			{1.25, 1.25, 2.0},
			{1.25, 1.5, 2.0},
			{1.25, 1.75, 2.0},
			{1.25, 2.0, 2.0},
			{1.25, 2.25, 2.0},
			{1.25, 2.5, 2.0},
			{1.25, 2.75, 2.0},
			{1.25, 3.0, 2.0},
			
			{1.5, 1.25, 2.0},
			{1.5, 1.5, 2.0},
			{1.5, 1.75, 2.0},
			{1.5, 2.0, 2.0},
			{1.5, 2.25, 2.0},
			{1.5, 2.5, 2.0},
			{1.5, 2.75, 2.0},
			{1.5, 3.0, 2.0},
			
			{1.75, 1.25, 2.0},
			{1.75, 1.5, 2.0},
			{1.75, 1.75, 2.0},
			{1.75, 2.0, 2.0},
			{1.75, 2.25, 2.0},
			{1.75, 2.5, 2.0},
			{1.75, 2.75, 2.0},
			{1.75, 3.0, 2.0},    			
			
			{2.0, 1.25, 2.0},
			{2.0, 1.5, 2.0},
			{2.0, 2.0, 2.0},
			{2.0, 2.25, 2.0},
			{2.0, 2.5, 2.0},
			{2.0, 2.75, 2.0},
			{2.0, 3.0, 2.0},
			
			{2.25, 1.25, 2.0},
			{2.25, 1.5, 2.0},
			{2.25, 1.75, 2.0},
			{2.25, 2.0, 2.0},
			{2.25, 2.25, 2.0},
			{2.25, 2.5, 2.0},
			{2.25, 2.75, 2.0},
			{2.25, 3.0, 2.0},
			
			{2.5, 1.25, 2.0},
			{2.5, 1.5, 2.0},
			{2.5, 1.75, 2.0},
			{2.5, 2.0, 2.0},
			{2.5, 2.25, 2.0},
			{2.5, 2.5, 2.0},
			{2.5, 2.75, 2.0},
			{2.5, 3.0, 2.0},
			
			{2.75, 1.25, 2.0},
			{2.75, 1.5, 2.0},
			{2.75, 1.75, 2.0},
			{2.75, 2.0, 2.0},
			{2.75, 2.25, 2.0},
			{2.75, 2.5, 2.0},
			{2.75, 2.75, 2.0},
			{2.75, 3.0, 2.0},
			
			{3.0, 3.0, 2.0},
			{3.0, 1.25, 2.0},
			{3.0, 1.5, 2.0},
			{3.0, 1.75, 2.0},
			{3.0, 2.0, 2.0},
			{3.0, 2.25, 2.0},
			{3.0, 1.5, 2.0},
			{3.0, 2.75, 2.0},
			{3.0, 1.25, 2.0},
			
			//--- 2 ---
			{3.25, 3.25, 1.0},
			{3.25, 3.5, 1.0},
			{3.25, 3.75, 1.0},
			{3.25, 4.0, 1.0},
			{3.25, 4.25, 1.0},
			{3.25, 4.75, 1.0},
			{3.25, 5.0, 1.0},
			
			{3.5, 3.25, 1.0},
			{3.5, 3.5, 1.0},
			{3.5, 3.75, 1.0},
			{3.5, 4.0, 1.0},
			{3.5, 4.25, 1.0},
			{3.5, 4.5, 1.0},
			{3.5, 4.75, 1.0},
			{3.5, 5.0, 1.0},
			
			{3.75, 3.25, 1.0},
			{3.75, 3.5, 1.0},
			{3.75, 3.75, 1.0},
			{3.75, 4.0, 1.0},
			{3.75, 4.25, 1.0},
			{3.75, 4.5, 1.0},
			{3.75, 4.75, 1.0},
			{3.75, 5.0, 1.0},
			
			{4.0, 3.25, 1.0},
			{4.0, 3.5, 1.0},
			{4.0, 3.75, 1.0},
			{4.0, 4.0, 1.0},
			{4.0, 4.25, 1.0},
			{4.0, 4.5, 1.0},
			{4.0, 4.75, 1.0},
			{4.0, 5.0, 1.0},
			
			{4.25, 3.25, 1.0},
			{4.25, 3.5, 1.0},
			{4.25, 3.75, 1.0},
			{4.25, 4.0, 1.0},
			{4.25, 4.25, 1.0},
			{4.25, 4.5, 1.0},
			{4.25, 4.75, 1.0},
			{4.25, 5.0, 1.0},

			{4.5, 3.25, 1.0},
			{4.5, 3.5, 1.0},
			{4.5, 3.75, 1.0},
			{4.5, 4.0, 1.0},
			{4.5, 4.25, 1.0},
			{4.5, 4.5, 1.0},
			{4.5, 4.75, 1.0},
			{4.5, 5.0, 1.0},

			{4.75, 3.25, 1.0},
			{4.75, 3.5, 1.0},
			{4.75, 3.75, 1.0},
			{4.75, 4.0, 1.0},
			{4.75, 4.25, 1.0},
			{4.75, 4.5, 1.0},
			{4.75, 4.75, 1.0},
			{4.75, 5.0, 1.0},
			
			{5.0, 3.25, 1.0},
			{5.0, 3.5, 1.0},
			{5.0, 3.75, 1.0},
			{5.0, 4.0, 1.0},
			{5.0, 4.25, 1.0},
			{5.0, 4.5, 1.0},
			{5.0, 4.75, 1.0},
			{5.0, 5.0, 1.0},
			
	};
*/
/*	double[][] trainingData = {
			{0.1, 0.4},
			{0.1, 0.4},
			{0.2, 0.3},    			
			{0.3, 0.2},
			{0.4, 0.1},
			{0.5, 0.0},    			 			
	};
*/
	double[][] trainingData = {
			{0.1, 0.1},
			{0.2, 0.1},    			
			{0.3, 0.1},
			{0.4, 0.1},
			{0.5, 0.1},    			 			
			{0.6, 0.2},
			{0.7, 0.2},
			{0.8, 0.2},
			{0.9, 0.2},
			{1.0, 0.2},
	};
	
	public Example() {
	
		int numberOfNeuronsInput = 2;
    	int numberOfNeuronsInner1 = 4;
    	int numberOfNeuronsInner2 = 4;
    	int numberOfNeuronsOutput = 1;

		//
    	//Input Layer
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeuronsInput; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    	}
		
    	//
    	//Inner Layer
    	InnerLayer innerLayer1 = new InnerLayer();    			
    	for( int i = 0; i < numberOfNeuronsInner1; i++ ){
    		InnerNeuron innerNeuron = new InnerNeuron();
    		innerLayer1.addNeuron(innerNeuron);
    	}    	

    	InnerLayer innerLayer2 = new InnerLayer();    			
    	for( int i = 0; i < numberOfNeuronsInner2; i++ ){
    		InnerNeuron innerNeuron = new InnerNeuron();
    		innerLayer2.addNeuron(innerNeuron);
    	}    	

    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
    	for( int i = 0; i < numberOfNeuronsOutput; i++ ){
    		OutputNeuron outputNeuron = new OutputNeuron();
    		outputLayer.addNeuron(outputNeuron);
    	}		
		
    	double[] trainingInput = new double[3];
    	double[] trainingOutput = new double[1];
    	ArrayList<double[]> trainingInputList = new ArrayList<double[]>();
    	ArrayList<double[]> trainingOutputList = new ArrayList<double[]>();
    	for( int i = 0; i < trainingData.length; i++ ){
    		trainingInput = new double[]{ trainingData[i][0], 1.0 };
    		trainingOutput = new double[]{ trainingData[i][1] };
        	trainingInputList.add(trainingInput);
        	trainingOutputList.add(trainingOutput);
    	}
		
		Network network = new Network(inputLayer, outputLayer);
		network.addInnerLayer(innerLayer1);
		//network.addInnerLayer(innerLayer2);
		network.setLearningRate( 0.7 );
		network.setMomentum( 0.5 );
		network.setMaxTrainCycle( 1000000000 );
		
		//network.setStartingWeightStrategy( new DefaultWeightStrategy() {
		//	public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
		//		return ( actualNeuron.getOrder() + 0.0 );
		//	}
		//} );
		network.setTrainingCycleListener( new ICycleListener() {			
			public void handlerError(int cycleCounter, double totalMeanSquareError) {
				if( cycleCounter % 10000 == 0 ){
					System.err.println( "i: " + cycleCounter + "    Err: " + totalMeanSquareError);
				}
			}
		});
		
		network.training(trainingInputList, trainingOutputList, 0.00026);
		
		double[] testInput = new double[]{0,1, 3.1};
		
		inputLayer.getNeuronList().get(0).setInput(testInput[0] );
		inputLayer.getNeuronList().get(1).setInput( 1.0 );
		//inputLayer.getNeuronList().get(1).setInput(testInput[1] / 10);
		//inputLayer.getNeuronList().get(1).setInput( 1.0 );
		
		inputLayer.calculateSigmas();
		innerLayer1.calculateSigmas();
		outputLayer.calculateSigmas();
		
		//System.out.println(inputLayer.toString());
		//System.out.println(innerLayer1.toString());;
		System.out.println(outputLayer.toString());		
		
		

	}
	
	

	public static void main(String[] args) {
		new Example();

	}
}
