package hu.akoel.n_neurnet;

import java.util.Iterator;

import hu.akoel.n_neurnet.layer.Layer;
import hu.akoel.n_neurnet.neuron.Neuron;
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

	public void test_getSize_Method(){
		int neuronNumbers = 200;		
		Layer layer = new Layer();		
		for( int i = 0; i < neuronNumbers; i++ ){			
			layer.addNeuron( new Neuron() );
		}		
		assertEquals( neuronNumbers, layer.getSize() );
	}
	
	public void test_addNeuronIterator_Method(){
		int neuronNumbers = 200;
		
		Layer layer = new Layer();
		
		for( int i = 0; i < neuronNumbers; i++ ){
			Neuron neuron = new Neuron();
			layer.addNeuron(neuron);
		}
		
		int i = 0;
		Iterator<Neuron> iterator = layer.getNeuronIterator();
		while( iterator.hasNext() ){
			assertEquals(i, iterator.next().getIndex() );
			i++;
		}
		
		assertEquals( neuronNumbers, i);
	}
}
