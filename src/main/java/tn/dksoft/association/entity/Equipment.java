package tn.dksoft.association.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Builder
@Entity
@Table(name = "t_equipments")
@EntityListeners(AuditingEntityListener.class)

public class Equipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Include
	private Long id;

	@Column
	private String type;

	@Column
	private String nom;

	@Column
	private String description;

	@Column
	private String energie;

	@Column
	private String matiereFabrication;

	@Column
	private String etat;

	@Column
	private String domaineUtilisation;

	@Column
	private String provenance;

	@OneToMany(mappedBy = "equipment")
	private List<Document> documents;

	@ManyToOne
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@OneToMany(mappedBy = "equipment")
	private List<Intervention> interventions;

	// a verifier one to one
	@OneToOne // (cascade = CascadeType.ALL)
	@JoinColumn(name = "attribution_id", referencedColumnName = "id")
	private Attribution attribution;

	@CreatedDate
	@Column(name = "creationDate", updatable = false)
	private Instant creationDate;

	@LastModifiedDate
	@Column(name = "lastModifiedDate")
	private Instant lastModifiedDate;

}
