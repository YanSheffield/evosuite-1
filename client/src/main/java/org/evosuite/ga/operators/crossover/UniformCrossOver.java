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
package org.evosuite.ga.operators.crossover;

import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ConstructionFailedException;

/**
 * Implement uniform crossover 
 *
 * @author Yan Ge
 */
public class UniformCrossOver extends CrossOverFunction{

	private static final long serialVersionUID = 2981387570766261795L;
	
	@Override
	public void crossOver(Chromosome parent1, Chromosome bestMutant) throws ConstructionFailedException {
		if (parent1.size() < 2 || bestMutant.size() < 2) {
			return;
		}
		
		int parentSize = parent1.size();
		int bestMutantSize = bestMutant.size();
		//The reason why we need create two strings as follows is that we need
		//to know the passed parameter is best mutant or parent. The root reason is 
		//that the uniform crossover probability is different.That is, the probability of taking bites
		//from best mutant and parent is different. We need to identify them and allocate different
		//probability to them.
		String identifyMutant = "mutant";
		String identifyParent = "parent";
		
		Chromosome tparent = parent1.clone();
		Chromosome tbestMutant = bestMutant.clone();
		
		if(parentSize < bestMutantSize){
			parent1.uniformCrossOver(tbestMutant, identifyMutant);
		}else {
			bestMutant.uniformCrossOver(tparent,identifyParent);
		}
		
	}
}
