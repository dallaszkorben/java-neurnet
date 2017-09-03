package hu.akoel.n_neurnet;

import hu.akoel.n_neurnet.activationfunctions.IActivationFunction;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.listeners.DataListener;
import hu.akoel.n_neurnet.listeners.ICycleListener;
import hu.akoel.n_neurnet.listeners.InputListener;
import hu.akoel.n_neurnet.listeners.OutputListener;
import hu.akoel.n_neurnet.network.Network;
import hu.akoel.n_neurnet.neuron.Neuron;
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
	
	class MyInputListener implements InputListener {
		private double[] inputValues;			
		public void setInput( double[] inputValues ){
			this.inputValues = inputValues;
		}
		public double getInput(int inputNeuronIndex) {
			return inputValues[ inputNeuronIndex];
		}
	};
	
	class MyOutputListener implements OutputListener {
		private double[] expectedOutputValues;			
		public void setExpectedOutputValues( double[] expecedOutputValues ){
			this.expectedOutputValues = expecedOutputValues;
		}
		public double getExpectedOutput(int outputNeuronIndex) {
			return expectedOutputValues[ outputNeuronIndex];
		}
	};

	class MyDataListener extends DataListener{
		double[][] input;
		double[][] output;
		int pointer = -1;

		public MyDataListener(double[][] input, double[][] output){
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
	
	public void test_calculateInputWeights_Method(){
		int inputLayerSize = 1;
		int innerLayerSize = 2;
		int outputLayerSize = 1;
		
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
				
				//set Sigma for output neurons
				//MyActivationFunction_ActivationFunction myActivationFunction = new MyActivationFunction_ActivationFunction(dataPairs[staticCycle][0][i] );
				//neuron.setActivationFunction(myActivationFunction);
				innerLayer.addNeuron(neuron);
			}

			Layer outputLayer = new Layer();
			for( int i = 0; i < outputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				//set Sigma for output neurons
				//MyActivationFunction_ActivationFunction myActivationFunction = new MyActivationFunction_ActivationFunction(dataPairs[staticCycle][0][i] );
				//neuron.setActivationFunction(myActivationFunction);
				outputLayer.addNeuron(neuron);
			}

			MyDataListener myTrainingDataListener = new MyDataListener(dataPairs[staticCycle][0], dataPairs[staticCycle][1]);
			
			Network myNetwork = new Network( myTrainingDataListener );
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
			
			myNetwork.setTrainingCycleListener( new ICycleListener() {				
				public void handlerError(int cycleCounter, double totalMeanSquareError) {
					if( cycleCounter % 10000 == 0 ){
						System.err.println( totalMeanSquareError);
					}						
				}
			});
			
			myNetwork.setMaxTrainCycle( 10000000 );
			myNetwork.initialize();
			myNetwork.executeTraining(0.00001);
								
//0.9994098801906562			
//0.4941285680594964			
			

		}
	}
	
	
}
