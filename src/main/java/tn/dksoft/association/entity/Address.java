package tn.dksoft.association.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Getter
@Setter
@Builder
@Embeddable
@Table(name = "t_address")
@EntityListeners(AuditingEntityListener.class)

public class Address {

	@Column
	private String rue;

	@Column
	private String ville;

	@Column
	private String codePostale;

	@Column
	private String pays;

}
