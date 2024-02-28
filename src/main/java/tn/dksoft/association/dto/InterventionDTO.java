package tn.dksoft.association.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
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

public class InterventionDTO {

	@Include
	private Long id;

	private String type;

	private List<DocumentDTO> documents;

	private Instant creationDate;

}
