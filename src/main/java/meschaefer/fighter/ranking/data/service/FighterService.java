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
import meschaefer.fighter.ranking.data.jpa.model.Fighter;
import meschaefer.fighter.ranking.data.jpa.repository.FighterRepository;

@Service
public class FighterService {

	private FighterRepository fighterRepository;

	public FighterService(FighterRepository fighterRepository) {
		super();
		this.fighterRepository = fighterRepository;
	}

	@Transactional
	public Fighter addFighter(Fighter fighter) {
		if (fighter != null) {
			fighter.setFighterId(UUID.randomUUID());
		}
		validate(fighter);
		return fighterRepository.save(fighter);
	}

	@Transactional
	public List<Fighter> addFighters(List<Fighter> fighters) {
		fighters.stream()
				.filter(Objects::nonNull)
				.forEach(fighter -> fighter.setFighterId(UUID.randomUUID()));
		
		fighters.forEach(FighterService::validate);
		return fighterRepository.saveAll(fighters);
	}

	@Transactional
	public void delete(UUID id) {
		fighterRepository.deleteById(id);
	}

	public Optional<Fighter> get(UUID id) {
		return fighterRepository.findById(id);
	}

	@Transactional
	public Fighter updateFighter(Fighter fighter) {
		validate(fighter);
		Fighter updated = null;
		Optional<Fighter> optExisting = fighterRepository.findById(fighter.getFighterId());

		if (optExisting.isPresent()) {
			updated = optExisting.get();
			updated.setDateOfBirth(fighter.getDateOfBirth());
			updated.setFighterId(fighter.getFighterId());
			updated.setFirstName(fighter.getFirstName());
			updated.setLastName(fighter.getLastName());
			updated.setMiddleName(fighter.getLastName());
			updated.setNationality(fighter.getNationality());
		} else {
			throw new IllegalArgumentException(
					String.format("Fighter with ID %s was not found", fighter.getFighterId().toString()));
		}
		return fighterRepository.save(updated);
	}

	@Transactional
	public List<Fighter> updateFighters(List<Fighter> fighters) {
		return fighters.stream().map(this::updateFighter).toList();
	}

	private static void validate(Fighter fighter) {
		String[] errors;

		if (fighter == null) {
			errors = new String[] { "Fighter cannot be null" };
		} else {
			errors = new String[] { 
					makeErrMsg(fighter.getDateOfBirth(), "Date of Birth"),
					makeErrMsg(fighter.getFighterId(), "Fighter ID"), 
					makeErrMsg(fighter.getFirstName(), "First Name"),
					makeErrMsg(fighter.getLastName(), "Last Name"), 
				};
		}

		String msg = combineErrs(errors);

		if (StringUtils.hasText(msg)) {
			throw new IllegalArgumentException(msg);
		}
	}

}