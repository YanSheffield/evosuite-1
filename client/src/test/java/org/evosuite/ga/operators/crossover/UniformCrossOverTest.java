package org.evosuite.ga.operators.crossover;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.DummyChromosome;
import org.junit.Test;

/**
 * 
 * @author Yan Ge
 *
 */
public class UniformCrossOverTest {

	@Test
	public void testUniformCrossOver() throws ConstructionFailedException {
		DummyChromosome parent_short = new DummyChromosome(1, 2, 3, 4, 5, 6, 7);
		DummyChromosome bestMutant_long = new DummyChromosome(8, 9, 10, 11, 12, 13, 14, 15, 16);

		UniformCrossOver uniformCrossOver = new UniformCrossOver();

		DummyChromosome offspring1 = new DummyChromosome(parent_short);
		DummyChromosome offspring2 = new DummyChromosome(bestMutant_long);
		
		uniformCrossOver.crossOver(offspring1, offspring2);

		for (int i = 0; i < parent_short.size(); i++) {
			assertTrue(offspring1.getGenes().get(i).equals(parent_short.getGenes().get(i))
					|| offspring1.getGenes().get(i).equals(bestMutant_long.getGenes().get(i)));
		}
		
		//For extra part of offspring
		for(int i = parent_short.size();i< offspring1.size();i++){
			assertTrue(offspring1.getGenes().get(i).equals(bestMutant_long.getGenes().get(i))||
					offspring1.getGenes().get(i).equals(bestMutant_long.getGenes().get(i+1)));
		}
	}

	@Test	
	public void testUniformCrossverOtherSenario() throws ConstructionFailedException {
		DummyChromosome parent_long = new DummyChromosome(1,2,3,4,5,6,7,8,9,10);
		DummyChromosome bestMutant_short = new DummyChromosome(11,12,13,14,15,16,17,18);
		
		UniformCrossOver uniformCrossOver = new UniformCrossOver();
		
		DummyChromosome offspring1 = new DummyChromosome(parent_long);
		DummyChromosome offspring2 = new DummyChromosome(bestMutant_short);
		
		uniformCrossOver.crossOver(offspring1, offspring2);
		
		for(int i = 0;i < bestMutant_short.size(); i++ ){
			
			assertTrue(offspring2.getGenes().get(i).equals(parent_long.getGenes().get(i))||
					offspring2.getGenes().get(i).equals(bestMutant_short.getGenes().get(i)));
		}
		
		//for extra part
		for(int i = bestMutant_short.size();i<offspring2.size();i++){
			assertTrue(offspring2.getGenes().get(i).equals(parent_long.getGenes().get(i))
					||offspring2.getGenes().get(i).equals(parent_long.getGenes().get(i+1)));
		}
	}
	
}
