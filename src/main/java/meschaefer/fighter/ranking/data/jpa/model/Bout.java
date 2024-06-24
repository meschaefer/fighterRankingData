  package meschaefer.fighter.ranking.data.jpa.model;

import java.time.LocalDate;
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
import meschaefer.fighter.ranking.data.jpa.converter.WeightClassConverter;

@Entity
@Table(name = "Bouts")
public class Bout {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "bout_id", columnDefinition = "UNIQUEIDENTIFIER")
	private UUID boutId;

	@ManyToOne
	@JoinColumn(name = "fighter1_id", nullable = false)
	private Fighter fighter1;

	@ManyToOne
	@JoinColumn(name = "fighter2_id", nullable = false)
	private Fighter fighter2;

	@Column(name = "bout_date", nullable = false)
	private LocalDate boutDate;

	@Column(name = "location", nullable = false, length = 128)
	private String location;

	@Convert(converter = WeightClassConverter.class)
	@Column(name = "weight_class", nullable = false, length = 64)
	private WeightClass weightClass;

	public UUID getBoutId() {
		return boutId;
	}

	public void setBoutId(UUID boutId) {
		this.boutId = boutId;
	}

	public Fighter getFighter1() {
		return fighter1;
	}

	public void setFighter1(Fighter fighter1) {
		this.fighter1 = fighter1;
	}

	public Fighter getFighter2() {
		return fighter2;
	}

	public void setFighter2(Fighter fighter2) {
		this.fighter2 = fighter2;
	}

	public LocalDate getBoutDate() {
		return boutDate;
	}

	public void setBoutDate(LocalDate boutDate) {
		this.boutDate = boutDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public WeightClass getWeightClass() {
		return weightClass;
	}

	public void setWeightClass(WeightClass weightClass) {
		this.weightClass = weightClass;
	}

}