package tn.dksoft.association.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
@Setter

public class EquipmentDTO {

	@EqualsAndHashCode.Include
	private Long id;

	private String type;

	private String nom;

	private String description;

	private String energie;

	private String matiereFabrication;

	private String etat;

	private String domaineUtilisation;

	private String provenance;

	private RoomDTO room;

	private AttributionDTO attribution;

	private List<InterventionDTO> interventions;

	private List<DocumentDTO> documents;

}
