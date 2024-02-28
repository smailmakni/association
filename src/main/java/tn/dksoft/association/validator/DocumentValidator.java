package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import tn.dksoft.association.dto.DocumentDTO;

public class DocumentValidator {
	public static List<String> validate(DocumentDTO dto) {

		List<String> errors = new ArrayList<>();

		if (dto == null) {
			errors.add("dto null, Veuillez renseigner le nom du fichier");

			return errors;
		}

		if (!StringUtils.hasLength(dto.getNom())) {
			errors.add("Veuillez renseigner le nom du document");
		}
		return errors;
	}
}