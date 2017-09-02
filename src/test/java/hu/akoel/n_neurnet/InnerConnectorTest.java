package hu.akoel.n_neurnet;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.n_neurnet.activationfunctions.IActivationFunction;
import hu.akoel.n_neurnet.connectors.InnerConnector;
import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;
import hu.akoel.n_neurnet.strategies.IResetWeightStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.*;
//import static org.hamcrest.CoreMatchers.*;
	
public class InnerConnectorTest extends TestCase{ 

	public static int staticCycle;
	
	public InnerConnectorTest( String testName ) {
		super( testName );
	}

	public static Test suite(){
		return new TestSuite( InputConnectorTest.class );
	}

	public void test_getWeight_Method_And_ResetWeightStrategy(){
		int inputLayerSize = 10;
		int outputLayerSize = 7;
		
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
		
		Layer outputLayer = new Layer();
		for( int i = 0; i < outputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			outputLayer.addNeuron(neuron);
		}

		InnerConnector innerConnector = new InnerConnector( outputLayer, inputLayer );
		innerConnector.setResetWeightStrategy( new IResetWeightStrategy() {
			public double getWeight(int outputNeuronOrder, int inputNeuronOrder) {
				return dataPairs[outputNeuronOrder][inputNeuronOrder];
			}
		});

		innerConnector.resetWeights();

		for(int i = 0; i < outputLayerSize; i++ ){
			for( int j = 0; j < inputLayerSize; j++ ){	
				
				//--- that is under TEST ---
				double weight = innerConnector.getInputWeight(i, j);
				assertEquals(dataPairs[i][j], weight);
			}			
		}
	}
	
	public void test_resetWeights_Method(){
		int inputLayerSize = 10;
		int outputLayerSize = 7;
		
		Layer inputLayer = new Layer();
		for( int i = 0; i < inputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			inputLayer.addNeuron(neuron);
		}
		
		Layer outputLayer = new Layer();
		for( int i = 0; i < outputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			outputLayer.addNeuron(neuron);
		}

		InnerConnector innerConnector = new InnerConnector( outputLayer, inputLayer );

		ArrayList<Double> weights = new ArrayList<Double>();
		for(int i = 0; i < outputLayerSize; i++ ){
			for( int j = 0; j < inputLayerSize; j++ ){	
				double weight = innerConnector.getInputWeight(i, j);
				weights.add( weight );
			}			
		}
		
		//--- that is under TEST ---
		innerConnector.resetWeights();
		
		int k = 0;
		for(int i = 0; i < outputLayerSize; i++ ){
			for( int j = 0; j < inputLayerSize; j++ ){	
				double weight = innerConnector.getInputWeight(i, j);
				assertNotEquals(weights.get(k), weight);
				k++;
			}			
		}	
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
	
	public void test_getInnerSumma_Method(){
		int inputLayerSize = 3;
		int outputLayerSize = 3;
		
		final Double[][] dataPairs = {
				//σ1, σ2, σ3, w1, w2, w3, summa
    			{0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0}, 
    			{1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0}, 
    			{0.3, 0.5, 0.7, 1.0, 1.0, 1.0, 1.5}, 
    			{1.0, 1.0, 1.0, 0.3, 0.5, 0.7, 1.5},
    			{0.3, 0.5, 0.7, 0.7, 0.5, 0.3, 0.6699999999999999},
		};

		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer outputLayer = new Layer();
			for( int i = 0; i < outputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				ForDefineOutputValue_ActivationFunction myActivationFunction = new ForDefineOutputValue_ActivationFunction(dataPairs[staticCycle][i] );
				neuron.setActivationFunction(myActivationFunction);
				outputLayer.addNeuron(neuron);
			}

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				inputLayer.addNeuron(neuron);
			}
			
			InnerConnector innerConnector = new InnerConnector( outputLayer, inputLayer );
			innerConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(int outputNeuronOrder, int inputNeuronOrder) {
					return dataPairs[staticCycle][outputNeuronOrder + 3];
				}
			});
			innerConnector.resetWeights();
							
			Iterator<Neuron> inputNeuronIterator = inputLayer.getNeuronIterator();
			while( inputNeuronIterator.hasNext() ){				
				Neuron inputNeuron = inputNeuronIterator.next();
				
				//--- that is under TEST ---
				double summa = innerConnector.getInputSumma( inputNeuron.getIndex() );
				assertEquals( dataPairs[staticCycle][6], summa );
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
		int outputLayerSize = 3;
		
		final Double[][][] dataPairs = {
				//{σo1, σo2, σo3}, {w1, w2, w3, w4, w5, w6, w7, w8, w9}, {σ1, σ2, σ3}
    			{{0.0, 0.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {0.5, 0.5, 0.5}}, 
    			{{1.0, 1.0, 1.0}, {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}, {0.5, 0.5, 0.5}}, 
    			{{0.3, 0.5, 0.7}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {0.8175744761936437, 0.8175744761936437, 0.8175744761936437}}, 
    			{{1.0, 1.0, 1.0}, {0.3, 0.5, 0.7, 0.3, 0.5, 0.7, 0.3, 0.5, 0.7}, {0.8175744761936437, 0.8175744761936437, 0.8175744761936437}},
    			{{0.3, 0.5, 0.7}, {0.7, 0.5, 0.3, 0.7, 0.5, 0.3, 0.7, 0.5, 0.3}, {0.6615031592029523, 0.6615031592029523, 0.6615031592029523}},
				{{0.3, 0.5, 0.7}, {0.7, 0.5, 0.3, 0.7, 0.5, 0.3, 0.7, 0.5, 0.3}, {0.6615031592029523, 0.6615031592029523, 0.6615031592029523}},
    			{{0.15, 0.91, 0.05}, {2.31, 1.61, 2.30, 0.76, 0.40, 0.16, 0.01, 0.04, 0.07}, {0.8728726134955462, 0.6191636828993606, 0.5103485219628292}},
		};		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer outputLayer = new Layer();
			for( int i = 0; i < outputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				ForDefineOutputValue_ActivationFunction myActivationFunction = new ForDefineOutputValue_ActivationFunction(dataPairs[staticCycle][0][i] );
				neuron.setActivationFunction(myActivationFunction);
				outputLayer.addNeuron(neuron);
			}

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				inputLayer.addNeuron(neuron);
			}
			
			InnerConnector innerConnector = new InnerConnector( outputLayer, inputLayer );
			innerConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(int outputNeuronOrder, int inputNeuronIndex) {
					return dataPairs[staticCycle][1][outputNeuronOrder + inputNeuronIndex * 3];
				}
			});
			innerConnector.resetWeights();

			//--- that is under TEST ---
			innerConnector.calculateInputSigmas();
			
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
		int outputLayerSize = 3;
		
		final double[][][] dataPairs = {
				//{σo1, σo2, σo3}, {δi1, δi2, δi3}, {α, β}, {w1, w2, w3, w4, w5, w6, w7, w8, w9}, {σ1, σ2, σ3}
				{{0.3, 0.5, 0.7},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.30, 1.30, 1.30, 1.50, 1.50, 1.50, 1.70, 1.70, 1.70}}, 
				{{0.3, 0.5, 0.7},  {0.5, 0.5, 0.5}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.15, 1.15, 1.15, 1.25, 1.25, 1.25, 1.35, 1.35, 1.35}},
				{{0.3, 0.5, 0.7},  {0.1, 0.1, 0.1}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.03, 1.03, 1.03, 1.05, 1.05, 1.05, 1.07, 1.07, 1.07}},
				{{0.3, 0.5, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.03, 1.15, 1.30, 1.05, 1.25, 1.50, 1.07, 1.35, 1.70}},
				{{0.3, 0.5, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.30, 4.05, 5.25, 6.50, 7.07, 8.35, 9.70}},
				{{0.0, 0.5, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.00, 2.00, 3.00, 4.05, 5.25, 6.50, 7.07, 8.35, 9.70}},
				{{0.3, 0.0, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.30, 4.00, 5.00, 6.00, 7.07, 8.35, 9.70}},
				{{0.3, 0.5, 0.0},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.30, 4.05, 5.25, 6.50, 7.00, 8.00, 9.00}},
				{{0.3, 0.5, 0.7},  {0.0, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.00, 2.15, 3.30, 4.00, 5.25, 6.50, 7.00, 8.35, 9.70}},
				{{0.3, 0.5, 0.7},  {0.1, 0.0, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.00, 3.30, 4.05, 5.00, 6.50, 7.07, 8.00, 9.70}},
				{{0.3, 0.5, 0.7},  {0.1, 0.5, 0.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.00, 4.05, 5.25, 6.00, 7.07, 8.35, 9.00}},
		};		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer outputLayer = new Layer();
			for( int i = 0; i < outputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				//set Sigma for output neurons
				ForDefineOutputValue_ActivationFunction myActivationFunction = new ForDefineOutputValue_ActivationFunction(dataPairs[staticCycle][0][i] );
				neuron.setActivationFunction(myActivationFunction);
				outputLayer.addNeuron(neuron);
			}

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				//set Delta for input neurons
				neuron.setDelta( dataPairs[ staticCycle][1][i] );
				inputLayer.addNeuron(neuron);
			}
			
			//Set Weights
			InnerConnector innerConnector = new InnerConnector( outputLayer, inputLayer );
			innerConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(int outputNeuronOrder, int inputNeuronIndex) {
					return dataPairs[staticCycle][3][outputNeuronOrder * 3 + inputNeuronIndex];
				}
			});
			innerConnector.resetWeights();
						
			for( int i = 0; i < inputLayerSize; i++ ){
				//--- that is under TEST ---
				innerConnector.calculateInputWeight(i, dataPairs[staticCycle][2][0], dataPairs[staticCycle][2][1]);
			}
		
			for( int i = 0; i < outputLayerSize; i++ ){
				for( int j = 0; j < inputLayerSize; j++ ){
					assertEquals( dataPairs[staticCycle][4][i * 3 + j], innerConnector.getInputWeight(i, j) );
				}
			}						
		}
	}	
	
	public void test_calculateInputWeights_Method(){
		int inputLayerSize = 3;
		int outputLayerSize = 3;
		
		final double[][][] dataPairs = {
				//{σo1, σo2, σo3}, {δi1, δi2, δi3}, {α, β}, {w1, w2, w3, w4, w5, w6, w7, w8, w9}, {σ1, σ2, σ3}
				{{0.3, 0.5, 0.7},  {1.0, 1.0, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.30, 1.30, 1.30, 1.50, 1.50, 1.50, 1.70, 1.70, 1.70}}, 
				{{0.3, 0.5, 0.7},  {0.5, 0.5, 0.5}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.15, 1.15, 1.15, 1.25, 1.25, 1.25, 1.35, 1.35, 1.35}},
				{{0.3, 0.5, 0.7},  {0.1, 0.1, 0.1}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.03, 1.03, 1.03, 1.05, 1.05, 1.05, 1.07, 1.07, 1.07}},
				{{0.3, 0.5, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.03, 1.15, 1.30, 1.05, 1.25, 1.50, 1.07, 1.35, 1.70}},
				{{0.3, 0.5, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.30, 4.05, 5.25, 6.50, 7.07, 8.35, 9.70}},
				{{0.0, 0.5, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.00, 2.00, 3.00, 4.05, 5.25, 6.50, 7.07, 8.35, 9.70}},
				{{0.3, 0.0, 0.7},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.30, 4.00, 5.00, 6.00, 7.07, 8.35, 9.70}},
				{{0.3, 0.5, 0.0},  {0.1, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.30, 4.05, 5.25, 6.50, 7.00, 8.00, 9.00}},
				{{0.3, 0.5, 0.7},  {0.0, 0.5, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.00, 2.15, 3.30, 4.00, 5.25, 6.50, 7.00, 8.35, 9.70}},
				{{0.3, 0.5, 0.7},  {0.1, 0.0, 1.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.00, 3.30, 4.05, 5.00, 6.50, 7.07, 8.00, 9.70}},
				{{0.3, 0.5, 0.7},  {0.1, 0.5, 0.0}, {1.0, 0.0}, {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0}, {1.03, 2.15, 3.00, 4.05, 5.25, 6.00, 7.07, 8.35, 9.00}},
		};		
		
		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle++ ){

			Layer outputLayer = new Layer();
			for( int i = 0; i < outputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				//set Sigma for output neurons
				ForDefineOutputValue_ActivationFunction myActivationFunction = new ForDefineOutputValue_ActivationFunction(dataPairs[staticCycle][0][i] );
				neuron.setActivationFunction(myActivationFunction);
				outputLayer.addNeuron(neuron);
			}

			Layer inputLayer = new Layer();
			for( int i = 0; i < inputLayerSize; i++ ){
				Neuron neuron = new Neuron();
				
				//set Delta for input neurons
				neuron.setDelta( dataPairs[ staticCycle][1][i] );
				inputLayer.addNeuron(neuron);
			}
			
			//Set Weights
			InnerConnector innerConnector = new InnerConnector( outputLayer, inputLayer );
			innerConnector.setResetWeightStrategy( new IResetWeightStrategy() {
				public double getWeight(int outputNeuronOrder, int inputNeuronIndex) {
					return dataPairs[staticCycle][3][outputNeuronOrder * 3 + inputNeuronIndex];
				}
			});
			innerConnector.resetWeights();
						
			//--- that is under TEST ---
			innerConnector.calculateInputWeights( dataPairs[staticCycle][2][0], dataPairs[staticCycle][2][1] );

/*			for(int i = 0; i < outputLayerSize; i++ ){
				for( int j = 0; j < inputLayerSize; j++ ){			
					double weight = innerConnector.getInputWeight(i, j);
					System.err.print(weight + ", ");
				}
				System.err.println();
			}
*/			
			for( int i = 0; i < outputLayerSize; i++ ){
				for( int j = 0; j < inputLayerSize; j++ ){
					assertEquals( dataPairs[staticCycle][4][i * 3 + j], innerConnector.getInputWeight(i, j) );
				}
			}						
		}
	}
	
	public void test_calculateOutputDelta_Method(){
		
	}
	
	
}
