package tn.dksoft.association.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "t_centers")
public class Center {
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

	@ManyToOne
	@JoinColumn( nullable = false)
	private Agency agency;

	@JsonIgnore
	@OneToMany(mappedBy = "center")
	private List<Room> rooms;

	@CreatedDate
	@Column( updatable = false)
	private Instant creationDate;

	@LastModifiedDate
	@Column
	private Instant lastModifiedDate;

}
