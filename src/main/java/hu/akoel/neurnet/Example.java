package hu.akoel.neurnet;

import java.util.ArrayList;

import hu.akoel.neurnet.layer.InnerLayer;
import hu.akoel.neurnet.layer.InputLayer;
import hu.akoel.neurnet.layer.OutputLayer;
import hu.akoel.neurnet.listeners.ICycleListener;
import hu.akoel.neurnet.network.Network;
import hu.akoel.neurnet.neuron.InnerNeuron;
import hu.akoel.neurnet.neuron.InputNeuron;
import hu.akoel.neurnet.neuron.OutputNeuron;

public class Example {
	InputNeuron inputNeuron1;
	InputNeuron inputNeuron2;

	InnerNeuron innerNeuron11;
	InnerNeuron innerNeuron12;
	InnerNeuron innerNeuron13;
	InnerNeuron innerNeuron14;
	InnerNeuron innerNeuron15;
	InnerNeuron innerNeuron16;
	
	InnerNeuron innerNeuron21;
	InnerNeuron innerNeuron22;
	InnerNeuron innerNeuron23;
	InnerNeuron innerNeuron24;
	InnerNeuron innerNeuron25;
	InnerNeuron innerNeuron26;
	
	OutputNeuron outputNeuron1;
	
	InputLayer inputLayer;
	InnerLayer innerLayer1;
	OutputLayer outputLayer;

	public Example() {


		inputNeuron1 = new InputNeuron();
		inputNeuron2 = new InputNeuron();

		innerNeuron11 = new InnerNeuron();
		innerNeuron12 = new InnerNeuron();
		innerNeuron13 = new InnerNeuron();
		innerNeuron14 = new InnerNeuron();
		innerNeuron15 = new InnerNeuron();
		innerNeuron16 = new InnerNeuron();
		
		innerNeuron21 = new InnerNeuron();
		innerNeuron22 = new InnerNeuron();
		innerNeuron23 = new InnerNeuron();
		innerNeuron24 = new InnerNeuron();
		innerNeuron25 = new InnerNeuron();
		innerNeuron26 = new InnerNeuron();
		
		outputNeuron1 = new OutputNeuron();

		// Input Layer
		inputLayer = new InputLayer();
		inputLayer.addNeuron(inputNeuron1);
		inputLayer.addNeuron(inputNeuron2);

		// Inner Layer 1
		innerLayer1 = new InnerLayer();
		innerLayer1.addNeuron(innerNeuron11);
		innerLayer1.addNeuron(innerNeuron12);
		innerLayer1.addNeuron(innerNeuron13);

		// Output Layer
		outputLayer = new OutputLayer();
		outputLayer.addNeuron(outputNeuron1);

		ArrayList<double[]> inputList = new ArrayList<double[]>();
		ArrayList<double[]> outputList = new ArrayList<double[]>();
		
		inputList.add( new double[]{0.1,0});
		outputList.add(new double[]{0.4});
		
		inputList.add( new double[]{0.1,0});
		outputList.add(new double[]{0.4});
		
		inputList.add( new double[]{0.2,0});
		outputList.add(new double[]{0.3});

		inputList.add( new double[]{0.3,0});
		outputList.add(new double[]{0.2});

		inputList.add( new double[]{0.4,0});
		outputList.add(new double[]{0.1});

		inputList.add( new double[]{0.5,0});
		outputList.add(new double[]{0.0});
		
		Network network = new Network(inputLayer, outputLayer);
		network.setTrainingCycleListener( new ICycleListener() {
			
			public void handlerError(int cycleCounter, double totalMeanSquareError) {
				if( cycleCounter % 10000 == 0 ){
					System.err.println( "W:" + innerNeuron13.getNeuronValues(0).getW_t() + "    Err: " + totalMeanSquareError);
				}
			}
		});	
		
		network.setLearningRate( 0.3 );
		network.setMomentumCoefficient( 0.5 );
		network.addInnerLayer(innerLayer1);
		network.training(inputList, outputList, 0.0008);
		
		double input1 = 0.0;
		double input2 = 0.0;
		//double[] expected = new double[]{0.5};
		inputNeuron1.setInput(input1);
		inputNeuron2.setInput(input2);
		
		inputLayer.calculateSigmas();
		innerLayer1.calculateSigmas();
		outputLayer.calculateSigmas();
		
		System.out.println(inputLayer.toString());
		System.out.println(innerLayer1.toString());;
		System.out.println(outputLayer.toString());		
		
		

	}
	
	

	public static void main(String[] args) {
		new Example();

	}
}
