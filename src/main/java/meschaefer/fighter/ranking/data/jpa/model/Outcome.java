package meschaefer.fighter.ranking.data.jpa.model;

import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import meschaefer.fighter.ranking.data.jpa.converter.FightEndMethodConverter;

@Entity
@Table(name = "Outcomes")
public class Outcome {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "outcome_id", columnDefinition = "UNIQUEIDENTIFIER")
	private UUID outcomeId;

	@ManyToOne
	@JoinColumn(name = "bout_id", nullable = false)
	private Bout bout;

	@ManyToOne
	@JoinColumn(name = "winner_id")
	private Fighter winner;

	@ManyToOne
	@JoinColumn(name = "loser_id")
	private Fighter loser;

	@Convert(converter = FightEndMethodConverter.class)
	@Column(name = "method", nullable = false, length = 64)
	private FightEndMethod method;

	@Column(name = "round")
	private Byte round;

	@Column(name = "time")
	private LocalTime time;

	public UUID getOutcomeId() {
		return outcomeId;
	}

	public void setOutcomeId(UUID outcomeId) {
		this.outcomeId = outcomeId;
	}

	public Bout getBout() {
		return bout;
	}

	public void setBout(Bout bout) {
		this.bout = bout;
	}

	public Fighter getWinner() {
		return winner;
	}

	public void setWinner(Fighter winner) {
		this.winner = winner;
	}

	public Fighter getLoser() {
		return loser;
	}

	public void setLoser(Fighter loser) {
		this.loser = loser;
	}

	public FightEndMethod getMethod() {
		return method;
	}

	public void setMethod(FightEndMethod method) {
		this.method = method;
	}

	public Byte getRound() {
		return round;
	}

	public void setRound(Byte round) {
		this.round = round;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

}