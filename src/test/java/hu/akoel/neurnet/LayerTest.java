package hu.akoel.neurnet;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.InputLayer;
import hu.akoel.neurnet.layer.OutputLayer;
import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.neuron.OutputNeuron;
import hu.akoel.neurnet.strategies.ActivationFunctionStrategy;
import hu.akoel.neurnet.strategies.DefaultWeightStrategy;
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

    	inputLayer.calculateWeights(outputLayer, α, β);
    	Iterator<InputNeuron> neuronIterator = inputLayer.getNeuronIterator();
    	
    	while( neuronIterator.hasNext() ){
    		InputNeuron neuron = neuronIterator.next();
    		
    		assertEquals( neuron.getWeight(), ( ( numberOfNeurons - neuron.getOrder() + 0.0 ) / 1000 ) + 1.0);
    	}    	
    	
    	
    }
    
    private double getSum( double value ){
    	if( value != 1 ){    		
    		value += getSum( value - 1 );
    	}
    	return value;
    }

}
