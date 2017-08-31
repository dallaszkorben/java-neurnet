package hu.akoel.n_neurnet;

import hu.akoel.n_neurnet.activationfunctions.IActivationFunction;
import hu.akoel.n_neurnet.neuron.Neuron;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
	
public class NeuronTest extends TestCase{ 

	public static int actualCycle;
	
	public NeuronTest( String testName ) {
		super( testName );
	}

	public static Test suite(){
		return new TestSuite( NeuronTest.class );
	}

	public void test_setOrder_Method(){
		Neuron neuron = new Neuron();
		
		for( int i = 0; i <= 100; i++ ){
			neuron.setIndex( i );
			assertEquals( i, neuron.getIndex() );
		}
	}
	
	public void test_setDelta_Method(){
		Neuron neuron = new Neuron();
		
		for( int i = 0; i <= 100; i++ ){
			double data = (i + 0.0) / 10;
			neuron.setDelta( data );
			assertEquals( data, neuron.getDelta() );
		}
	}
	
	public void test_calculateSigma_summa_Method(){
		Double[][] dataPairs = {
    			//{input, w, σ, δ}
    			//{summa, σ}
    			{0.0, 0.5},
    			{1.0, 0.7310585786300049},
    			{-1.0,0.2689414213699951},
		};
		
		Neuron neuron = new Neuron();		
		for( int i = 0; i < dataPairs.length; i++ ){
			neuron.calculateActivationFunction(dataPairs[i][0]);
			assertEquals( dataPairs[i][1], neuron.getSigma() );
		}
	}
	
	public void test_setActivationFunction(){
		Double[][] dataPairs = {
    			//{summa, σ}
    			{0.0, -0.0},
    			{1.0, -1.0},
    			{-1.0, 1.0},
    			{97.0, -97.0},
    			{-97.0, 97.0},    			
		};

		Neuron neuron = new Neuron();		
		
		neuron.setActivationFunction( new IActivationFunction() {			
			double summa;
			public void calculateDetails(double summa) {
				this.summa = summa;
			}
			public double getSigma() {
				return -summa;
			}
			public double getDerivateSigmaBySum() {
				return 0;
			}
		});
		
		for( int i = 0; i < dataPairs.length; i++ ){
			neuron.calculateActivationFunction( dataPairs[i][0] );
			assertEquals( dataPairs[i][1], neuron.getSigma() );
		}
		
	}
	
}
