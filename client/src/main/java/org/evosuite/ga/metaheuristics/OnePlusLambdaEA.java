package org.evosuite.ga.metaheuristics;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ChromosomeFactory;

public class OnePlusLambdaEA<T extends Chromosome> extends GeneticAlgorithm<T> {
	
	private static final long serialVersionUID = 5229089847512798127L;
	
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OnePlusOneEA.class);
	
	public OnePlusLambdaEA(ChromosomeFactory<T> factory) {
		super(factory);
	}

	@Override
	protected void evolve() {
		List<T> mutants = new ArrayList<T>();

		T parent = (T) population.get(0).clone();
		
		while (!isNextPopulationFull(mutants)) {
			T mutationOffspring = (T) parent.clone();
			
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
		calculateFitnessAndSortPopulation();
		updateFitnessFunctionsAndValues();
		this.notifyIteration();
		logger.info("Initial fitness: " + population.get(0).getFitness());		
	}

	@Override
	public void generateSolution() {
		if (Properties.ENABLE_SECONDARY_OBJECTIVE_AFTER > 0 || Properties.ENABLE_SECONDARY_OBJECTIVE_STARVATION) {
			disableFirstSecondaryCriterion();
		}

		if (population.isEmpty()) {// All of results are stored in population
			initializePopulation();
		}
		logger.debug("Starting evolution");
		while (!isFinished()) {
			logger.debug("Current population: " + getAge() + "/" + Properties.SEARCH_BUDGET);
			logger.info("Best fitness: " + getBestIndividual().getFitness());
			evolve();
			applyLocalSearch();
			updateFitnessFunctionsAndValues();

			this.notifyIteration();
		}
		updateBestIndividualFromArchive();
		notifySearchFinished();
	}

}
