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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * (1+lambda)EA implementation 
 * 
 * @author Yan Ge
 */
public class OnePlusLambdaEA<T extends Chromosome> extends GeneticAlgorithm<T> {
	
	private static final long serialVersionUID = 5229089847512798127L;
	
	private static final Logger logger = LoggerFactory.getLogger(OnePlusLambdaEA.class);
	
	public OnePlusLambdaEA(ChromosomeFactory<T> factory) {
		super(factory);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void evolve() {
		List<T> mutants = new ArrayList<T>();

		T parent = (T) population.get(0).clone();
		
		while (!isNextPopulationFull(mutants)) {
			T mutationOffspring = (T) parent.clone();
			//execute mutation operation
			notifyMutation(mutationOffspring);			
			do{
				mutationOffspring.mutate();
			}while(!mutationOffspring.isChanged());
			
			mutants.add(mutationOffspring);
		}
		population = mutants;
		updateFitnessFunctionsAndValues();
		calculateFitnessAndSortPopulation();
		T bestMutant = getBestIndividual();
		//select the individual as the parent for next iteration
		if (isBetterOrEqual(bestMutant, parent)) {
			population.set(0, bestMutant);
		}else {
			population.set(0, parent);
		}
		currentIteration++;
	}

	@Override
	public void initializePopulation() {
		notifySearchStarted();
		currentIteration = 0;
		// initialize one size parent
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
		logger.debug("Starting evolution");
		while (!isFinished()) {
			logger.debug("Current population: " + getAge() + "/" + Properties.SEARCH_BUDGET);
			logger.info("Best fitness: " + getBestIndividual().getFitness());
			evolve();
			// Determine fitness
			calculateFitnessAndSortPopulation();
			applyLocalSearch();

			this.notifyIteration();
		}
		updateBestIndividualFromArchive();
		notifySearchFinished();
	}

}
