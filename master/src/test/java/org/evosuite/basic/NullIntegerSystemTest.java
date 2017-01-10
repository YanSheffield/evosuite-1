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
package org.evosuite.basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.evosuite.EvoSuite;
import org.evosuite.Properties;
import org.evosuite.Properties.Algorithm;
import org.evosuite.Properties.Criterion;
import org.evosuite.SystemTestBase;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.strategy.TestGenerationStrategy;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.Assert;
import org.junit.Test;

import com.examples.with.different.packagename.Calculator;
import com.examples.with.different.packagename.NullInteger;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.Static;


public class NullIntegerSystemTest extends SystemTestBase {

	@Test
	public void testNullInteger() {
		EvoSuite evosuite = new EvoSuite();

		String targetClass = NullInteger.class.getCanonicalName();
		
		Properties.TARGET_CLASS = targetClass;
		
		Properties.ALGORITHM = Algorithm.LAMBDAGA;
		Properties.CRITERION = new Criterion[] {  Criterion.LINE, Criterion.BRANCH, Criterion.EXCEPTION, Criterion.WEAKMUTATION, Criterion.OUTPUT, Criterion.METHOD, Criterion.METHODNOEXCEPTION, Criterion.CBRANCH };

		String[] command = new String[] { "-generateSuite", "-class", targetClass };

		Object result = evosuite.parseCommandLine(command);
		
		GeneticAlgorithm<?> ga = getGAFromResult(result);
		TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
		System.out.println("EvolvedTestSuite:\n" + best);

		int goals = TestGenerationStrategy.getFitnessFactories().get(0).getCoverageGoals().size(); // assuming single fitness function
		

//		Assert.assertEquals("Wrong number of goals: ", 3, goals);
//		Assert.assertEquals("Non-optimal coverage: ", 1d, best.getCoverage(), 0.001);
//		List<Integer> changeBite = new ArrayList<>();
//		Set<Integer> changeBite = new HashSet<>();
// 		for(int i = 0;i<5;i++){
//		int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
//		changeBite.add(randomNum);
//		
//		}
//		while(changeBite.size()<5){
//			int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
//			changeBite.add(randomNum);
//	}
//		System.out.println(changeBite);
//		for(int i:changeBite){
//			System.out.println("i"+i);
//		}
//	public static void main(String[] args) {
////		System.out.println(bia());
//		makelist();
//	}
////	
//	public static int getBinomial(int n, double p) {
//		  int x = 0;
//		  for(int i = 0; i < n; i++) {
//		    if(Math.random() < p)
//		      x++;
//		  }
//		  return x;
//		}
//	public static int bia(){
//		BinomialDistribution bi = new BinomialDistribution(9,1d/3d);
//		return bi.sample();
//	}
	
//	public static <T> void makelist(T...args){
//		System.out.println(args);
//		if (args==null) {
//			System.out.println("Null");
//		}
//	}
}
	
}
