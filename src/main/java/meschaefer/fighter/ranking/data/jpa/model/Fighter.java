package meschaefer.fighter.ranking.data.jpa.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Fighters")
public class Fighter {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "fighter_id", columnDefinition = "UNIQUEIDENTIFIER")
	private UUID fighterId;

	@Column(name = "first_name", nullable = false, length = 64)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 64)
	private String lastName;

	@Column(name = "middle_name", length = 64)
	private String middleName;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "nationality", length = 64)
	private String nationality;   

	public UUID getFighterId() {
		return fighterId;
	}

	public void setFighterId(UUID fighterId) {
		this.fighterId = fighterId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}
