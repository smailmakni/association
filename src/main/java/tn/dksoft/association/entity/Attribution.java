package tn.dksoft.association.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "t_attributions")
@EntityListeners(AuditingEntityListener.class)

public class Attribution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Include
	private Long id;

	@Column
	private String nomOrganisme;

	@Column
	private String type;

	@CreatedDate

	private Instant creationDate;

	@LastModifiedDate
	@Column
	private Instant lastModifiedDate;

	@OneToOne
	@JoinColumn(name = "document_id", referencedColumnName = "id")
	private Document document;

	@OneToOne(mappedBy = "attribution")
	private Equipment equipment;
}
