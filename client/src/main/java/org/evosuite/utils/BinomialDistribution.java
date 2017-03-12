/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.utils;
/**
 * Implementation of binomial distribution
 */
public class BinomialDistribution {

	private int testSize;
	private double mutationProbability;
	
	public BinomialDistribution(int testSzie,double mutationProbability){
		this.testSize = testSzie;
		this.mutationProbability = mutationProbability;
	}
	
	public int sample(){
		//The number of selected bite used for mutation operator for lambda GA
		int numberSample = 0;	
		for(int i = 0;i<testSize;i++){
			if (mutationProbability > Math.random()) {
				numberSample++;
			}
		}	
		return numberSample;
	}
}
