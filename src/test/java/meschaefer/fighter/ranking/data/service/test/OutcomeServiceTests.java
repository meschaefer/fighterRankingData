package meschaefer.fighter.ranking.data.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import meschaefer.fighter.ranking.data.jpa.model.FightEndMethod;
import meschaefer.fighter.ranking.data.jpa.model.Outcome;
import meschaefer.fighter.ranking.data.jpa.repository.BoutRepository;
import meschaefer.fighter.ranking.data.jpa.repository.FighterRepository;
import meschaefer.fighter.ranking.data.service.OutcomeService;

@SpringBootTest
@Sql(scripts = "setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class OutcomeServiceTests {

	@Autowired
	private BoutRepository boutRepository;

	@Autowired
	private FighterRepository fighterRepository;
	
	@Autowired
	private OutcomeService outcomeService;

	private Outcome outcome;

	@BeforeEach
	public void setup() {
		outcome = new Outcome();
		outcome.setBout(boutRepository.findAll().get(0));
		outcome.setLoser(fighterRepository.findAll().get(0));
		outcome.setWinner(fighterRepository.findAll().get(0));
		outcome.setMethod(FightEndMethod.KO);
		outcome.setRound((byte) 5);
		outcome.setTime(LocalTime.NOON);
	}

	@Test
	void add() {
		Outcome added = outcomeService.addOutcome(outcome);
		assertTrue(outcomeService.get(added.getOutcomeId()).isPresent(), "Outcome wasn't found.");
	}

	@Test
	void add_null() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.addOutcome(null), "Null arguments not allowed");

		assertEquals("Outcome cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullBout() {
		outcome.setBout(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.addOutcome(outcome), "Null arguments not allowed");

		assertEquals("Bout cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullId() {
		// we'd expect this to be set by service.
		outcome.setOutcomeId(null);

		Outcome added = outcomeService.addOutcome(outcome);

		assertNotNull(added.getOutcomeId());
	}

	@Test
	void add_nullMethod() {
		outcome.setMethod(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.addOutcome(outcome), "Null arguments not allowed");

		assertEquals("Method cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullRound() {
		outcome.setRound(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.addOutcome(outcome), "Null arguments not allowed");

		assertEquals("Round cannot be null", thrown.getMessage());
	}

	@Test
	void add_nullTime() {
		outcome.setTime(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.addOutcome(outcome), "Null arguments not allowed");

		assertEquals("Time cannot be null", thrown.getMessage());
	}

	@Test
	void addAll() {
		List<Outcome> added = outcomeService.addOutcomes(Arrays.asList(outcome));
		assertTrue(outcomeService.get(added.get(0).getOutcomeId()).isPresent(), "Outcome wasn't found.");
	}

	@Test
	void addAll_validateFailed_rollback() {
		List<Outcome> outcomeList = new ArrayList<>();
		outcomeList.add(outcome);
		outcomeList.add(outcome);

		outcome.setMethod(null); // last outcome is invalid
		outcomeList.add(outcome);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.addOutcomes(outcomeList), "Null arguments not allowed");

		assertEquals("Method cannot be null", thrown.getMessage());
	}

	@Test
	void delete() {
		Outcome added = outcomeService.addOutcome(outcome);
		assertTrue(outcomeService.get(added.getOutcomeId()).isPresent(), "Outcome wasn't found.");

		outcomeService.delete(added.getOutcomeId());
		assertFalse(outcomeService.get(added.getOutcomeId()).isPresent(), "Outcome wasn't deleted.");
	}

	@Test
	void get() {
		Outcome added = outcomeService.addOutcome(outcome);
		assertTrue(outcomeService.get(added.getOutcomeId()).isPresent(), "Outcome wasn't found.");
	}

	@Test
	void update() {
		Outcome added = outcomeService.addOutcome(outcome);

		added.setRound((byte) 69);

		Outcome updated = outcomeService.updateOutcome(added);
		assertEquals((byte) 69, updated.getRound());
	}

	@Test
	void update_null() {
		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.updateOutcome(null), "Null arguments not allowed");

		assertEquals("Outcome cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullBout() {
		Outcome added = outcomeService.addOutcome(outcome);
		added.setBout(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.updateOutcome(added), "Null arguments not allowed");

		assertEquals("Bout cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullId() {
		Outcome added = outcomeService.addOutcome(outcome);
		added.setOutcomeId(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.updateOutcome(added), "Null arguments not allowed");

		assertEquals("Outcome ID cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullMethod() {
		Outcome added = outcomeService.addOutcome(outcome);
		added.setMethod(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.updateOutcome(added), "Null arguments not allowed");

		assertEquals("Method cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullRound() {
		Outcome added = outcomeService.addOutcome(outcome);
		added.setRound(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.updateOutcome(added), "Null arguments not allowed");

		assertEquals("Round cannot be null", thrown.getMessage());
	}

	@Test
	void update_nullTime() {
		Outcome added = outcomeService.addOutcome(outcome);
		added.setTime(null);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.updateOutcome(added), "Null arguments not allowed");

		assertEquals("Time cannot be null", thrown.getMessage());
	}

	@Test
	void updateAll() {
		Outcome added = outcomeService.addOutcome(outcome);

		added.setRound((byte) 69);

		List<Outcome> updated = outcomeService.updateOutcomes(Arrays.asList(added));
		assertEquals((byte) 69, updated.get(0).getRound());
	}

	@Test
	void updateAll_validateFailed_rollback() {
		Outcome added = outcomeService.addOutcome(outcome);

		List<Outcome> outcomeList = new ArrayList<>();
		outcomeList.add(added);
		outcomeList.add(added);

		added.setMethod(null); // last outcome is invalid
		outcomeList.add(added);

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
				() -> outcomeService.updateOutcomes(outcomeList), "Null arguments not allowed");

		assertEquals("Method cannot be null", thrown.getMessage());
	}

}
