package hu.akoel.neurnet;

import hu.akoel.neurnet.activationfunctions.IActivationFunction;
import hu.akoel.neurnet.connectors.OutputConnector;
import hu.akoel.neurnet.handlers.OutputDataHandler;
import hu.akoel.neurnet.layer.Layer;
import hu.akoel.neurnet.neuron.Neuron;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
	
public class OutputConnectorTest extends TestCase{ 

	public static int staticCycle;
	
	public OutputConnectorTest( String testName ) {
		super( testName );
	}

	public static Test suite(){
		return new TestSuite( OutputConnectorTest.class );
	}

	class MyActivationFunction_ActivationFunction implements IActivationFunction {
		private double sigma;
		private double derivate;
		MyActivationFunction_ActivationFunction( double sigma, double derivate ) {
			this.sigma = sigma;
			this.derivate = derivate;
		}
		public double getSigma() {
			return sigma;
		}			
		public double getDerivateSigmaBySum() {
			return derivate;
		}
		public void calculateDetails(double summa) {
		}
	};
	
	class MyOutputDataHandler implements OutputDataHandler {
		private double[] expectedOutputValues;			
		public void setExpectedOutputValues( double[] expecedOutputValues ){
			this.expectedOutputValues = expecedOutputValues;
		}
		public double getExpectedOutput(int outputNeuronIndex) {
			return expectedOutputValues[ outputNeuronIndex];
		}
	};
	
	public void test_calculateOutputDelta_Method(){
		int outputLayerSize = 3;
		
		final Double[][][] dataPairs = {
				//{σ1, σ2, σ3}, {der1, der2, der3}, {expected1, expected2, expected3}, {delta1, delta2, delta3}
    			{{0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}},
    			{{1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}},
    			{{1.0, 1.0, 1.0}, {0.3, 0.5, 0.7}, {2.0, 2.0, 2.0}, {0.3, 0.5, 0.7}},
    			{{2.0, 2.0, 2.0}, {0.3, 0.5, 0.7}, {1.0, 1.0, 1.0}, {-0.3, -0.5, -0.7}},
    			{{1.0, 2.0, 3.0}, {0.3, 0.5, 0.7}, {3.0, 4.0, 5.0}, {0.6, 1.0, 1.4}},
    			{{3.0, 2.0, 3.0}, {0.3, 0.5, 0.7}, {1.0, 4.0, 5.0}, {-0.6, 1.0, 1.4}},
    			{{1.0, 4.0, 3.0}, {0.3, 0.5, 0.7}, {3.0, 2.0, 5.0}, {0.6, -1.0, 1.4}},
    			{{1.0, 4.0, 5.0}, {0.3, 0.5, 0.7}, {3.0, 2.0, 3.0}, {0.6, -1.0, -1.4}},
		};
		
		Layer outputLayer = new Layer();
		for( int i = 0; i < outputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			outputLayer.addNeuron(neuron);		
		}

		MyOutputDataHandler outputDataHandler = new MyOutputDataHandler();
		OutputConnector outputConnector = new OutputConnector( outputLayer );
		outputConnector.setOutputDataHandler(outputDataHandler);

		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle ++ ){		

			outputDataHandler.setExpectedOutputValues( new double[]{dataPairs[staticCycle][2][0], dataPairs[staticCycle][2][1], dataPairs[staticCycle][2][2]});

			for( int i = 0; i < outputLayerSize; i++ ){
				Neuron neuron = outputLayer.getNeuron( i );
				MyActivationFunction_ActivationFunction myActivationFunction = new MyActivationFunction_ActivationFunction(dataPairs[staticCycle][0][i], dataPairs[staticCycle][1][i] );
				neuron.setActivationFunction(myActivationFunction);
				
				//--- that is under TEST ---
				outputConnector.calculateOutputDelta( i );;
			}

			for( int j = 0; j < outputLayerSize; j++ ){
				double delta = outputLayer.getNeuron( j ).getDelta();
				assertEquals(dataPairs[staticCycle][3][j], delta);
			}
		}
	}	
	
	public void test_calculateOutputDeltas_Method(){
		int outputLayerSize = 3;
		
		final Double[][][] dataPairs = {
				//{σ1, σ2, σ3}, {der1, der2, der3}, {expected1, expected2, expected3}, {delta1, delta2, delta3}
    			{{0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}},
    			{{1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}, {0.0, 0.0, 0.0}},
    			{{1.0, 1.0, 1.0}, {0.3, 0.5, 0.7}, {2.0, 2.0, 2.0}, {0.3, 0.5, 0.7}},
    			{{2.0, 2.0, 2.0}, {0.3, 0.5, 0.7}, {1.0, 1.0, 1.0}, {-0.3, -0.5, -0.7}},
    			{{1.0, 2.0, 3.0}, {0.3, 0.5, 0.7}, {3.0, 4.0, 5.0}, {0.6, 1.0, 1.4}},
    			{{3.0, 2.0, 3.0}, {0.3, 0.5, 0.7}, {1.0, 4.0, 5.0}, {-0.6, 1.0, 1.4}},
    			{{1.0, 4.0, 3.0}, {0.3, 0.5, 0.7}, {3.0, 2.0, 5.0}, {0.6, -1.0, 1.4}},
    			{{1.0, 4.0, 5.0}, {0.3, 0.5, 0.7}, {3.0, 2.0, 3.0}, {0.6, -1.0, -1.4}},
		};
		
		Layer outputLayer = new Layer();
		for( int i = 0; i < outputLayerSize; i++ ){
			Neuron neuron = new Neuron();
			outputLayer.addNeuron(neuron);		
		}

		MyOutputDataHandler outputDataHandler = new MyOutputDataHandler();
		OutputConnector outputConnector = new OutputConnector( outputLayer );
		outputConnector.setOutputDataHandler(outputDataHandler);

		for( staticCycle = 0; staticCycle < dataPairs.length; staticCycle ++ ){		

			outputDataHandler.setExpectedOutputValues( new double[]{dataPairs[staticCycle][2][0], dataPairs[staticCycle][2][1], dataPairs[staticCycle][2][2]});

			for( int i = 0; i < outputLayerSize; i++ ){
				Neuron neuron = outputLayer.getNeuron( i );
				MyActivationFunction_ActivationFunction myActivationFunction = new MyActivationFunction_ActivationFunction(dataPairs[staticCycle][0][i], dataPairs[staticCycle][1][i] );
				neuron.setActivationFunction(myActivationFunction);
			}
			
			//--- that is under TEST ---
			outputConnector.calculateOutputDeltas();;

			for( int j = 0; j < outputLayerSize; j++ ){
				double delta = outputLayer.getNeuron( j ).getDelta();
				assertEquals(dataPairs[staticCycle][3][j], delta);
			}
		}
	}
}
