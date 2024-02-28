package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import tn.dksoft.association.dto.UserDTO;

public class UserValidator {
	public static List<String> validate(UserDTO dto) {

		List<String> errors = new ArrayList<>();

		if (dto == null) {
			errors.add("Veuillez renseigner le nom");
			errors.add("Veuillez renseigner le prenom");
			errors.add("Veuillez renseigner l'email");
			errors.add("Veuillez renseigner le mot de passe");
			errors.add("Veuillez renseigner le role");

			return errors;
		}

		if (!StringUtils.hasLength(dto.getFirstname())) {
			errors.add("Veuillez renseigner le nom");
		}
		if (!StringUtils.hasLength(dto.getLastname())) {
			errors.add("Veuillez renseigner le prenom");
		}
		if (!StringUtils.hasLength(dto.getEmail())) {
			errors.add("Veuillez renseigner l'email");
		}
		if (!StringUtils.hasLength(dto.getPassword())) {
			errors.add("Veuillez renseigner le mot de passe");
		}
		if (dto.getRole() == null) {
			errors.add("Veuillez renseigner le role");
		}
		return errors;
	}
}