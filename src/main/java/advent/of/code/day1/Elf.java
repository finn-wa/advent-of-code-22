package advent.of.code.day1;

import static java.util.stream.Collectors.summingInt;

import java.util.List;

public class Elf {
	public final List<Integer> items;
	public final int totalCalories;

	public Elf(List<Integer> items) {
		this.items = items;
		this.totalCalories = items.stream().collect(summingInt(i -> i));
	}

	public List<Integer> getItems() {
		return items;
	}

	public int getTotalCalories() {
		return totalCalories;
	}

	@Override
	public String toString() {
		return "Elf [items=" + items + ", totalCalories=" + totalCalories + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + totalCalories;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elf other = (Elf) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (totalCalories != other.totalCalories)
			return false;
		return true;
	}

}
