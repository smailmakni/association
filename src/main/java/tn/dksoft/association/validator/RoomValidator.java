package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import tn.dksoft.association.dto.RoomDTO;

public class RoomValidator {
	public static List<String> validate(RoomDTO dto) {

		List<String> errors = new ArrayList<>();

		if (dto == null) {
			errors.add("Veuillez renseigner le numero de la salle");
			errors.add("Veuillez renseigner l'activité");
			errors.add("Veuillez renseigner le centre");
			return errors;
		}

		if (!StringUtils.hasLength(dto.getNumero())) {
			errors.add("Veuillez renseigner le numero de la salle");
		}
		if (!StringUtils.hasLength(dto.getActivite())) {
			errors.add("Veuillez renseigner l'activité");
		}
		if (dto.getCenter() == null || dto.getCenter().getId() == null) {
			errors.add("Veuillez renseigner le centre");
		}
		return errors;
	}
}