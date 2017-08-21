package hu.akoel.neurnet;

import hu.akoel.neurnet.neuron.IInputNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.strategies.StaticDefaultWeightStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class NeuronTest extends TestCase{ 

    public NeuronTest( String testName ) {
        super( testName );
    }

    public static Test suite(){
        return new TestSuite( NeuronTest.class );
    }

    public void testSetterGetterPairs(){
    	Double[][] dataPairs = {
    			//{input, w, δ}
    			{0.0, 0.5, 0.0},
    			{0.0, 0.5, 0.3},
    			{0.0, 0.5, 0.7},
    	};

    	for (int i = 0; i < dataPairs.length; i++ ){
    		IInputNeuron inputNeuron = new InputNeuron();

    		// --- w ---
    		inputNeuron.setWeight( new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		assertEquals( dataPairs[i][1], inputNeuron.getWeight() );
    		
    		// --- input ---
    		inputNeuron.setInput(dataPairs[i][1]);	
    		assertEquals( dataPairs[i][1], inputNeuron.getInput() );
    	
    		// --- δ ---
    		inputNeuron.calculateWeight(dataPairs[i][2], 0.5, 0.7 );
    		assertEquals( dataPairs[i][2], inputNeuron.getDelta() );
    		
    	}    	
    }
    
    public void testInputNeuronCalculateOutputMethod(){
    	Double[][] dataPairs = {
    			//{w, Input, expectedValue}
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
    
    public void testInputNeuronCalculateWeightMethod(){
    	Double[][] dataPairs = {
    			//{input, w, δ, α, β, expectedValue}
    												//β=0.0
    			{1.0, 0.2, 0.5, 1.0, 0.0, 0.7},
    			{1.0, 0.2, 0.5, 0.5, 0.0, 0.45},
    			{1.0, 0.2, 0.5, 0.0, 0.0, 0.2},		//α=0.0
    			{1.0, 0.2, 0.0, 0.5, 0.0, 0.2},		//δ=0.0
    			{0.5, 0.0, 0.3, 0.7, 0.0, 0.105},	//w=0.0
    			
    	
    	};
    	for (int i = 0; i < dataPairs.length; i++ ){
    		IInputNeuron inputNeuron = new InputNeuron();
    		inputNeuron.setWeight( new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		inputNeuron.setInput( dataPairs[i][0] );
    		inputNeuron.calculateWeight(dataPairs[i][2], dataPairs[i][3], dataPairs[i][4]);
    		
    		assertEquals( dataPairs[i][5], inputNeuron.getWeight() );
   		
    	}
    	
    }
}
