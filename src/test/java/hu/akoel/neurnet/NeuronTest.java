package hu.akoel.neurnet;

import hu.akoel.neurnet.neuron.IInputNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.strategies.StaticDefaultWeightStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class NeuronTest extends TestCase{ 

    public NeuronTest( String testName ) {
        super( testName );
    }

    public static Test suite(){
        return new TestSuite( NeuronTest.class );
    }

    public void testInputNeuronCalculateOutput(){
    	Double[][] dataPairs = {
    			{10.0, 0.0, 0.5},
    			{10.0, 1.0, 0.9999546021312976},
    			{-10.0, 1.0, 4.539786870243442E-5},
    			{2.0, 2.0, 0.9820137900379085},
    			{2.0, -2.0, 0.017986209962091562},
    	
    	};
    	for (int i = 0; i < dataPairs.length; i++ ){
    		IInputNeuron inputNeuron = new InputNeuron();
    		inputNeuron.setWeight( new StaticDefaultWeightStrategy( dataPairs[i][0] ) );
    		inputNeuron.setInput( dataPairs[i][1] );
    		inputNeuron.calculateOutput();
    		    		
    		assertEquals( dataPairs[i][2], inputNeuron.getSigma() );
   		
    	}
    	
    }
    
    public void testInputNeuronCalculateWeight(){
    	Double[][] dataPairs = {
    			{10.0, 0.0, 0.5},
    			
    	
    	};
    	for (int i = 0; i < dataPairs.length; i++ ){
    		IInputNeuron inputNeuron = new InputNeuron();
    		inputNeuron.setWeight( new StaticDefaultWeightStrategy( dataPairs[i][0] ) );
    		inputNeuron.setInput( dataPairs[i][1] );
    		//inputNeuron.calculateWeight(δ);
    		    		
    		//assertEquals( dataPairs[i][2], inputNeuron.getSigma() );
   		
    	}
    	
    }
}
