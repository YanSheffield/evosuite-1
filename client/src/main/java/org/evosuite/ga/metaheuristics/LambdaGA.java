package org.evosuite.ga.metaheuristics;

import java.util.ArrayList;
import java.util.List;

import org.evosuite.Properties;
import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ChromosomeFactory;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.ga.FitnessFunction;
import org.evosuite.ga.operators.crossover.CrossOverFunction;
import org.evosuite.ga.operators.crossover.SinglePointCrossOver;
import org.evosuite.ga.operators.crossover.UniformCrossOver;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.LoggingUtils;

public class LambdaGA<T extends Chromosome> extends GeneticAlgorithm<T> {
	// what is used for??
	private static final long serialVersionUID = 529089847512798127L;
	// new TestSuiteChromosomeFactory(new ArchiveTestChromosomeFactory());
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LambdaGA.class);

	protected UniformCrossOver crossoverFunction = new UniformCrossOver();

	public LambdaGA(ChromosomeFactory<T> factory) {
		super(factory);// call parent's constructor
	}

	@Override
	protected void evolve() {

		List<T> mutants = new ArrayList<T>();
		
		T parent = (T) population.get(0).clone();
		// control the number of offspring
		while (!isNextPopulationFull(mutants)) {
			// clone offspring from parent
			T MutationOffspring = (T) parent.clone();
//			System.out.println("before mutation size "+MutationOffspring.size());
			// operate mutation before crossover operation
			notifyMutation(MutationOffspring);
			MutationOffspring.mutateWithHighProbability();
			// newGeneration = getBestIndividuals();
//			System.out.println("After mutation size "+MutationOffspring.size());
//			System.out.println("--"+MutationOffspring.equals(parent));
			mutants.add(MutationOffspring);
		}
		population = mutants;

		updateFitnessFunctionsAndValues();
		calculateFitnessAndSortPopulation();
		T bestMutant = getBestIndividual();
		// uniform crossover phrase
		List<T> crossoverOffspring = new ArrayList<T>();

		while (!isNextPopulationFull(crossoverOffspring)) {
			try {			
				//question one. The number of test case
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
		calculateFitnessAndSortPopulation();
		T bestCrossoverOffspring = getBestIndividual();

		if(isBetterOrEqual(bestCrossoverOffspring,parent)){
			population.set(0, bestCrossoverOffspring);
		}else{
			population.set(0, parent);
		}
		updateFitnessFunctionsAndValues();
		currentIteration++;
	}

	@Override
	public void initializePopulation() {
		notifySearchStarted();
		currentIteration = 0;
		// Only one parent produced
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
			updateFitnessFunctionsAndValues();

			this.notifyIteration();
		}
		updateBestIndividualFromArchive();
		notifySearchFinished();
	}

}
