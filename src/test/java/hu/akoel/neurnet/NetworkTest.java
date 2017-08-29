package hu.akoel.neurnet;



import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.InnerLayer;
import hu.akoel.neurnet.layer.InputLayer;
import hu.akoel.neurnet.layer.OutputLayer;
import hu.akoel.neurnet.listeners.ICycleListener;
import hu.akoel.neurnet.network.Network;
import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.InnerNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.neuron.NeuronWeights;
import hu.akoel.neurnet.neuron.OutputNeuron;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

	
public class NetworkTest extends TestCase{ 

	public static int actualCycle;
	
	public NetworkTest( String testName ) {
		super( testName );
	}

	public static Test suite(){
		return new TestSuite( NetworkTest.class );
	}
	
	public void test_Check_GettersSetters(){
		final double[][] dataPairs = {
    			//α,  β}
    			{0.0, 0.0, 1.0},
    			{0.0, 1.0, 2.0},
    			{1.0, 0.0, 3.0},
    			{1.0, 1.0, 4.0},
    			{0.234, 0.00156, 5.0},
    			{0.00156, 0.234, 6.0},
    	};		
				
    	//Input Layer
    	InputLayer inputLayer = new InputLayer();    			
   		InputNeuron inputNeuron = new InputNeuron();
   		inputLayer.addNeuron(inputNeuron);
		
    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
   		OutputNeuron outputNeuron = new OutputNeuron();
   		outputLayer.addNeuron(outputNeuron);
   		
    	//These section what I test (connections are OK or not)
    	Network network = new Network(inputLayer, outputLayer);
    	
    	for( int i = 0; i < dataPairs.length; i++ ){
    		network.setLearningRate(dataPairs[i][0]);
    		assertEquals(dataPairs[i][0], network.getLearningRate());

    		network.setMomentum(dataPairs[i][1]);
    		assertEquals(dataPairs[i][1], network.getMomentum());
    		
    		network.setMaxTrainCycle((int)dataPairs[i][2]);
    		assertEquals((int)dataPairs[i][2], network.getMaxTrainCycle());
    	}
    	
	}	    
	
	public void test_Check_Connections(){
    	int numberOfNeurons = 100;

    	//DefaultWeightStrategy
    	DefaultWeightStrategy dws = new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return ( actualNeuron.getOrder() + 0.0 );
			}
		};

    	
		//
    	//Input Layer
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    	}
		
    	//
    	//Inner Layer
    	InnerLayer innerLayer = new InnerLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InnerNeuron innerNeuron = new InnerNeuron();
    		innerLayer.addNeuron(innerNeuron);
    	}    	
    	
    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		OutputNeuron outputNeuron = new OutputNeuron();
    		outputLayer.addNeuron(outputNeuron);
    		//outputNeuron.setSigma( (i) );
    	}

    	//These section what I test (connections are OK or not)
    	Network network = new Network(inputLayer, outputLayer);
    	network.setStartingWeightStrategy( dws );
    	network.addInnerLayer(innerLayer);
    	
    	//Check the order of the layers
    	assertEquals( 0, (int)inputLayer.getOrderOfLayer() );
    	assertEquals( 1, (int)innerLayer.getOrderOfLayer() );
    	assertEquals( 2, (int)outputLayer.getOrderOfLayer() );
    	
    	//Check the connections (start weights
    	//inputLayer
    	Iterator<InputNeuron> inputIt = inputLayer.getNeuronIterator();
    	while( inputIt.hasNext() ){
    		InputNeuron actualNeuron = inputIt.next();
    		assertEquals( actualNeuron.getOrder() + 0.0, actualNeuron.getWeight() );
    	}
    	
    	//InnerLayer
    	Iterator<InnerNeuron> innerIt = innerLayer.getNeuronIterator();
    	while( innerIt.hasNext() ){
    		InnerNeuron actualNeuron = innerIt.next();    		
    		for( int i = 0; i < inputLayer.getNumberOfNeurons(); i++ ){
    			NeuronWeights neuronWeights = actualNeuron.getNeuronValues(i);
    			assertEquals(  actualNeuron.getOrder() + 0.0, neuronWeights.getW_t() );
    			assertEquals( inputLayer.getNeuronList().get(i), neuronWeights.getPreviousNeuron() );
    		}
    	}
    	
    	//OutputLayer
    	Iterator<OutputNeuron> outputIt = outputLayer.getNeuronIterator();
    	while( outputIt.hasNext() ){
    		OutputNeuron actualNeuron = outputIt.next();    		
    		for( int i = 0; i < innerLayer.getNumberOfNeurons(); i++ ){
    			NeuronWeights neuronWeights = actualNeuron.getNeuronValues(i);
    			assertEquals(  actualNeuron.getOrder() + 0.0, neuronWeights.getW_t() );
    			assertEquals( innerLayer.getNeuronList().get(i), neuronWeights.getPreviousNeuron() );
    		}
    	}    	
    }    
	
	public void test_Training_Method(){
/*	   	double[][] trainingData = {
    			{0.0, 0.0},
    			{0.1, 1.0},
    			{0.2, 2.0},    			
    			{0.3, 3.0},
    			{0.4, 4.0},
    			{0.5, 5.0},
    			{0.6, 6.0},    			
    			{0.7, 7.0},
    			{0.8, 8.0},    			
    			{0.9, 9.0},    			
    			{1.0, 10.0},    			
    	};
*/	   	double[][] trainingData = {
    			{0.1, 0.4},
    			{0.1, 0.4},
    			{0.15, 0.35},
    			{0.2, 0.3},  
    			//{0.25, 0.25},
    			{0.3, 0.2},
    			{0.35, 0.15},
    			{0.4, 0.1},
    			{0.45, 0.05},
    			{0.5, 0.0},    			 			
    	};

		int numberOfNeuronsInput = 2;
    	int numberOfNeuronsInner = 3;
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
    	for( int i = 0; i < numberOfNeuronsInner; i++ ){
    		InnerNeuron innerNeuron = new InnerNeuron();
    		innerLayer1.addNeuron(innerNeuron);
    	}    	

    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
    	for( int i = 0; i < numberOfNeuronsOutput; i++ ){
    		OutputNeuron outputNeuron = new OutputNeuron();
    		outputLayer.addNeuron(outputNeuron);
    	}

    	//These section what I test (connections are OK or not)
    	Network network = new Network(inputLayer, outputLayer);
    	network.addInnerLayer(innerLayer1);
    	network.setLearningRate( 0.3 );
		network.setMomentum( 0.5 );
    	
    	network.setStartingWeightStrategy( new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return ( actualNeuron.getOrder() + 0.0 );
			}
		} );
    	network.setTrainingCycleListener( new ICycleListener() {			
			public void handlerError(int cycleCounter, double totalMeanSquareError) {
				//if( cycleCounter % 10000 == 0 ){
					//System.err.println( "i: " + cycleCounter + "   Err: " + totalMeanSquareError);
				//}
				NetworkTest.actualCycle = cycleCounter;
			}
		});	    	
    	
    	double[] trainingInput = new double[2];
    	double[] trainingOutput = new double[1];
    	ArrayList<double[]> trainingInputList = new ArrayList<double[]>();
    	ArrayList<double[]> trainingOutputList = new ArrayList<double[]>();
    	for( int i = 0; i < trainingData.length; i++ ){
    		trainingInput = new double[]{ trainingData[i][0], 1.0 };
    		trainingOutput = new double[]{ trainingData[i][1] };
        	trainingInputList.add(trainingInput);
        	trainingOutputList.add(trainingOutput);
    	}
    	

    	double maxTotalMeanSquereError = 0.0004;
    	int trainingCycle = 1;
    	//
    	// Test if the trainingCycle < maxTotalMeanSquareError
    	//
    	trainingCycle = 1;
    	network.setMaxTrainCycle( trainingCycle );
    	network.training(trainingInputList, trainingOutputList, maxTotalMeanSquereError);
    	assertEquals(trainingCycle, NetworkTest.actualCycle);
    	
    	//
    	// Test if the trainingCycle > maxTotalMeanSquareError
    	//
    	trainingCycle = 10000000;
    	int expectedCycle = 49365;
    	network.setMaxTrainCycle( trainingCycle );
    	network.training(trainingInputList, trainingOutputList, maxTotalMeanSquereError);
    	assertEquals(expectedCycle, NetworkTest.actualCycle);
    	
    	//
    	// Test if the output is OK for an test data
    	//
    	double testInput = 0.25;
    	double expectedOutput = 0.23211085166858758;
    	
    	inputLayer.getNeuronList().get(0).setInput(testInput);
		inputLayer.getNeuronList().get(1).setInput(1.0);
    	inputLayer.calculateSigmas();
    	innerLayer1.calculateSigmas();
		outputLayer.calculateSigmas();
    	assertEquals( expectedOutput, outputLayer.getNeuronList().get(0).getStoredSigma());
    	
	}
		
}
