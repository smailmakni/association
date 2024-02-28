package tn.dksoft.association.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.dksoft.association.entity.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

	@Id
	@GeneratedValue
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private AgencyDTO agency;
	private CenterDTO center;
	private String password;
	private Role role;

}
