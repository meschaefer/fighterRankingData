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

import meschaefer.fighter.ranking.data.jpa.model.Fighter;
import meschaefer.fighter.ranking.data.service.FighterService;

@SpringBootTest
@Sql(scripts = "setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class FighterServiceTests {

	@Autowired
	private FighterService fighterService;

	private Fighter fighter;
	
	@BeforeEach
	public void setup() {
		fighter = new Fighter();
		fighter.setDateOfBirth(LocalDate.now());
		fighter.setFighterId(null);
		fighter.setFirstName("Steve");
		fighter.setLastName("Minecraft");
		fighter.setMiddleName("Um");
		fighter.setNationality("Sweden");
	}

	@Test
	void add() {
		Fighter added = fighterService.addFighter(fighter);
		assertTrue(fighterService.get(added.getFighterId()).isPresent(), "Fighter wasn't found.");
	}

	@Test
	void add_null() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.addFighter(null), "Null arguments not allowed");

		assertEquals("Fighter cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullDOB() {
		fighter.setDateOfBirth(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.addFighter(fighter), "Null arguments not allowed");

		assertEquals("Date of Birth cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullId() {
		// we'd expect this to be set by service.
		fighter.setFighterId(null);

		Fighter added = fighterService.addFighter(fighter);

		assertNotNull(added.getFighterId());
	}

	@Test
	void add_nullFirstName() {
		fighter.setFirstName(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.addFighter(fighter), "Null arguments not allowed");

		assertEquals("First Name cannot be null", thrown.getMessage());
	}
	
	@Test
	void add_blankFirstName() {
		fighter.setFirstName("   ");

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.addFighter(fighter), "Null arguments not allowed");

		assertEquals("First Name cannot be blank", thrown.getMessage());
	}
	
	@Test
	void add_nullLastName() {
		fighter.setLastName(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.addFighter(fighter), "Null arguments not allowed");

		assertEquals("Last Name cannot be null", thrown.getMessage());
	}
	
	@Test
	void add_blankLastName() {
		fighter.setLastName("   ");

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.addFighter(fighter), "Null arguments not allowed");

		assertEquals("Last Name cannot be blank", thrown.getMessage());
	}




	@Test
	void addAll() {
		List<Fighter> added = fighterService.addFighters(Arrays.asList(fighter));
		assertTrue(fighterService.get(added.get(0).getFighterId()).isPresent(), "Fighter wasn't found.");
	}

	@Test
	void addAll_validateFailed_rollback() {
		List<Fighter> fighterList = new ArrayList<>();
		fighterList.add(fighter);
		fighterList.add(fighter);

		fighter.setFirstName(null); // last fighter is invalid
		fighterList.add(fighter);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.addFighters(fighterList), "Null arguments not allowed");

		assertEquals("First Name cannot be null", thrown.getMessage());
	}

	@Test
	void delete() {
		Fighter added = fighterService.addFighter(fighter);
		assertTrue(fighterService.get(added.getFighterId()).isPresent(), "Fighter wasn't found.");

		fighterService.delete(added.getFighterId());
		assertFalse(fighterService.get(added.getFighterId()).isPresent(), "Fighter wasn't deleted.");
	}

	@Test
	void get() {
		Fighter added = fighterService.addFighter(fighter);
		assertTrue(fighterService.get(added.getFighterId()).isPresent(), "Fighter wasn't found.");
	}

	@Test
	void update() {
		Fighter added = fighterService.addFighter(fighter);

		added.setFirstName("Barbara");

		Fighter updated = fighterService.updateFighter(added);
		assertEquals("Barbara", updated.getFirstName());
	}

	@Test
	void update_null() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighter(null), "Null arguments not allowed");

		assertEquals("Fighter cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullFirstName() {
		Fighter added = fighterService.addFighter(fighter);
		added.setFirstName(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighter(added), "Null arguments not allowed");

		assertEquals("First Name cannot be null", thrown.getMessage());
	}
	
	@Test
	void update_blankFirstName() {
		Fighter added = fighterService.addFighter(fighter);
		added.setFirstName("    ");

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighter(added), "Null arguments not allowed");

		assertEquals("First Name cannot be blank", thrown.getMessage());
	}


	@Test
	void update_nullId() {
		Fighter added = fighterService.addFighter(fighter);
		added.setFighterId(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighter(added), "Null arguments not allowed");

		assertEquals("Fighter ID cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullLastName() {
		Fighter added = fighterService.addFighter(fighter);
		added.setLastName(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighter(added), "Null arguments not allowed");

		assertEquals("Last Name cannot be null", thrown.getMessage());
	}

	@Test
	void update_blankLastName() {
		Fighter added = fighterService.addFighter(fighter);
		added.setLastName("    ");

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighter(added), "Null arguments not allowed");

		assertEquals("Last Name cannot be blank", thrown.getMessage());
	}

	@Test
	void update_nullDOB() {
		Fighter added = fighterService.addFighter(fighter);
		added.setDateOfBirth(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighter(added), "Null arguments not allowed");

		assertEquals("Date of Birth cannot be null", thrown.getMessage());
	}

	@Test
	void updateAll() {
		Fighter added = fighterService.addFighter(fighter);

		added.setFirstName("Barbara");

		List<Fighter> updated = fighterService.updateFighters(Arrays.asList(added));
		assertEquals("Barbara", updated.get(0).getFirstName());
	}

	@Test
	void updateAll_validateFailed_rollback() {
		Fighter added = fighterService.addFighter(fighter);

		List<Fighter> fighterList = new ArrayList<>();
		fighterList.add(added);
		fighterList.add(added);

		added.setFirstName(null); // last fighter is invalid
		fighterList.add(added);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> fighterService.updateFighters(fighterList), "Null arguments not allowed");

		assertEquals("First Name cannot be null", thrown.getMessage());
	}

}
