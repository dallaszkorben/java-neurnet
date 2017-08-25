package hu.akoel.neurnet;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.InnerLayer;
import hu.akoel.neurnet.layer.InputLayer;
import hu.akoel.neurnet.layer.OutputLayer;
import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.InnerNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.neuron.NeuronWeights;
import hu.akoel.neurnet.neuron.OutputNeuron;
import hu.akoel.neurnet.strategies.ActivationFunctionStrategy;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;
import hu.akoel.neurnet.strategies.RandomDefaultWeightStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LayerTest extends TestCase{ 

    public LayerTest( String testName ) {
        super( testName );
    }

    public static Test suite(){
        return new TestSuite( LayerTest.class );
    }

    // -------------
    // --- Input ---
    // -------------
    public void testInputLayer_Check_NeuronsInTheList(){
    	int numberOfNeurons = 100;    	
    	ArrayList<ANeuron> neuronArray = new ArrayList<ANeuron>();
    	
    	InputLayer inputLayer = new InputLayer();    	
    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    		neuronArray.add(inputNeuron);
    	}
    	
    	//Checks the getOrderOfLayerMethod()
    	for( int i = 0; i < 1000; i++ ){
    		inputLayer.setOrderOfLayer( i );
    		assertEquals( (Integer)i, inputLayer.getOrderOfLayer() );
    	}
    	
    	
    	//Checks the getNeuronIterator() method
    	Iterator<InputNeuron> neuronIterator = inputLayer.getNeuronIterator();
    	int i = 0;
    	while( neuronIterator.hasNext() ){
    		ANeuron neuronIte = neuronIterator.next();
    		ANeuron neuronFor = neuronArray.get(i);    	
    		assertEquals(neuronIte, neuronFor);
    		i++;
    	}
    	
    	//Checks the getNeuronOrder() method
    	for( i = 0; i < numberOfNeurons; i++){    	
    		assertEquals( inputLayer.getNeuronOrder(neuronArray.get(i)), i );
    	}
    	
    	//Checks the getNumberOfNeurons() meghod    	
    	assertEquals( inputLayer.getNumberOfNeurons(), numberOfNeurons );
    }
    
    public void testInputLayer_InitializeNeurons_Method(){
    	int numberOfNeurons = 100;
    	
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    	}
    	
    	//That what I test
    	inputLayer.initializeNeurons( new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return actualNeuron.hashCode() + 0.0;
			}
		});
    	
    	Iterator<InputNeuron> it = inputLayer.getNeuronIterator();
    	while( it.hasNext() ){
    		InputNeuron neuron = (InputNeuron)(it.next());
    		assertEquals( neuron.hashCode() + 0.0, neuron.getWeight() );
    	}
    }

    public void testInputLayer_CalculateSigmas_Method(){    	
    	final Double[][] dataPairs = {
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
    	
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < dataPairs.length; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputNeuron.setInput( dataPairs[i][0] );
    		inputLayer.addNeuron(inputNeuron);
    	}
    	
    	inputLayer.initializeNeurons( new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return dataPairs[actualNeuron.getOrder()][1];
			}
		});

    	//That is what I test
    	inputLayer.calculateSigmas();
    	
    	Iterator<InputNeuron> it = inputLayer.getNeuronIterator();
    	int i = 0;
    	while( it.hasNext() ){
    		InputNeuron neuron = (InputNeuron)(it.next());
    		assertEquals( dataPairs[i][2], neuron.getStoredSigma() );
    		i++;
    	}   	
    }
    
  


    
    
    public void testInputLayer_CalculateWeight_Method(){
    	final int numberOfNeurons = 19;
    	double α = 1.0;
    	double β = 0.0;    	
    	double sum = getSum(numberOfNeurons); 
    	
    	ActivationFunctionStrategy activationFunctionStrategy = new ActivationFunctionStrategy() {
			public double getSigma(double S) {
				return 0;
			}			
			public double getDerivatedSigmaByStoredSigma(double σ) {
				return σ;
			}
		};
    	
		//
    	//Input Layer
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    		inputNeuron.setActivationFunctionStrategy(activationFunctionStrategy);
    		inputNeuron.setSigma( (i + 1.0) );
    		inputNeuron.setInput( 1.0 / ( sum * inputNeuron.getStoredSigma() ) );
    	}
    	
    	inputLayer.initializeNeurons( new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				int neuronOrder = actualNeuron.getOrder();
				return ( numberOfNeurons - neuronOrder + 0.0 ) / 1000;
			}
		});

    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		OutputNeuron outputNeuron = new OutputNeuron();
    		outputLayer.addNeuron(outputNeuron);
    		outputNeuron.setDelta( 1.0 );
    	}

    	outputLayer.initializeNeurons( inputLayer, new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return ( actualNeuron.getOrder() + 1.0 );
			}
		});

    	//That what I test
    	inputLayer.calculateWeights(outputLayer, α, β);
    	Iterator<InputNeuron> neuronIterator = inputLayer.getNeuronIterator();
    	
    	while( neuronIterator.hasNext() ){
    		InputNeuron neuron = neuronIterator.next();
    		
    		assertEquals( ( ( numberOfNeurons - neuron.getOrder() + 0.0 ) / 1000 ) + 1.0, neuron.getWeight() );
    	}    	
    }
    
    private double getSum( double value ){
    	if( value != 1 ){    		
    		value += getSum( value - 1 );
    	}
    	return value;
    }

    // -------------
    // --- Inner ---
    // -------------
    public void testnInerLayer_CalculateWeight_Method(){
       	final int numberOfNeurons = 19;
    	double α = 1.0;
    	double β = 0.0;    	
    	double sum = getSum(numberOfNeurons); 
    	
    	ActivationFunctionStrategy activationFunctionStrategy = new ActivationFunctionStrategy() {
			public double getSigma(double S) {
				return 0;
			}			
			public double getDerivatedSigmaByStoredSigma(double σ) {
				return σ;
			}
		};
		
		//
    	//Input Layer - needed to initialize InnerLayer
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    		inputNeuron.setActivationFunctionStrategy(activationFunctionStrategy);
    		inputNeuron.setSigma( 1.0 / ( (1.0) * sum ) );
    	}
		
    	//
    	//Inner Layer
    	InnerLayer innerLayer = new InnerLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InnerNeuron innerNeuron = new InnerNeuron();
    		innerLayer.addNeuron(innerNeuron);
    		innerNeuron.setActivationFunctionStrategy(activationFunctionStrategy);
    		innerNeuron.setSigma( (i + 1.0) );
    	}
    	
    	innerLayer.initializeNeurons( inputLayer, new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return ( 1.0 );
			}
		});

    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		OutputNeuron outputNeuron = new OutputNeuron();
    		outputLayer.addNeuron(outputNeuron);
    		outputNeuron.setDelta( 1.0 );
    	}

    	outputLayer.initializeNeurons( innerLayer, new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return ( actualNeuron.getOrder() + 1.0 );
			}
		});

    	//That what I test
    	innerLayer.calculateWeights(outputLayer, α, β);
    	Iterator<InnerNeuron> neuronIterator = innerLayer.getNeuronIterator();
    	
    	double i = 2.0;
    	while( neuronIterator.hasNext() ){    		
    		InnerNeuron neuron = neuronIterator.next();
    		for ( int j = 0; j < inputLayer.getNumberOfNeurons(); j++ ){
    			 NeuronWeights nw = neuron.getNeuronValues(j);
    			 assertEquals( i, nw.getW_t());
    		}
    		i++;
    	}    	
    }
    
    
    
    // --------------
    // --- Output ---
    // --------------
    public void testOutputLayer_CalculateWeight_Method(){
       	final int numberOfNeurons = 19;
    	double α = 1.0;
    	double β = 0.0;    	
    	double sum = getSum(numberOfNeurons); 
    	
    	ActivationFunctionStrategy activationFunctionStrategy = new ActivationFunctionStrategy() {
			public double getSigma(double S) {
				return 0;
			}			
			public double getDerivatedSigmaByStoredSigma(double σ) {
				return σ;
			}
		};
		
		//
    	//Input Layer - needed to initialize InnerLayer
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    		//inputNeuron.setActivationFunctionStrategy(activationFunctionStrategy);
    		//inputNeuron.setSigma( 1.0 / ( (1.0) * sum ) );
    	}
		
    	//
    	//Inner Layer - needed to initialize OutputLayer
    	InnerLayer innerLayer = new InnerLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InnerNeuron innerNeuron = new InnerNeuron();
    		innerLayer.addNeuron(innerNeuron);
    		innerNeuron.setActivationFunctionStrategy(activationFunctionStrategy);    		
    		innerNeuron.setSigma( 1.0 / ( (1.0) * sum ) );
    	}
    	
    	innerLayer.initializeNeurons( inputLayer, new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return ( 0.0 );
			}
		});
    	
    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		OutputNeuron outputNeuron = new OutputNeuron();
    		outputLayer.addNeuron(outputNeuron);
    		outputNeuron.setDelta( 1.0 );
    		outputNeuron.setSigma( (i + 1.0) );
    		outputNeuron.setActivationFunctionStrategy(activationFunctionStrategy);
    	}

    	outputLayer.initializeNeurons( innerLayer, new DefaultWeightStrategy() {
			public Double getValue(ANeuron previousNeuron, ANeuron actualNeuron) {
				return ( actualNeuron.getOrder() + 2.0 );
			}
		});

    	
    	double[] output = new double[numberOfNeurons];
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		output[ i ] = i + 1.0;
    	}
    	
    	//That what I test
    	outputLayer.calculateWeights(output, α, β);
    	
    	Iterator<OutputNeuron> neuronIterator = outputLayer.getNeuronIterator();    	
    	double i = 2.0;
    	while( neuronIterator.hasNext() ){    		
    		OutputNeuron neuron = neuronIterator.next();
    		for ( int j = 0; j < outputLayer.getNumberOfNeurons(); j++ ){
    			 NeuronWeights nw = neuron.getNeuronValues(j);
    			 assertEquals( i, nw.getW_t());
    			 //System.err.print( nw.getW_t() + ", " );
    		}
    		//System.err.println();
    		i++;
    	}        	
    }
    
    public void testOutputLayer_GetMeanSquareError_Method(){
    	final int numberOfNeurons = 20;
    	final double dSigma = 0.25; //Do not change this value otherwise the precision will be different
	
		//
    	//Input Layer - needed to initialize InnerLayer
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    	}
		
    	//
    	//Inner Layer - needed to initialize OutputLayer
    	InnerLayer innerLayer = new InnerLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InnerNeuron innerNeuron = new InnerNeuron();
    		innerLayer.addNeuron(innerNeuron);
    	}    	
    	innerLayer.initializeNeurons( inputLayer, new RandomDefaultWeightStrategy() );
    	
    	//
    	//Output Layer
    	OutputLayer outputLayer = new OutputLayer();
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		OutputNeuron outputNeuron = new OutputNeuron();
    		outputLayer.addNeuron(outputNeuron);
    		outputNeuron.setSigma( (i) );
    	}
    	outputLayer.initializeNeurons( innerLayer, new RandomDefaultWeightStrategy() );

    	
    	double[] output = new double[numberOfNeurons];
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		output[ i ] = i + dSigma;
    	}

    	//That what I test
    	double mse = outputLayer.getMeanSquareError( output );

    	assertEquals( Math.pow( dSigma, 2 ), mse );
    }    
}
