package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import tn.dksoft.association.dto.AttributionDTO;

public class AttributionValidator {
	public static List<String> validate(AttributionDTO dto) {

		List<String> errors = new ArrayList<>();

		if (dto == null) {
			errors.add("Veuillez renseigner le nom de l'organisme");
			errors.add("Veuillez renseigner le type");
			errors.add("Veuillez renseigner l'equipement");
			return errors;
		}

		if (!StringUtils.hasLength(dto.getNomOrganisme())) {
			errors.add("Veuillez renseigner le nom de l'organisme");
		}
		if (!StringUtils.hasLength(dto.getType())) {
			errors.add("Veuillez renseigner le type d'attribution");
		}
		if (dto.getCreationDate() == null) {
			errors.add("Veuillez renseigner le type d'attribution");
		}

		return errors;
	}
}