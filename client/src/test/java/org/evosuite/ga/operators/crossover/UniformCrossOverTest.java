package org.evosuite.ga.operators.crossover;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.DummyChromosome;
import org.junit.Test;

public class UniformCrossOverTest {

	@Test
	public void testUniformCrossOver() throws ConstructionFailedException{
//		for(int i = 0;i<100;i++){
		DummyChromosome parent = new DummyChromosome(1, 2, 3, 4, 5, 6, 7);
	    DummyChromosome bestMutant = new DummyChromosome(8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
	   
	    UniformCrossOver uniformCrossOver = new UniformCrossOver();
	    
	    DummyChromosome offspring1 = new DummyChromosome(parent);
	    DummyChromosome offspring2 = new DummyChromosome(bestMutant);
	    
//	    System.out.println("Offspring1 "+offspring1.getGenes());
	    uniformCrossOver.crossOver(offspring1, offspring2);	      
	    System.out.println("Offspring1 "+offspring1.getGenes());
	    }

//	    assertEquals(Arrays.asList(1), offspring1.getGenes().get(0));
//	    assertEquals(Arrays.asList(5, 3, 4), offspring2.getGenes());
//	}

}
