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
package org.evosuite.ga.metaheuristics;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (1+(lambda,lambda))GA implementation 
 * 
 * @author Yan Ge
 */
public class LambdaGA<T extends Chromosome> extends GeneticAlgorithm<T> {
	
	private static final long serialVersionUID = 529089847512798127L;
	
	private static final Logger logger = LoggerFactory.getLogger(LambdaGA.class);

	public LambdaGA(ChromosomeFactory<T> factory) {
		super(factory);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void evolve() {

		List<T> mutants = new ArrayList<T>();
		
		T parent = (T) population.get(0).clone();
		// control the number of offspring
		while (!isNextPopulationFull(mutants)) {
			
			// clone firstly offspring from parent
			T MutationOffspring = (T) parent.clone();
			notifyMutation(MutationOffspring);
			
			//perform mutation operation with high probability 
			MutationOffspring.mutate();
			mutants.add(MutationOffspring);
		}
		//mutants are evaluated as current population so that the best mutant
		//can be selected
		population = mutants;

		updateFitnessFunctionsAndValues();
		calculateFitnessAndSortPopulation();
		
		//obtain the best mutant
		T bestMutant = getBestIndividual();
		
		// start to execute uniform crossover operator
		List<T> crossoverOffspring = new ArrayList<T>();

		while (!isNextPopulationFull(crossoverOffspring)) {
			try {			
				//the individual which has the shorter size is chosen as 
				//the model basis of uniform crossover.The reason why I do this is that the size between 
				//best mutant and parent maybe different after mutation operation, which is the biggest difference 
				//from standard uniform crossover. This shorter individual will be basis model, and take bites(test cases)
				//from parent or keep itself with crossover probability. 
				if (parent.size() < bestMutant.size()) {
					crossoverFunction.crossOver(parent, bestMutant);
					crossoverOffspring.add(parent);
				} else {
					crossoverFunction.crossOver(parent, bestMutant);
					crossoverOffspring.add(bestMutant);
				}
			} catch (ConstructionFailedException e) {
				logger.info("CrossOver failed.");
				continue;
			}
		}
		
		population = crossoverOffspring;
		updateFitnessFunctionsAndValues();
		T bestCrossoverOffspring = getBestIndividual();

		// judge which one will be regarded as the parent for next iteration
		if(isBetterOrEqual(bestCrossoverOffspring, parent)){
			population.set(0, bestCrossoverOffspring);
		}else{
			population.set(0, parent);
		}
		currentIteration++;
	}

	@Override
	public void initializePopulation() {
		notifySearchStarted();
		currentIteration = 0;
		//Initialize one size parent
		generateRandomPopulation(1);
		// Determine fitness
		calculateFitnessAndSortPopulation();
		this.notifyIteration();
		logger.info("Initial fitness: " + population.get(0).getFitness());
	}

	@Override
	public void generateSolution() {
		if (Properties.ENABLE_SECONDARY_OBJECTIVE_AFTER > 0 || Properties.ENABLE_SECONDARY_OBJECTIVE_STARVATION) {
			disableFirstSecondaryCriterion();
		}

		if (population.isEmpty()) {
			initializePopulation();
		}
		int starvationCounter = 0;
		double bestFitness = Double.MAX_VALUE;
		double lastBestFitness = Double.MAX_VALUE;
		
		if (getFitnessFunction().isMaximizationFunction()) {
			bestFitness = 0.0;
			lastBestFitness = 0.0;
		}
		while (!isFinished()) {
			logger.debug("Current population: " + getAge() + "/" + Properties.SEARCH_BUDGET);
			logger.info("Best fitness: " + getBestIndividual().getFitness());
			
			evolve();
			
			applyLocalSearch();
			
			double newFitness = getBestIndividual().getFitness();
	
			if (getFitnessFunction().isMaximizationFunction())
				assert (newFitness >= bestFitness) : "best fitness was: " + bestFitness + ", now best fitness is "
						+ newFitness;
			else
				assert (newFitness <= bestFitness) : "best fitness was: " + bestFitness + ", now best fitness is "
						+ newFitness;
			bestFitness = newFitness;

			if (Double.compare(bestFitness, lastBestFitness) == 0) {
				starvationCounter++;
			} else {
				logger.info("reset starvationCounter after " + starvationCounter + " iterations");
				starvationCounter = 0;
				lastBestFitness = bestFitness;
			}

			updateSecondaryCriterion(starvationCounter);

			this.notifyIteration();
		}
		updateBestIndividualFromArchive();
		notifySearchFinished();
	}

}
