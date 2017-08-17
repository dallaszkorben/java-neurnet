package hu.akoel.neurnet;

import java.util.ArrayList;

import layer.IInnerLayer;
import layer.IInputLayer;
import layer.IOutputLayer;
import layer.InnerLayer;
import layer.InputLayer;
import layer.OutputLayer;
import neuron.IInputNeuron;
import neuron.INormalNeuron;
import neuron.InputNeuron;
import neuron.NormalNeuron;

public class Test {
	IInputNeuron inputNeuron1;
	IInputNeuron inputNeuron2;

	INormalNeuron innerNeuron11;
	INormalNeuron innerNeuron12;
	INormalNeuron innerNeuron13;
	INormalNeuron innerNeuron14;
	INormalNeuron innerNeuron15;
	INormalNeuron innerNeuron16;
	
	INormalNeuron innerNeuron21;
	INormalNeuron innerNeuron22;
	INormalNeuron innerNeuron23;
	INormalNeuron innerNeuron24;
	INormalNeuron innerNeuron25;
	INormalNeuron innerNeuron26;
	
	INormalNeuron outputNeuron1;
	
	IInputLayer inputLayer;
	IInnerLayer innerLayer1;
//	IInnerLayer innerLayer2;
	IOutputLayer outputLayer;

	public Test() {


		inputNeuron1 = new InputNeuron();
		inputNeuron2 = new InputNeuron();

		innerNeuron11 = new NormalNeuron();
		innerNeuron12 = new NormalNeuron();
		innerNeuron13 = new NormalNeuron();
		innerNeuron14 = new NormalNeuron();
		innerNeuron15 = new NormalNeuron();
		innerNeuron16 = new NormalNeuron();
		
		innerNeuron21 = new NormalNeuron();
		innerNeuron22 = new NormalNeuron();
		innerNeuron23 = new NormalNeuron();
		innerNeuron24 = new NormalNeuron();
		innerNeuron25 = new NormalNeuron();
		innerNeuron26 = new NormalNeuron();
		
		outputNeuron1 = new NormalNeuron();

		// Input Layer
		inputLayer = new InputLayer();
		inputLayer.addNeuron(inputNeuron1);
		inputLayer.addNeuron(inputNeuron2);

		// Inner Layer 1
		innerLayer1 = new InnerLayer(inputLayer);
		innerLayer1.addNeuron(innerNeuron11);
		innerLayer1.addNeuron(innerNeuron12);
		innerLayer1.addNeuron(innerNeuron13);
//		innerLayer1.addNeuron(innerNeuron14);
//		innerLayer1.addNeuron(innerNeuron15);
//		innerLayer1.addNeuron(innerNeuron16);

		// Inner Layer 2
//		innerLayer2 = new InnerLayer(innerLayer1);
//		innerLayer2.addNeuron(innerNeuron21);
//		innerLayer2.addNeuron(innerNeuron22);
//		innerLayer2.addNeuron(innerNeuron23);
//		innerLayer2.addNeuron(innerNeuron24);
//		innerLayer2.addNeuron(innerNeuron25);
//		innerLayer2.addNeuron(innerNeuron26);

		// Output Layer
		outputLayer = new OutputLayer(innerLayer1);
		outputLayer.addNeuron(outputNeuron1);

		//
		// Calculation
		//

		double input1;
		double input2;
		ArrayList<Double> expected;
		
		for( int i = 1; i < 10000000; i++){
			
			double sumErr = 0;
			
			input1 = 0.1;
			input2 = 0;		
			expected = new ArrayList<Double>();
			expected.add(0.4);
			sumErr += cycle(input1, input2, expected);
			
			input1 = 0.1;
			input2 = 0;		
			expected = new ArrayList<Double>();
			expected.add(0.4);
			sumErr += cycle(input1, input2, expected);
			
			input1 = 0.2;
			input2 = 0;		
			expected = new ArrayList<Double>();
			expected.add(0.3);
			sumErr += cycle(input1, input2, expected);

			input1 = 0.3;
			input2 = 0;		
			expected = new ArrayList<Double>();
			expected.add(0.2);
			sumErr += cycle(input1, input2, expected);

			input1 = 0.4;
			input2 = 0;		
			expected = new ArrayList<Double>();
			expected.add(0.1);
			sumErr += cycle(input1, input2, expected);

			input1 = 0.5;
			input2 = 0;		
			expected = new ArrayList<Double>();
			expected.add(0.0);
			sumErr += cycle(input1, input2, expected);
			
			sumErr = sumErr / 6;
			if( i % 10000 == 0 ){
				System.err.println( "Total Mean Square Error: " + sumErr);
			}
			
			if( sumErr <= 0.0001){
				break;
			}

		}
		
		
		input1 = 0.0;
		input2 = 0.0;
		expected = new ArrayList<Double>();
		expected.add(0.1);
		inputNeuron1.setInput(input1);
		inputNeuron2.setInput(input2);
		
		inputLayer.calculateOutputs();
		innerLayer1.calculateOutputs();
//		innerLayer2.calculateOutputs();
		outputLayer.calculateOutputs();
		
		outputLayer.calculateWeights(expected);
		
		System.out.println(inputLayer.toString());
		System.out.println(innerLayer1.toString());;
		System.out.println(outputLayer.toString());		
		
		

	}

	private double cycle(double input1, double input2, ArrayList<Double> expected) {

		// OUTPUT(t)
		inputNeuron1.setInput(input1);
		inputNeuron2.setInput(input2);

		inputLayer.calculateOutputs();
		innerLayer1.calculateOutputs();
//		innerLayer2.calculateOutputs();
		outputLayer.calculateOutputs();

		// DELTA(t+1)
		outputLayer.calculateWeights(expected);
//		innerLayer2.calculateWeights(outputLayer);
		innerLayer1.calculateWeights(outputLayer);
		inputLayer.calculateWeights(innerLayer1);

		inputLayer.calculateOutputs();
		innerLayer1.calculateOutputs();
//		innerLayer2.calculateOutputs();
		outputLayer.calculateOutputs();
		
			
		return Math.pow( outputNeuron1.getSigma() - expected.get(0), 2 );
		//System.err.println(inputLayer.toString());
		//System.err.println(outputLayer.toString());

	}

	public static void main(String[] args) {
		Test test = new Test();

	}
}
