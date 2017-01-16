package org.evosuite.ga;

import org.evosuite.Properties;
import org.evosuite.ga.localsearch.LocalSearchObjective;
import org.evosuite.utils.Randomness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by gordon on 14/05/2016.
 */
public class DummyChromosome extends Chromosome {

	private List<Integer> values = new ArrayList<Integer>();

	public DummyChromosome(int... values) {
		for (int x : values) {
			this.values.add(x);
		}
	}

	public DummyChromosome(Collection<Integer> values) {
		this.values.addAll(values);
	}

	public DummyChromosome(DummyChromosome other) {
		this.values.addAll(other.values);
	}

	@Override
	public Chromosome clone() {
		return new DummyChromosome(values);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		DummyChromosome that = (DummyChromosome) o;

		return values != null ? values.equals(that.values) : that.values == null;

	}

	@Override
	public int hashCode() {
		return values != null ? values.hashCode() : 0;
	}

	@Override
	public <T extends Chromosome> int compareSecondaryObjective(T o) {
		return 0;
	}

	@Override
	public void mutate() {
		if (values.isEmpty())
			return;

		double P = 1.0 / values.size();
		for (int i = 0; i < values.size(); i++) {
			if (Randomness.nextDouble() < P) {
				values.set(i, Randomness.nextInt());
			}
		}
		this.setChanged(true);
	}

	public int get(int position) {
		return values.get(position);
	}

	public List<Integer> getGenes() {
		return values;
	}

	@Override
	public boolean localSearch(LocalSearchObjective<? extends Chromosome> objective) {
		return false;
	}

	@Override
	public int size() {
		return values.size();
	}

	@Override
	public void mutateWithHighProbability() {
		// TODO Auto-generated method stub

	}

	@Override
	public void crossOver(Chromosome other, int position1, int position2) throws ConstructionFailedException {
		DummyChromosome chromosome = (DummyChromosome) other;
		
		while (values.size() > position1) {
			values.remove(position1);
		}

		for (int num = position2; num < other.size(); num++) {
			values.add(chromosome.get(num));
		}

		this.setChanged(true);
	}

	@Override
	public void uniformCrossOver(Chromosome other, String identify) throws ConstructionFailedException {
		double probilityBitesFromMutant = (1.0 / values.size()) * (1.0 / Properties.HIGH_MUTATION_PROBOBILITY);
		DummyChromosome chromosome = (DummyChromosome) other;
		//初始化对象的的时候已经给 value 值了，这个对象在调用其它方法包含初始化
		//的时候，在其他方法里都默认为初始化的值
		for (int i = 0; i < values.size(); i++) {
			
			if (identify.equals("mutant")) {		
				if (Randomness.nextDouble() <= probilityBitesFromMutant) {
					values.remove(i);
					values.add(i,chromosome.values.get(i));
				}
			} else {
				if (Randomness.nextDouble() <= 1 - probilityBitesFromMutant) {
					values.remove(i);
					values.add(i,chromosome.values.get(i));
				}
			}
		}
		for (int i = values.size(); i < other.size(); i++) {
			if (identify.equals("mutant")) {
				if (Randomness.nextDouble() <= probilityBitesFromMutant) {
					values.add(chromosome.values.get(i));
				}
			}else {
				if (Randomness.nextDouble() <= 1-probilityBitesFromMutant){
					values.add(chromosome.values.get(i));
				}
			}
		};
		this.setChanged(true);
	}

}
