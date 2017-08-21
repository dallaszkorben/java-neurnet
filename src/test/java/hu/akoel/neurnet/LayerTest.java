package hu.akoel.neurnet;

import java.util.ArrayList;
import java.util.Iterator;

import hu.akoel.neurnet.layer.InputLayer;
import hu.akoel.neurnet.neuron.ANeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
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
    public void testInputLayerCheckNeuronsInTheList(){
    	int numberOfNeurons = 100;
    	ArrayList<ANeuron> neuronArray = new ArrayList<ANeuron>();
    	
    	InputLayer inputLayer = new InputLayer();
    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    		neuronArray.add(inputNeuron);
    	}
    	
    	//Checks the getNeuronIterator() method
    	Iterator<ANeuron> neuronIterator = inputLayer.getNeuronIterator();
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
    
    public void testInputLayerCheckInitializeNeuronsMethod(){
    	int numberOfNeurons = 100;
    	
    	InputLayer inputLayer = new InputLayer();    			
    	for( int i = 0; i < numberOfNeurons; i++ ){
    		InputNeuron inputNeuron = new InputNeuron();
    		inputLayer.addNeuron(inputNeuron);
    	}
    	
    	inputLayer.initializeNeurons( new DefaultWeightStrategy() {
			public Double getValue( ANeuron neuron ) {
				return neuron.hashCode() + 0.0;
			}
		});
    	
    	Iterator<ANeuron> it = inputLayer.getNeuronIterator();
    	while( it.hasNext() ){
    		InputNeuron neuron = (InputNeuron)(it.next());
    		assertEquals( neuron.hashCode() + 0.0, neuron.getWeight() );
    	}
    }
    
}
