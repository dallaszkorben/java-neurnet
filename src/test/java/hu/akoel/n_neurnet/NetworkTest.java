package hu.akoel.n_neurnet;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.activationfunctions.IActivationFunction;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.listeners.ICycleListener;
import hu.akoel.n_neurnet.handlers.DataHandler;
import hu.akoel.n_neurnet.network.Network;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.resultiterator.IResultIterator;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
	
public class NetworkTest extends TestCase{ 

	public static int staticCycle;
	
	public NetworkTest( String testName ) {
		super( testName );
	}

	public static Test suite(){
		return new TestSuite( NetworkTest.class );
	}
	
	class MyActivationFunction_ActivationFunction implements IActivationFunction {
		private double summa;
		MyActivationFunction_ActivationFunction( double summa ) {
			this.summa = summa;
		}
		public double getSigma() {
			return summa;
		}			
		public double getDerivateSigmaBySum() {
			return 0;
		}
		public void calculateDetails(double summa) {
		}
	};
	
	class MyDataHandler extends DataHandler{
		double[][] input;
		double[][] output;
		int pointer = -1;

		public MyDataHandler(double[][] input, double[][] output){
			this.input = input;
			this.output = output;
		}
		
		public double getInput(int inputNeuronIndex) {
			return input[pointer][inputNeuronIndex];
		}

		public double getExpectedOutput(int outputNeuronIndex) {
			return output[pointer][outputNeuronIndex];
		}

		@Override
		public boolean hasNext() {
			if( pointer + 1 < input.length )
				return true;
			else
				return false;
		}

		@Override
		public void reset() {
			pointer = -1;
		}

		@Override
		public void takeNext() {
			pointer++;
		}

		@Override
		public double getSize() {			
			return input.length;
		}		
	}
	
	private Network getGenaralNeuron( final double[][][][] dataPairs ){
		int inputLayerSize = 1;
		int innerLayerSize = 2;
		int outputLayerSize = 1;
		
		final Layer inputLayer = new Layer();
		for( int i = 0; i < inputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			
			//set Delta for input neurons
			//neuron.setDelta( dataPairs[ staticCycle][1][i] );
			inputLayer.addNeuron(neuron);
		}

		Layer innerLayer = new Layer();
		for( int i = 0; i < innerLayerSize; i++ ){
			Neuron neuron = new Neuron();
			innerLayer.addNeuron(neuron);
		}

		Layer outputLayer = new Layer();
		for( int i = 0; i < outputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			outputLayer.addNeuron(neuron);
		}		
		
		Network myNetwork = new Network();
		myNetwork.addLayer(inputLayer);
		myNetwork.addLayer(innerLayer);
		myNetwork.addLayer(outputLayer);
		
		//Set initial Weights
		myNetwork.setResetWeightStrategy( new IResetWeightStrategy() {
			public double getWeight(Neuron outputNeuron, Neuron inputNeuron) {
				
				//1 layer
				if( null == outputNeuron ){
					return dataPairs[staticCycle][2][0][0];

				//1-2 layer
				}else if( inputLayer.getNeuronIndex(outputNeuron) >= 0 ){
					return dataPairs[staticCycle][2][1][inputNeuron.getIndex()];
					
				//2-3 layer
				}else{
					return dataPairs[staticCycle][2][2][outputNeuron.getIndex()];
				}					
			}
		});
		
		return myNetwork;
	}
	
	public void test_SettersGetters(){	
		final double[][][][] dataPairs = {
				//{I},      {O},    	 {w01 w10 w11 w20},       {cycle, α, β}
				{
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}}, 
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}},  
					{{1.0}, {2.0, 3.0}, {4.0, 5.0}}, 
					{{1000.0, 0.0, 0.0}, {253.0, 0.99, 0.0}, {253.0, 0.0, 0.99}, {253.0, 0.45, 0.32}} 
				},					
		};

		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Network myNetwork = getGenaralNeuron(dataPairs);
			
			for( int i = 0; i < dataPairs[staticCycle][3].length; i++){
				myNetwork.setMaxTrainCycle((int)dataPairs[staticCycle][3][i][0]);
				myNetwork.setLearningRate(dataPairs[staticCycle][3][i][1]);
				myNetwork.setMomentum(dataPairs[staticCycle][3][i][2]);
				
				assertEquals( (int)dataPairs[staticCycle][3][i][0], myNetwork.getMaxTrainCycle() );
				assertEquals( dataPairs[staticCycle][3][i][1], myNetwork.getLearningRate() );
				assertEquals( dataPairs[staticCycle][3][i][2], myNetwork.getMomentum() );
			}
		}		
	}
	
	public void test_executeTraining_Method(){	
		
		final double[][][][] inputDataPairs = {
				//{δi1, δi2, δi3}, {α, β}, {w1, w2, w3, w4, w5, w6, w7, w8, w9}, {σ1, σ2, σ3}
				//{I},      {O},    	 {w01 w10 w11 w20},       {cycle}
				{
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}}, 
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}},  
					{{1.0}, {2.0, 3.0}, {4.0, 5.0}}, 
					{{1001.0}}
				},					
		};
		final double[][][][] outputDataPairs = {
				//{expected w-s}
				{
					{{0.09955158924812021}}, {{0.6007897283660051}, {1.4952123897080412}}, {{-0.744372079095825,-0.547696937447136}},
										
				},					
		};		
		
		for( staticCycle = 0; staticCycle < inputDataPairs.length; staticCycle++ ){

			Network myNetwork = getGenaralNeuron(inputDataPairs);

			MyDataHandler myTrainingDataListener = new MyDataHandler(inputDataPairs[staticCycle][0], inputDataPairs[staticCycle][1]);
			
			myNetwork.setMaxTrainCycle( (int)inputDataPairs[staticCycle][3][0][0] );
			
			//--- that is under TEST ---
			myNetwork.executeTraining(true, myTrainingDataListener, 0.00005);
			
			int layerIndex = 0;
			ArrayList<IResultIterator> resultContainer = myNetwork.getResultIteratorArray();
			Iterator<IResultIterator> resultContainerIterator = resultContainer.iterator();
			while( resultContainerIterator.hasNext() ){				
				IResultIterator resultIterator = resultContainerIterator.next();
				
				//System.err.println( "layer: " + resultIterator.getInputLayer() );				
				
				resultIterator.reset();
				while( resultIterator.hasNextNeuron() ){
					
					resultIterator.getNextNeuron();
					//System.err.println("  Neuron: " + " idx: " + neruron.getIndex() );
					
					while( resultIterator.hasNextWeight() ){

						double weight = resultIterator.getNextWeight();
						assertEquals(outputDataPairs[staticCycle][layerIndex][resultIterator.getNeuronIndex()][resultIterator.getWeightIndex()], weight);
						//System.err.println("    w: " + resultIterator.getNextWeight() );
						
					}					
				}
				layerIndex++;
			}			
		}
	}
	
	
	public void test_TrainingCycleListener_Functionality(){	
		
		final double[][][][] inputDataPairs = {
				//{δi1, δi2, δi3}, {α, β}, {w1, w2, w3, w4, w5, w6, w7, w8, w9}, {σ1, σ2, σ3}
				//{I},      {O},    	 {w01 w10 w11 w20},       {cycle}
				{
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}}, 
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}},  
					{{1.0}, {2.0, 3.0}, {4.0, 5.0}}, 
					{{3.0}}
				},					
		};
		final double[][][][] outputDataPairs = {
				//{cycle, total meansquare}, {w20, w21}, {σ2}
				{
					{{0.0, 0.49870521626940145}, {}, {0.9994098801906562}}, //cycle 0
					{{1.0, 0.4987033180916924}, {}, {0.999408689304742}}, //cycle 1
					{{2.0, 0.498701412807827}, {}, {0.9994074938566105}}, //cycle 2

				},					
		};		

		for( staticCycle = 0; staticCycle < inputDataPairs.length; staticCycle++ ){

			Network myNetwork = getGenaralNeuron(inputDataPairs);

			MyDataHandler myTrainingDataListener = new MyDataHandler(inputDataPairs[staticCycle][0], inputDataPairs[staticCycle][1]);
			
			myNetwork.setMaxTrainCycle( (int)inputDataPairs[staticCycle][3][0][0] );

			//--- that is under TEST ---
			myNetwork.setTrainingCycleListener(new ICycleListener() {
				int cycle = 0;
				public void handlerError(int cycleCounter, double totalMeanSquareError,	ArrayList<IResultIterator> resultIteratorArray) {
					assertEquals((int)outputDataPairs[staticCycle][cycle][0][0], cycleCounter);
					assertEquals(outputDataPairs[staticCycle][cycle][0][1], totalMeanSquareError);
					
					IResultIterator resultIterator = resultIteratorArray.get(2);
					while( resultIterator.hasNextNeuron() ){
						Neuron neuron = resultIterator.getNextNeuron();
						assertEquals(outputDataPairs[staticCycle][cycle][2][neuron.getIndex()], neuron.getSigma());
					}
					cycle++;
				}
			});
			
			myNetwork.executeTraining(true, myTrainingDataListener, 0.00005);
		}
	}	
	
/*
public void test_calculateInputWeights_Method(){	
		
		final double[][][][] dataPairs = {
				//{δi1, δi2, δi3}, {α, β}, {w1, w2, w3, w4, w5, w6, w7, w8, w9}, {σ1, σ2, σ3}
				//{I},      {O},    	 {w01 w10 w11 w20},       {expected w-s}, {cycle}
				{
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}}, 
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}},  
					{{1.0}, {2.0, 3.0}, {4.0, 5.0}}, 
					{{0.09955158924812021}, {0.6007897283660051, 1.4952123897080412}, {-0.744372079095825,-0.547696937447136}},
					{{1000.0}}
				},
					
		};		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Network myNetwork = getGenaralNeuron(dataPairs);

			//MyDataHandler myTrainingDataListener = new MyDataHandler(dataPairs[staticCycle][0], dataPairs[staticCycle][1]);
			
			//myNetwork.setMaxTrainCycle( (int)dataPairs[staticCycle][4][0][0] );
			//myNetwork.executeTraining(true, myTrainingDataListener, 0.00005);
			
			myNetwork.initialize();
			
			ArrayList<IResultIterator> resultContainer = myNetwork.getResultContainer();
			Iterator<IResultIterator> resultContainerIterator = resultContainer.iterator();
			while( resultContainerIterator.hasNext() ){				
				IResultIterator resultIterator = resultContainerIterator.next();
				
				System.err.println( "layer: " + resultIterator.getInputLayer() );				
				
				resultIterator.reset();
				while( resultIterator.hasNextNeuron() ){
					
					Neuron neruron = resultIterator.getNextNeuron();
					System.err.println("  Neuron: " + " idx: " + neruron.getIndex() );
					
					while( resultIterator.hasNextWeight() ){
						
						System.err.println("    w: " + resultIterator.getNextWeight() );
						
					}
					
				}
			}
		}
	} 
 	
 	
 */
	
	
/*	public void sscalculateInputWeights_Method(){	
		
		final double[][][][] dataPairs = {
				//{δi1, δi2, δi3}, {α, β}, {w1, w2, w3, w4, w5, w6, w7, w8, w9}, {σ1, σ2, σ3}
				//{I}      {O}    	 {w},                             {expected w-s}
				{
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}}, 
					{{0.1}, {0.15}, {0.2}, {0.3}, {0.35}, {0.4}, {0.45}, {0.5}},  
					{{1.0}, {2.0, 3.0}, {4.0, 5.0}}, 
					{{1.0}, {1.0, 1.0}, {1.0}}}
		};		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Network myNetwork = getGenaralNeuron(dataPairs);
			
			myNetwork.setTrainingCycleListener( new ICycleListener() {				
				public void handlerError(int cycleCounter, double totalMeanSquareError) {
					if( cycleCounter % 10000 == 0 ){
						System.err.println( totalMeanSquareError);
					}						
				}
			});
			
			MyDataHandler myTrainingDataListener = new MyDataHandler(dataPairs[staticCycle][0], dataPairs[staticCycle][1]);
			
			myNetwork.setMaxTrainCycle( 50000 );
			myNetwork.executeTraining(true, myTrainingDataListener, 0.00005);

System.err.println();			
			
			myNetwork.setMaxTrainCycle( 50000 );
			myNetwork.executeTraining(false, myTrainingDataListener, 0.00005);

			
			myNetwork.executeTest(myTrainingDataListener);
								
//0.9994098801906562			
//0.4941285680594964			
			

		}
	}
	*/	
	
}
