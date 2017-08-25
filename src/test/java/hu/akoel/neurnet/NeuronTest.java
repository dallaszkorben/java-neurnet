package hu.akoel.neurnet;

import java.util.ArrayList;

import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.InnerNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.neuron.OutputNeuron;
import hu.akoel.neurnet.strategies.SigmoidActivationFunction;
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

    // -------------
    // --- Input ---
    // -------------
    public void testInputNeuron_Check_SetterGetterPairs(){
    	Double[][] dataPairs = {
    			//{input, w, σ, δ}
    			{0.0, 0.5, 0.0, 2.3},
    			{0.0, 0.5, 0.3, 0.0},
    			{0.0, 0.5, 0.7, -1.7},
    	};

    	for (int i = 0; i < dataPairs.length; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();

    		// --- w ---
    		inputNeuron.setWeight( new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		assertEquals( dataPairs[i][1], inputNeuron.getWeight() );
    		
    		// --- input ---
    		inputNeuron.setInput( dataPairs[i][1] );	
    		assertEquals( dataPairs[i][1], inputNeuron.getInput() );

    		// --- σ ---
    		inputNeuron.setSigma( dataPairs[i][2] );
    		assertEquals( dataPairs[i][2], inputNeuron.getStoredSigma() );

       		// --- order ---
    		inputNeuron.setOrder( i );
    		assertEquals( i, inputNeuron.getOrder() );
    		
    		// --- δ ---
    		//inputNeuron.calculateWeight(dataPairs[i][3], 0.5, 0.7 );
    		inputNeuron.setDelta( dataPairs[i][3] );
    		assertEquals( dataPairs[i][3], inputNeuron.getDelta() );
    	}
    	
    	// --- ActivationFunctionStrategey ---
    	SigmoidActivationFunction safs = new SigmoidActivationFunction();
    	InputNeuron inputNeuron = new InputNeuron();
    	inputNeuron.setActivationFunctionStrategy( safs );
    	assertEquals( safs, inputNeuron.getActivationFunctionStrategy() );    	
    	
    }
    
    public void testInputNeuron_CalculateOutput_Method(){
    	Double[][] dataPairs = {
    			//{Input, w, expectedValue}
    			{0.0, 10.0, 0.5},
    			{1.0, 10.0, 0.9999546021312976},
    			{1.0, -10.0, 4.539786870243442E-5},
    			{2.0, 2.0, 0.9820137900379085},
    			{-2.0, 2.0, 0.017986209962091562},
    	
    	};
    	for (int i = 0; i < dataPairs.length; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputNeuron.setWeight( new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		inputNeuron.setInput( dataPairs[i][0] );
    		inputNeuron.calculateOutput();
    		    		
    		assertEquals( dataPairs[i][2], inputNeuron.getStoredSigma() );   		
    	}    	
    }
    
    public void testInputNeuron_CalculateWeight_Method(){
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
    		InputNeuron inputNeuron = new InputNeuron();
    		inputNeuron.setWeight( new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		inputNeuron.setInput( dataPairs[i][0] );
    		inputNeuron.calculateWeight(dataPairs[i][2], dataPairs[i][3], dataPairs[i][4]);
    		
    		assertEquals( dataPairs[i][5], inputNeuron.getWeight() );
   		
    	}
    	
    }
    
    // --------------
    // --- Inner ---
    // --------------
    public void testInnerNeuron_Check_SetterGetterPairs(){
    	Double[][] dataPairs = {
    			//{w, σ, δ}
    			{0.5, 0.0, 0.3},
    			{0.0, 0.3, 0.5},
    			{0.0, 0.0, 0.7},
    			{0.3, 0.5, 1.1},
    	};
    	//double α = 0.5;
    	//double β = 0.7;    	
    	double input = 2.3;
    	double w = -7.5;
    	InputNeuron inputNeuron = new InputNeuron();    	
    	ArrayList<ANeuron> inputNeuronList = new ArrayList<ANeuron>();
    	inputNeuronList.add(inputNeuron);
    	inputNeuron.setInput( input );
    	inputNeuron.setWeight( new StaticDefaultWeightStrategy( w ) );   	
    	
    	for (int i = 0; i < dataPairs.length; i++ ){    		
    		InnerNeuron innerNeuron = new InnerNeuron();    		
    		
    		// --- w ---
    		innerNeuron.connectToPreviousNeuron(inputNeuronList.iterator(), new StaticDefaultWeightStrategy( dataPairs[i][2] ) );
    		assertEquals( dataPairs[i][2], innerNeuron.getNeuronValues(0).getW_t() );
    		
       		// --- σ ---
    		innerNeuron.setSigma( dataPairs[i][1] );
    		assertEquals( dataPairs[i][1], innerNeuron.getStoredSigma() );
    		
       		// --- order ---
    		innerNeuron.setOrder( i );
    		assertEquals( i, innerNeuron.getOrder() );
    		
    		// --- δ ---
    		innerNeuron.setDelta( dataPairs[i][2] );
    		assertEquals( dataPairs[i][2], innerNeuron.getDelta() );
    		
    	}
    	
       	// --- ActivationFunctionStrategey ---
    	SigmoidActivationFunction safs = new SigmoidActivationFunction();
    	inputNeuron = new InputNeuron();
    	inputNeuron.setActivationFunctionStrategy( safs );
    	assertEquals( safs, inputNeuron.getActivationFunctionStrategy() );    	
    }
    
    public void testInnerNeuron_CalculateOutput_Method(){
    	Double[][] dataPairs = {
    			//{input(σ), w, expected}
    			{0.0, 10.0, 0.5},
    			{5.0, 0.0, 0.5},    			
    			{1.0, 10.0, 0.9999546021312976},
    			{1.0, -10.0, 4.539786870243442E-5},    			
    			{2.0, 2.0, 0.9820137900379085},
    			{-2.0, 2.0, 0.017986209962091562},
    			{-2.0, 2.0, 0.017986209962091562},
    			{2.0, 2.0, 0.9820137900379085},
    			{1.0, -10.0, 4.539786870243442E-5},
    			{1.0, 10.0, 0.9999546021312976},    			
    			{5.0, 0.0, 0.5},
    			{0.0, 10.0, 0.5},
    			
    			
    	};
    	double w = 10;
    	InputNeuron inputNeuron = new InputNeuron();    	
    	ArrayList<ANeuron> inputNeuronList = new ArrayList<ANeuron>();
    	inputNeuronList.add(inputNeuron);
    	inputNeuron.setWeight( new StaticDefaultWeightStrategy( w ) );   	

    	for (int i = 0; i < dataPairs.length; i++ ){    		
    		inputNeuron.setSigma( dataPairs[i][0] );
    		
    		InnerNeuron innerNeuron = new InnerNeuron();    		
    		innerNeuron.connectToPreviousNeuron(inputNeuronList.iterator(), new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		innerNeuron.calculateOutput();
    		    		
    		assertEquals( dataPairs[i][2], innerNeuron.getStoredSigma() );    		
    	}    	
    }
    
    public void testInnerNeuron_CalculateWeight_Method(){
    	Double[][] dataPairs = {
    			//{input, w, δ, α, β, expectedValue}
    												//β=0.0
    			{1.0, 0.2, 0.5, 1.0, 0.0, 0.7},
    			{1.0, 0.2, 0.5, 0.5, 0.0, 0.45},
    			{1.0, 0.2, 0.5, 0.0, 0.0, 0.2},		//α=0.0
    			{1.0, 0.2, 0.0, 0.5, 0.0, 0.2},		//δ=0.0
    			{0.5, 0.0, 0.3, 0.7, 0.0, 0.105},	//w=0.0    	
    	};
    	double w = 10;
    	InputNeuron inputNeuron = new InputNeuron();    	
    	ArrayList<ANeuron> inputNeuronList = new ArrayList<ANeuron>();
    	inputNeuronList.add(inputNeuron);
    	inputNeuron.setWeight( new StaticDefaultWeightStrategy( w ) );   	
    	
    	for (int i = 0; i < dataPairs.length; i++ ){
    		
    		inputNeuron.setSigma( dataPairs[i][0] );
    		
    		InnerNeuron innerNeuron = new InnerNeuron();    		
    		innerNeuron.connectToPreviousNeuron(inputNeuronList.iterator(), new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		innerNeuron.calculateWeight(dataPairs[i][2], dataPairs[i][3], dataPairs[i][4]);
    		
    		assertEquals( dataPairs[i][5], innerNeuron.getNeuronValues(0).getW_t() );   		
    	}    	
    }
    
    // --------------
    // --- Output ---
    // --------------
    public void testOutputNeuron_Check_SetterGetterPairs(){
    	Double[][] dataPairs = {
    			//{w, σ, δ}
    			{0.5, 0.0, 0.3},
    			{0.0, 0.3, 0.5},
    			{0.0, 0.0, 0.7},
    			{0.3, 0.5, 1.1},
    	};
    	//double α = 0.5;
    	//double β = 0.7;    	
    	double input = 2.3;
    	double w = -7.5;
    	InputNeuron inputNeuron = new InputNeuron();    	
    	ArrayList<ANeuron> inputNeuronList = new ArrayList<ANeuron>();
    	inputNeuronList.add(inputNeuron);
    	inputNeuron.setInput( input );
    	inputNeuron.setWeight( new StaticDefaultWeightStrategy( w ) );   	
    	
    	for (int i = 0; i < dataPairs.length; i++ ){    		
    		OutputNeuron outputNeuron = new OutputNeuron();    		
    		
    		// --- w ---
    		outputNeuron.connectToPreviousNeuron(inputNeuronList.iterator(), new StaticDefaultWeightStrategy( dataPairs[i][2] ) );
    		assertEquals( dataPairs[i][2], outputNeuron.getNeuronValues(0).getW_t() );
    		
       		// --- σ ---
    		outputNeuron.setSigma( dataPairs[i][1] );
    		assertEquals( dataPairs[i][1], outputNeuron.getStoredSigma() );
    		
       		// --- order ---
    		outputNeuron.setOrder( i );
    		assertEquals( i, outputNeuron.getOrder() );
    		
    		// --- δ ---
    		outputNeuron.setDelta( dataPairs[i][2] );
    		assertEquals( dataPairs[i][2], outputNeuron.getDelta() );    		
    	}    	
    	
       	// --- ActivationFunctionStrategey ---
    	SigmoidActivationFunction safs = new SigmoidActivationFunction();
    	inputNeuron = new InputNeuron();
    	inputNeuron.setActivationFunctionStrategy( safs );
    	assertEquals( safs, inputNeuron.getActivationFunctionStrategy() );    	
    }
    
    public void testOutputNeuron_CalculateOutput_Method(){
    	Double[][] dataPairs = {
    			//{input (σ), w, expected}
    			{0.0, 10.0, 0.5},
    			{1.0, 10.0, 0.9999546021312976},
    			{1.0, -10.0, 4.539786870243442E-5},
    			{2.0, 2.0, 0.9820137900379085},
    			{-2.0, 2.0, 0.017986209962091562},
    	};
    	double w = 10;
    	InputNeuron inputNeuron = new InputNeuron();    	
    	ArrayList<ANeuron> inputNeuronList = new ArrayList<ANeuron>();
    	inputNeuronList.add(inputNeuron);
    	inputNeuron.setWeight( new StaticDefaultWeightStrategy( w ) );   	

    	for (int i = 0; i < dataPairs.length; i++ ){    		
    		inputNeuron.setSigma( dataPairs[i][0] );
    		
    		OutputNeuron outputNeuron = new OutputNeuron();    		
    		outputNeuron.connectToPreviousNeuron(inputNeuronList.iterator(), new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		outputNeuron.calculateOutput();
    		    		
    		assertEquals( dataPairs[i][2], outputNeuron.getStoredSigma() );    		
    	}    	
    }
    
    public void testOutputNeuron_CalculateWeight_Method(){
    	Double[][] dataPairs = {
    			//{input, w, δ, α, β, expectedValue}
    												//β=0.0
    			{1.0, 0.2, 0.5, 1.0, 0.0, 0.7},
    			{1.0, 0.2, 0.5, 0.5, 0.0, 0.45},
    			{1.0, 0.2, 0.5, 0.0, 0.0, 0.2},		//α=0.0
    			{1.0, 0.2, 0.0, 0.5, 0.0, 0.2},		//δ=0.0
    			{0.5, 0.0, 0.3, 0.7, 0.0, 0.105},	//w=0.0    	
    	};
    	double w = 10;
    	InputNeuron inputNeuron = new InputNeuron();    	
    	ArrayList<ANeuron> inputNeuronList = new ArrayList<ANeuron>();
    	inputNeuronList.add(inputNeuron);
    	inputNeuron.setWeight( new StaticDefaultWeightStrategy( w ) );   	
    	
    	for (int i = 0; i < dataPairs.length; i++ ){
    		
    		inputNeuron.setSigma( dataPairs[i][0] );
    		
    		OutputNeuron outputNeuron = new OutputNeuron();    		
    		outputNeuron.connectToPreviousNeuron(inputNeuronList.iterator(), new StaticDefaultWeightStrategy( dataPairs[i][1] ) );
    		outputNeuron.calculateWeight(dataPairs[i][2], dataPairs[i][3], dataPairs[i][4]);
    		
    		assertEquals( dataPairs[i][5], outputNeuron.getNeuronValues(0).getW_t() );   		
    	}   
    }   

}
