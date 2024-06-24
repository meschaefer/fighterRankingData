package meschaefer.fighter.ranking.data.service;

import static meschaefer.fighter.ranking.data.util.Validation.combineErrs;
import static meschaefer.fighter.ranking.data.util.Validation.makeErrMsg;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;
import meschaefer.fighter.ranking.data.jpa.model.Outcome;
import meschaefer.fighter.ranking.data.jpa.repository.OutcomeRepository;

@Service
public class OutcomeService {

	private OutcomeRepository outcomeRepository;

	public OutcomeService(OutcomeRepository outcomeRepository) {
		super();
		this.outcomeRepository = outcomeRepository;
	}

	@Transactional
	public Outcome addOutcome(Outcome outcome) {
		if (outcome != null) {
			outcome.setOutcomeId(UUID.randomUUID());
		}
		validate(outcome);
		return outcomeRepository.save(outcome);
	}

	@Transactional
	public List<Outcome> addOutcomes(List<Outcome> outcomes) {
		outcomes.stream()
				.filter(Objects::nonNull)
				.forEach(outcome -> outcome.setOutcomeId(UUID.randomUUID()));
		
		outcomes.forEach(OutcomeService::validate);
		return outcomeRepository.saveAll(outcomes);
	}

	@Transactional
	public void delete(UUID id) {
		outcomeRepository.deleteById(id);
	}

	public Optional<Outcome> get(UUID id) {
		return outcomeRepository.findById(id);
	}

	@Transactional
	public Outcome updateOutcome(Outcome outcome) {
		validate(outcome);
		Outcome updated = null;
		Optional<Outcome> optExisting = outcomeRepository.findById(outcome.getOutcomeId());

		if (optExisting.isPresent()) {
			updated = optExisting.get();
			updated.setBout(outcome.getBout());
			updated.setWinner(outcome.getWinner());
			updated.setLoser(outcome.getLoser());
			updated.setMethod(outcome.getMethod());
			updated.setRound(outcome.getRound());
			updated.setTime(outcome.getTime());
		} else {
			throw new IllegalArgumentException(
					String.format("Outcome with ID %s was not found", outcome.getOutcomeId().toString()));
		}
		return outcomeRepository.save(updated);
	}

	@Transactional
	public List<Outcome> updateOutcomes(List<Outcome> outcomes) {
		return outcomes.stream().map(this::updateOutcome).toList();
	}
	
	private static void validate(Outcome outcome) {
	    String[] errors;

	    if (outcome == null) {
	        errors = new String[] {"Outcome cannot be null"};
	    } else {
	        errors = new String[] {
	            makeErrMsg(outcome.getBout(), "Bout"),
	            makeErrMsg(outcome.getMethod(), "Method"),
	            makeErrMsg(outcome.getOutcomeId(), "Outcome ID"),
	            makeErrMsg(outcome.getRound(), "Round"),
	            makeErrMsg(outcome.getTime(), "Time"),
	        };
	    }
	    
	    String msg = combineErrs(errors);
	    
	    if (StringUtils.hasText(msg)) {
	        throw new IllegalArgumentException(msg);
	    }
	}

}
