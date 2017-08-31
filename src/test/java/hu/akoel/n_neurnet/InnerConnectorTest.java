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
		return new TestSuite( InnerConnectorTest.class );
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
	
	class SpecialActivationFunction implements IActivationFunction {
		private double summa;
		SpecialActivationFunction( double summa ) {
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
	
	public void test_getSumma_Method(){
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
				
				SpecialActivationFunction myActivationFunction = new SpecialActivationFunction(dataPairs[staticCycle][i] );
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
				double summa = innerConnector.getInputSumma( inputNeuron.getOrder() );
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

	
}
