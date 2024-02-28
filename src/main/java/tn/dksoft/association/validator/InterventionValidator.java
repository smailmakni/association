package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import tn.dksoft.association.dto.InterventionDTO;

public class InterventionValidator {
	public static List<String> validate(InterventionDTO dto) {

		List<String> errors = new ArrayList<>();

		if (dto == null) {
			errors.add("Veuillez renseigner le type de l'intervention");
			errors.add("Veuillez renseigner l'equipment");
			return errors;
		}

		if (!StringUtils.hasLength(dto.getType())) {
			errors.add("Veuillez renseigner le type de l'intervention");
		}
		if (dto.getDocuments() == null || dto.getDocuments().get(0).getId() == null) {
			errors.add("Veuillez renseigner les documents de l'intervention");
		}

		// validation document
		return errors;
	}
}