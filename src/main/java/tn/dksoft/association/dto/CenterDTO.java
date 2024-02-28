package tn.dksoft.association.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@Getter
@Setter

public class CenterDTO {

	@Include
	private Long id;

	private String nom;

	private String numTel;

	private AddressDTO address;

	private AgencyDTO agency;

}
