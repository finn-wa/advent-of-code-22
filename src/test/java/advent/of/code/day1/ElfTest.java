package advent.of.code.day1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class ElfTest {
	@Test
	public void testElf() {
		final var items = List.of(1000, 2000, 3000);
		final Elf elf = new Elf(items);
		assertThat(elf.totalCalories).isEqualTo(6000);
		assertThat(elf.items).isEqualTo(items);
	}

}
