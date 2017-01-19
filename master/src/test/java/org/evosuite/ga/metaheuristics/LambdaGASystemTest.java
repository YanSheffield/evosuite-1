package org.evosuite.ga.metaheuristics;

import static org.junit.Assert.*;

import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.SystemTestBase;
import org.evosuite.Properties.Algorithm;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.Calculator;

public class LambdaGASystemTest extends SystemTestBase{

	@Test
	public void testLambdaGAIntegration() {
		Properties.ALGORITHM = Algorithm.LAMBDAGA;
		
		EvoSuite evoSuite = new EvoSuite();
		
		String targetClass = Calculator.class.getCanonicalName();
		
		Properties.TARGET_CLASS = targetClass;
		
		String[] command = new String[]{"-generateSuite","-class",targetClass};
		
		Object result = evoSuite.parseCommandLine(command);
		
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);
		Assert.assertEquals(0.0,best.getFitness(), 0.0);
		Assert.assertEquals(1d, best.getCoverage(),0.001);
	}

}
