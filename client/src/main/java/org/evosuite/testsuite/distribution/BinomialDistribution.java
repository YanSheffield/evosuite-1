package org.evosuite.testsuite.distribution;

public class BinomialDistribution {

	private int testSize;
	private double mutationProbability;
	
	public BinomialDistribution(int testSzie,double mutationProbability){
		this.testSize = testSzie;
		this.mutationProbability = mutationProbability;
	}
	
	public int sample(){
		//The number of selected bite used for mutation operator
		int numberSample = 0;	
		for(int i = 0;i<testSize;i++){
			if (mutationProbability > Math.random()) {
				numberSample++;
			}
		}	
		return numberSample;
	}
}
