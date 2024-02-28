package tn.dksoft.association.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "t_rooms")
@EntityListeners(AuditingEntityListener.class)
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Include
	private Long id;

	@Column
	private String numero;

	@Column
	private String activite;

	@ManyToOne
	@JoinColumn(name = "center_id", nullable = false)
	private Center center;

	@JsonIgnore
	@OneToMany(mappedBy = "room")
	private List<Equipment> equipments;

	@CreatedDate
	@Column(updatable = false)
	private Instant creationDate;

	@LastModifiedDate
	@Column
	private Instant lastModifiedDate;

}
