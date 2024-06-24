package meschaefer.fighter.ranking.data.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import meschaefer.fighter.ranking.data.jpa.model.Bout;
import meschaefer.fighter.ranking.data.jpa.model.WeightClass;
import meschaefer.fighter.ranking.data.jpa.repository.FighterRepository;
import meschaefer.fighter.ranking.data.service.BoutService;

@SpringBootTest
@Sql(scripts = "setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class BoutServiceTests {

	@Autowired
	private FighterRepository fighterRepository;

	@Autowired
	private BoutService boutService;

	private Bout bout;

	@BeforeEach
	public void setup() {
		bout = new Bout();
		bout.setBoutDate(LocalDate.now());
		bout.setFighter1(fighterRepository.findAll().get(0));
		bout.setFighter2(fighterRepository.findAll().get(1));
		bout.setLocation("T-Mobile Arena");
		bout.setWeightClass(WeightClass.HEAVYWEIGHT);
	}

	@Test
	void add() {
		Bout added = boutService.addBout(bout);
		assertTrue(boutService.get(added.getBoutId()).isPresent(), "Bout wasn't found.");
	}

	@Test
	void add_null() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> boutService.addBout(null),
				"Null arguments not allowed");

		assertEquals("Bout cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullBoutDate() {
		bout.setBoutDate(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> boutService.addBout(bout),
				"Null arguments not allowed");

		assertEquals("Bout Date cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullId() {
		// we'd expect this to be set by service.
		bout.setBoutId(null);

		Bout added = boutService.addBout(bout);

		assertNotNull(added.getBoutId());
	}

	@Test
	void add_nullLocation() {
		bout.setLocation(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> boutService.addBout(bout),
				"Null arguments not allowed");

		assertEquals("Location cannot be null", thrown.getMessage());
	}

	@Test
	void add_blankLocation() {
		bout.setLocation("    ");

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> boutService.addBout(bout),
				"Null arguments not allowed");

		assertEquals("Location cannot be blank", thrown.getMessage());
	}

	@Test
	void add_nullWeightClass() {
		bout.setWeightClass(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> boutService.addBout(bout),
				"Null arguments not allowed");

		assertEquals("Weight Class cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullFighter1() {
		bout.setFighter1(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> boutService.addBout(bout),
				"Null arguments not allowed");

		assertEquals("Fighter One cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullFighter2() {
		bout.setFighter2(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> boutService.addBout(bout),
				"Null arguments not allowed");

		assertEquals("Fighter Two cannot be null", thrown.getMessage());
	}

	@Test
	void addAll() {
		List<Bout> added = boutService.addBouts(Arrays.asList(bout));
		assertTrue(boutService.get(added.get(0).getBoutId()).isPresent(), "Bout wasn't found.");
	}

	@Test
	void addAll_validateFailed_rollback() {
		List<Bout> boutList = new ArrayList<>();
		boutList.add(bout);
		boutList.add(bout);

		bout.setBoutDate(null); // last bout is invalid
		boutList.add(bout);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.addBouts(boutList), "Null arguments not allowed");

		assertEquals("Bout Date cannot be null", thrown.getMessage());
	}

	@Test
	void delete() {
		Bout added = boutService.addBout(bout);
		assertTrue(boutService.get(added.getBoutId()).isPresent(), "Bout wasn't found.");

		boutService.delete(added.getBoutId());
		assertFalse(boutService.get(added.getBoutId()).isPresent(), "Bout wasn't deleted.");
	}

	@Test
	void get() {
		Bout added = boutService.addBout(bout);
		assertTrue(boutService.get(added.getBoutId()).isPresent(), "Bout wasn't found.");
	}

	@Test
	void update() {
		Bout added = boutService.addBout(bout);

		added.setWeightClass(WeightClass.BANTAMWEIGHT);

		Bout updated = boutService.updateBout(added);
		assertEquals(WeightClass.BANTAMWEIGHT, updated.getWeightClass());
	}

	@Test
	void update_null() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(null), "Null arguments not allowed");

		assertEquals("Bout cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullBoutDate() {
		Bout added = boutService.addBout(bout);
		added.setBoutDate(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(added), "Null arguments not allowed");

		assertEquals("Bout Date cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullId() {
		Bout added = boutService.addBout(bout);
		added.setBoutId(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(added), "Null arguments not allowed");

		assertEquals("Bout ID cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullFighter1() {
		Bout added = boutService.addBout(bout);
		added.setFighter1(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(added), "Null arguments not allowed");

		assertEquals("Fighter One cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullFighter2() {
		Bout added = boutService.addBout(bout);
		added.setFighter2(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(added), "Null arguments not allowed");

		assertEquals("Fighter Two cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullLocation() {
		Bout added = boutService.addBout(bout);
		added.setLocation(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(added), "Null arguments not allowed");

		assertEquals("Location cannot be null", thrown.getMessage());
	}

	@Test
	void update_blankLocation() {
		Bout added = boutService.addBout(bout);
		added.setLocation("    ");

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(added), "Null arguments not allowed");

		assertEquals("Location cannot be blank", thrown.getMessage());
	}

	@Test
	void update_nullWeightClass() {
		Bout added = boutService.addBout(bout);
		added.setWeightClass(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBout(added), "Null arguments not allowed");

		assertEquals("Weight Class cannot be null", thrown.getMessage());
	}

	@Test
	void updateAll() {
		Bout added = boutService.addBout(bout);

		added.setWeightClass(WeightClass.FLYWEIGHT);

		List<Bout> updated = boutService.updateBouts(Arrays.asList(added));
		assertEquals(WeightClass.FLYWEIGHT, updated.get(0).getWeightClass());
	}

	@Test
	void updateAll_validateFailed_rollback() {
		Bout added = boutService.addBout(bout);

		List<Bout> boutList = new ArrayList<>();
		boutList.add(added);
		boutList.add(added);

		added.setWeightClass(null); // last bout is invalid
		boutList.add(added);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> boutService.updateBouts(boutList), "Null arguments not allowed");

		assertEquals("Weight Class cannot be null", thrown.getMessage());
	}

}
