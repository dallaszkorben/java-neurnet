package hu.akoel.n_neurnet;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.activationfunctions.IActivationFunction;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.handlers.DataHandler;
import hu.akoel.n_neurnet.network.Network;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.resultcontainers.IResultContainer;
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
			
/*			myNetwork.setTrainingCycleListener( new ICycleListener() {				
				public void handlerError(int cycleCounter, double totalMeanSquareError) {
					if( cycleCounter % 10000 == 0 ){
						System.err.println( totalMeanSquareError);
					}						
				}
			});
*/			
			MyDataHandler myTrainingDataListener = new MyDataHandler(dataPairs[staticCycle][0], dataPairs[staticCycle][1]);
			
			myNetwork.setMaxTrainCycle( (int)dataPairs[staticCycle][4][0][0] );
			myNetwork.executeTraining(true, myTrainingDataListener, 0.00005);
			
			
			ArrayList<IResultContainer> weightArray = myNetwork.getResultContainer();
			Iterator<IResultContainer> wilIterator = weightArray.iterator();
			while( wilIterator.hasNext() ){				
				IResultContainer wil = wilIterator.next();
				
				System.err.println( "layer: " + wil.getInputLayer() );				
				
				wil.reset();
				while( wil.hasNextNeuron() ){
					
					Neuron neruron = wil.getNextNeuron();
					System.err.println("  Neuron: " + " idx: " + neruron.getIndex() );
					
					while( wil.hasNextWeight() ){
						
						System.err.println("    w: " + wil.getNextWeight() );
						
					}
					
				}
			}
		}
	}
	
	
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
