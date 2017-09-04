package hu.akoel.n_neurnet;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.activationfunctions.IActivationFunction;
import hu.akoel.n_neurnet.connectors.InputConnector;
import hu.akoel.n_neurnet.handlers.InputDataHandler;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.Assert.assertNotEquals;
	
public class InputConnectorTest extends TestCase{ 

	public static int staticCycle;
	
	public InputConnectorTest( String testName ) {
		super( testName );
	}

	public static Test suite(){
		return new TestSuite( InputConnectorTest.class );
	}

	class ForDefineOutputValue_ActivationFunction implements IActivationFunction {
		private double summa;
		ForDefineOutputValue_ActivationFunction( double summa ) {
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
	
	class MyInputDataHandler implements InputDataHandler {
		private double[] inputValues;			
		public void setInput( double[] inputValues ){
			this.inputValues = inputValues;
		}
		public double getInput(int inputNeuronIndex) {
			return inputValues[ inputNeuronIndex];
		}
	};
	
	public void test_getWeight_Method(){
		int inputLayerSize = 10;
		
		final Double[][] dataPairs = {
    			{0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, 
    			{1000.0, 1001.0, 1002.0, 1003.0, 1004.0, 1005.0, 1006.0, 1007.0, 1008.0, 1009.0}, 
    			{2000.0, 2001.0, 2002.0, 2003.0, 2004.0, 2005.0, 2006.0, 2007.0, 2008.0, 2009.0}, 
    			{3000.0, 3001.0, 3002.0, 3003.0, 3004.0, 3005.0, 3006.0, 3007.0, 3008.0, 3009.0}, 
    			{4000.0, 4001.0, 4002.0, 4003.0, 4004.0, 4005.0, 4006.0, 4007.0, 4008.0, 4009.0}, 
    			{5000.0, 5001.0, 5002.0, 5003.0, 5004.0, 5005.0, 5006.0, 5007.0, 5008.0, 5009.0}, 
    			{6000.0, 6001.0, 6002.0, 6003.0, 6004.0, 6005.0, 6006.0, 6007.0, 6008.0, 6009.0}, 
		};
		
		Layer inputLayer = new Layer();
		for( int i = 0; i < inputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			inputLayer.addNeuron(neuron);
		}

		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle ++ ){		

			InputConnector inputConnector = new InputConnector( inputLayer );
			inputConnector.setInputDataHandler( new MyInputDataHandler() );
			inputConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(Neuron outputNeuron, Neuron inputNeuron) {
					return dataPairs[ staticCycle ][ inputNeuron.getIndex() ];				}
			});

			inputConnector.resetWeights();

			for( int j = 0; j < inputLayerSize; j++ ){	
				
				//--- that is under TEST ---
				double weight = inputConnector.getInputWeight(j);
				assertEquals(dataPairs[staticCycle][j], weight);
			}
		}
	}
	
	public void test_resetWeights_Method(){
		int inputLayerSize = 10;
		
		Layer inputLayer = new Layer();
		for( int i = 0; i < inputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			inputLayer.addNeuron(neuron);
		}

		InputConnector inputConnector = new InputConnector( inputLayer );
		inputConnector. setInputDataHandler( new MyInputDataHandler() );
		
		ArrayList<Double> weights = new ArrayList<Double>();
		for( int j = 0; j < inputLayerSize; j++ ){	
			double weight = inputConnector.getInputWeight(j);
			weights.add( weight );
		}
		
		//--- that is under TEST ---
		inputConnector.resetWeights();
		
		int k = 0;
		for( int j = 0; j < inputLayerSize; j++ ){	
			double weight = inputConnector.getInputWeight(j);
			assertNotEquals(weights.get(k), weight);
			k++;
		}	
	}	
	
	public void test_getInnerSumma_Method(){
		int inputLayerSize = 3;
		
		final Double[][][] dataPairs = {
				//{I1, I2, I3}, {w1, w2, w3}, {summa1, summa2, summa3}
    			{{0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}}, 
    			{{1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}}, 
    			{{0.3, 0.5, 0.7}, {1.0, 1.0, 1.0}, {0.3, 0.5, 0.7}}, 
    			{{1.0, 1.0, 1.0}, {0.3, 0.5, 0.7}, {0.3, 0.5, 0.7}},
    			{{0.3, 0.5, 0.8}, {11.0, 13.0, 16.0}, {3.3, 6.5, 12.8 }},
		};
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				inputLayer.addNeuron(neuron);
			}

			MyInputDataHandler inputDataHandler = new MyInputDataHandler();
			inputDataHandler.setInput( new double[]{dataPairs[staticCycle][0][0], dataPairs[staticCycle][0][1], dataPairs[staticCycle][0][2]} );
			
			InputConnector inputConnector = new InputConnector( inputLayer );
			inputConnector.setInputDataHandler( inputDataHandler );
			inputConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(Neuron outputNeuron, Neuron inputNeuron) {
					return dataPairs[staticCycle][1][inputNeuron.getIndex()];				}
			});
			inputConnector.resetWeights();
							
			Iterator<Neuron> inputNeuronIterator = inputLayer.getNeuronIterator();
			while( inputNeuronIterator.hasNext() ){				
				Neuron inputNeuron = inputNeuronIterator.next();
				
				//--- that is under TEST ---
				double summa = inputConnector.getInputSumma( inputNeuron.getIndex() );
				assertEquals( dataPairs[staticCycle][2][inputNeuron.getIndex()], summa );
			}
			
/*			for(int i = 0; i < outputLayerSize; i++ ){
				for( int j = 0; j < inputLayerSize; j++ ){			
					double weight = innerConnector.getWeight(i, j);
					System.err.print(weight + ", ");
				}
				System.err.println();
			}
*/			
		}
	}

	public void test_calculateInputSigmas_Method(){
		int inputLayerSize = 3;
		
		final Double[][][] dataPairs = {
				//{I1, I2, I3}, {w1, w2, w3}, {σ1, σ2, σ3}
    			{{0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}, {0.5, 0.5, 0.5}}, 
    			{{1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}, {0.5, 0.5, 0.5}}, 
    			{{0.3, 0.5, 0.7}, {1.0, 1.0, 1.0}, {0.574442516811659, 0.6224593312018546, 0.668187772168166}}, 
    			{{1.0, 1.0, 1.0}, {0.3, 0.5, 0.7}, {0.574442516811659, 0.6224593312018546, 0.668187772168166}},
    			{{0.3, 0.5, 0.7}, {0.7, 0.5, 0.11}, {0.5523079095743253, 0.5621765008857981, 0.5192404945315857}},
    			{{0.15, 0.91, 0.05}, {2.31, 1.61, 2.30}, {0.5857685815180886, 0.8123114674413744, 0.5287183569514396}},
		};		

		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				inputLayer.addNeuron(neuron);
			}
			
			MyInputDataHandler inputDataHandler = new MyInputDataHandler();
			inputDataHandler.setInput( new double[]{dataPairs[staticCycle][0][0], dataPairs[staticCycle][0][1], dataPairs[staticCycle][0][2]} );
			InputConnector inputConnector = new InputConnector( inputLayer );
			inputConnector.setInputDataHandler( inputDataHandler );
			inputConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(Neuron outputNeuron, Neuron inputNeuron) {
					return dataPairs[staticCycle][1][inputNeuron.getIndex()];				}
			});
			inputConnector.resetWeights();

			//--- that is under TEST ---
			inputConnector.calculateInputSigmas();
			
			Iterator<Neuron> inputNeuronIterator = inputLayer.getNeuronIterator();
			int i = 0;
			while( inputNeuronIterator.hasNext() ){				
				Neuron inputNeuron = inputNeuronIterator.next();
				assertEquals( dataPairs[staticCycle][2][i], inputNeuron.getSigma() );
				i++;
			}			
		}
	}
	
	public void test_calculateInputWeight_Method(){
		int inputLayerSize = 3;
		
		final double[][][] dataPairs = {
				//{I1, I2, I3}, {δi1, δi2, δi3}, {α, β}, {w1, w2, w3,}
				{{0.3, 0.5, 0.7},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0}, {1.30, 1.50, 1.70}}, 
				{{0.3, 0.5, 0.7},  {0.5, 0.5, 0.5}, {1.0, 0.0}, {1.0, 1.0, 1.0}, {1.15, 1.25, 1.35}},
				{{0.6, 1.0, 1.4},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0}, {1.60, 2.00, 2.40}},
				{{0.3, 0.5, 0.7},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {2.0, 3.0, 4.0}, {2.30, 3.50, 4.70}}, 
				{{0.3, 0.5, 0.8},  {5.0, 6.0, 8.0}, {1.0, 0.0}, {2.0, 3.0, 4.0}, {3.50, 6.00, 10.40}}, 
		};		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				//set Delta for input neurons
				neuron.setDelta( dataPairs[ staticCycle][1][i] );
				inputLayer.addNeuron(neuron);
			}

			MyInputDataHandler inputDataHandler = new MyInputDataHandler();
			inputDataHandler.setInput( new double[]{dataPairs[staticCycle][0][0], dataPairs[staticCycle][0][1], dataPairs[staticCycle][0][2]} );
			InputConnector inputConnector = new InputConnector( inputLayer );
			inputConnector.setInputDataHandler(inputDataHandler);

			inputConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(Neuron outputNeuron, Neuron inputNeuron) {
					return dataPairs[staticCycle][3][inputNeuron.getIndex()];
				}
			});
			inputConnector.resetWeights();
						
			for( int i = 0; i < inputLayerSize; i++ ){
				//--- that is under TEST ---
				inputConnector.calculateInputWeight(i, dataPairs[staticCycle][2][0], dataPairs[staticCycle][2][1]);
			}
		
			for( int j = 0; j < inputLayerSize; j++ ){
				assertEquals( dataPairs[staticCycle][4][j], inputConnector.getInputWeight(j) );
			}						
		}
	}	
	
	public void test_calculateInputWeights_Method(){
		int inputLayerSize = 3;
		
		final double[][][] dataPairs = {
				//{I1, I2, I3}, {δi1, δi2, δi3}, {α, β}, {w1, w2, w3,}
				{{0.3, 0.5, 0.7},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0}, {1.30, 1.50, 1.70}}, 
				{{0.3, 0.5, 0.7},  {0.5, 0.5, 0.5}, {1.0, 0.0}, {1.0, 1.0, 1.0}, {1.15, 1.25, 1.35}},
				{{0.6, 1.0, 1.4},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0}, {1.60, 2.00, 2.40}},
				{{0.3, 0.5, 0.7},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {2.0, 3.0, 4.0}, {2.30, 3.50, 4.70}}, 
				{{0.3, 0.5, 0.8},  {5.0, 6.0, 8.0}, {1.0, 0.0}, {2.0, 3.0, 4.0}, {3.50, 6.00, 10.40}}, 
		};		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				//set Delta for input neurons
				neuron.setDelta( dataPairs[ staticCycle][1][i] );
				inputLayer.addNeuron(neuron);
			}

			MyInputDataHandler inputDataHandler = new MyInputDataHandler();
			inputDataHandler.setInput( new double[]{dataPairs[staticCycle][0][0], dataPairs[staticCycle][0][1], dataPairs[staticCycle][0][2]} );
			InputConnector inputConnector = new InputConnector( inputLayer );
			inputConnector.setInputDataHandler(inputDataHandler);
	
			inputConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(Neuron outputNeuron, Neuron inputNeuron) {
					return dataPairs[staticCycle][3][inputNeuron.getIndex()];
				}
			});
			inputConnector.resetWeights();
						
			//--- that is under TEST ---
			inputConnector.calculateInputWeights( dataPairs[staticCycle][2][0], dataPairs[staticCycle][2][1] );
		
			for( int j = 0; j < inputLayerSize; j++ ){
				assertEquals( dataPairs[staticCycle][4][j], inputConnector.getInputWeight(j) );
			}
		}
	}	
}
