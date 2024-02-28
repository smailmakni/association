package tn.dksoft.association.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@Entity
@Table(name = "t_agencies")
@EntityListeners(AuditingEntityListener.class)

public class Agency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Include
	private Long id;

	@Column
	private String nom;

	@Column
	private String numTel;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "agency")
	private List<Center> centers;

	@CreatedDate
	@Column(name = "creationDate", updatable = false)
	private Instant creationDate;

	@LastModifiedDate
	@Column(name = "lastModifiedDate")
	private Instant lastModifiedDate;

}
