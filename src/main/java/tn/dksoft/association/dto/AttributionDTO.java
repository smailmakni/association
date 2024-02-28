package tn.dksoft.association.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter

public class AttributionDTO {

	@Include
	private Long id;

	private String nomOrganisme;

	private String type;

	private Instant creationDate;

	private DocumentDTO document;

}
