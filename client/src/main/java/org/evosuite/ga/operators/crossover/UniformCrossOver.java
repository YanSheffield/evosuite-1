package org.evosuite.ga.operators.crossover;

import java.util.List;

import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.testsuite.TestSuiteChromosome;

public class UniformCrossOver extends CrossOverFunction<Chromosome>{

	
	@Override
	public void crossOver(Chromosome parent1, Chromosome bestMutant) throws ConstructionFailedException {
		if (parent1.size() < 2 || bestMutant.size() < 2) {
			return;
		}
		
		int parentSize = parent1.size();
		int bestMutantSize = bestMutant.size();
		String identifyMutant = "mutant";
		String identifyParent = "parent";
		Chromosome tparent = parent1.clone();
		Chromosome tbestMutant = bestMutant.clone();
		
		if(parentSize < bestMutantSize){
			tparent.uniformCrossOver(tbestMutant,identifyMutant);
		}else {
			tbestMutant.uniformCrossOver(tparent,identifyParent);
		}
		
	}
}
