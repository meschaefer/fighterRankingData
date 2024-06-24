package meschaefer.fighter.ranking.data.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import meschaefer.fighter.ranking.data.jpa.model.Outcome;

public interface OutcomeRepository extends JpaRepository<Outcome, UUID> {

}
