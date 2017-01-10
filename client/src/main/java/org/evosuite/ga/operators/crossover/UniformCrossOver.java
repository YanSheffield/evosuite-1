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
		String m = "mutant";
		String p = "parent";
		Chromosome tparent = parent1.clone();
		Chromosome tbestMutant = bestMutant.clone();
		if(parentSize <= bestMutantSize){
			tparent.uniformCrossOver(tbestMutant,parentSize,m);
		}else {
			tbestMutant.uniformCrossOver(tparent,bestMutantSize,p);
		}
		
	}

//	@Override
//	public void uniformCrossOver(Chromosome crossOverOffspring, Chromosome parent, Chromosome bestMutant)
//			throws ConstructionFailedException {
//		if (parent.size() < 2 || bestMutant.size() < 2) {
//			return;
//		}
//		Chromosome tcrossOverOffspring = crossOverOffspring.clone();
//		Chromosome tparent = parent.clone();
//		Chromosome tbestMutant = bestMutant.clone();
////		System.out.println("crossOverSize1 "+crossOverOffspring.size());
//		tcrossOverOffspring.uniformCrossOver(tparent, tbestMutant);
////		System.out.println("crossOverSize2 "+crossOverOffspring.size());
////		System.out.println(tparent.equals(tparent));
////		System.out.println(tcrossOverOffspring.equals(tbestMutant));
//	}

}
