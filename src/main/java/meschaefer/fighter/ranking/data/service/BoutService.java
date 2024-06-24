package meschaefer.fighter.ranking.data.service;

import static meschaefer.fighter.ranking.data.util.Validation.combineErrs;
import static meschaefer.fighter.ranking.data.util.Validation.makeErrMsg;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import jakarta.transaction.Transactional;
import meschaefer.fighter.ranking.data.jpa.model.Bout;
import meschaefer.fighter.ranking.data.jpa.repository.BoutRepository;

@Service
@Validated
public class BoutService {

	private BoutRepository boutRepository;

	public BoutService(BoutRepository boutRepository) {
		super();
		this.boutRepository = boutRepository;
	}

	@Transactional
	public Bout addBout(Bout bout) {
		if (bout != null) {
			bout.setBoutId(UUID.randomUUID());
		}
		validate(bout);
		return boutRepository.save(bout);
	}

	@Transactional
	public List<Bout> addBouts(List<Bout> bouts) {
		bouts.stream()
			 .filter(Objects::nonNull)
			 .forEach(bout -> bout.setBoutId(UUID.randomUUID()));
		bouts.forEach(BoutService::validate);
		return boutRepository.saveAll(bouts);
	}

	@Transactional
	public void delete(UUID id) {
		boutRepository.deleteById(id);
	}

	public Optional<Bout> get(UUID id) {
		return boutRepository.findById(id);
	}

	@Transactional
	public Bout updateBout(Bout bout) {
		validate(bout);
		Bout updated = null;
		Optional<Bout> optExisting = boutRepository.findById(bout.getBoutId());

		if (optExisting.isPresent()) {
			updated = optExisting.get();
			updated.setBoutDate(bout.getBoutDate());
			updated.setBoutId(bout.getBoutId());
			updated.setFighter1(bout.getFighter1());
			updated.setFighter2(bout.getFighter2());
			updated.setLocation(bout.getLocation());
			updated.setWeightClass(bout.getWeightClass());
		} else {
			throw new IllegalArgumentException(
					String.format("Bout with ID %s was not found", bout.getBoutId().toString()));
		}
		return boutRepository.save(updated);
	}

	@Transactional
	public List<Bout> updateBouts(List<Bout> bouts) {
		return bouts.stream().map(this::updateBout).toList();
	}
	
	private static void validate(Bout bout) {
	    String[] errors;

	    if (bout == null) {
	        errors = new String[] {"Bout cannot be null"};
	    } else {
	        errors = new String[] {
	            makeErrMsg(bout.getBoutDate(), "Bout Date"),
	            makeErrMsg(bout.getLocation(), "Location"),
	            makeErrMsg(bout.getWeightClass(), "Weight Class"),
	            makeErrMsg(bout.getBoutId(), "Bout ID"),
	            makeErrMsg(bout.getFighter1(), "Fighter One"),
	            makeErrMsg(bout.getFighter2(), "Fighter Two")
	        };
	    }
	    
	    String msg = combineErrs(errors);
	    
	    if (StringUtils.hasText(msg)) {
	        throw new IllegalArgumentException(msg);
	    }
	}
	


}