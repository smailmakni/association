package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import tn.dksoft.association.dto.AgencyDTO;

public class AgencyValidator {
	public static List<String> validate(AgencyDTO dto) {

		List<String> errors = new ArrayList<>();

		if (dto == null) {
			errors.add("Veuillez renseigner le nom de l'agence");
			errors.add("Veuillez renseigner le numero du telephone");
			errors.addAll(AddressValidator.validate(null));
			return errors;
		}

		if (!StringUtils.hasLength(dto.getNom())) {
			errors.add("Veuillez renseigner le nom de l'agence");
		}
		if (!StringUtils.hasLength(dto.getNumTel())) {
			errors.add("Veuillez renseigner le numero du telephone");
		}
		errors.addAll(AddressValidator.validate(dto.getAddress()));
		return errors;
	}

}
