package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import tn.dksoft.association.dto.EquipmentDTO;

public class EquipmentValidator {
	public static List<String> validate(EquipmentDTO dto) {

		List<String> errors = new ArrayList<>();

		if (dto == null) {
			errors.add("Veuillez renseigner le Type de l'equipement");
			errors.add("Veuillez renseigner le nom de l'equipement");
			errors.add("Veuillez renseigner la description de l'equipement");
			errors.add("Veuillez renseigner l'energie consommé par l'equipement");
			errors.add("Veuillez renseigner la matière de fabrication de l'equipement");
			errors.add("Veuillez renseigner l'etat de l'equipement");
			errors.add("Veuillez renseigner le domaine d'utilisation de l'equipement");
			errors.add("Veuillez renseigner la provenance de l'equipement");
			errors.add("Veuillez renseigner la facture l'equipement");
			errors.add("Veuillez renseigner la salle de l'equipement");

			return errors;
		}

		if (!StringUtils.hasLength(dto.getType())) {
			errors.add("Veuillez renseigner le Type de l'equipement");
		}
		if (!StringUtils.hasLength(dto.getNom())) {
			errors.add("Veuillez renseigner le nom de l'equipement");
		}
		if (!StringUtils.hasLength(dto.getDescription())) {
			errors.add("Veuillez renseigner la description de l'equipement");
		}
		if (!StringUtils.hasLength(dto.getEnergie())) {
			errors.add("Veuillez renseigner l'energie consommé par l'equipement");
		}
		if (!StringUtils.hasLength(dto.getMatiereFabrication())) {
			errors.add("Veuillez renseigner la matière de fabrication de l'equipement");
		}
		if (!StringUtils.hasLength(dto.getEtat())) {
			errors.add("Veuillez renseigner l'etat de l'equipement");
		}
		if (!StringUtils.hasLength(dto.getDomaineUtilisation())) {
			errors.add("Veuillez renseigner le domaine d'utilisation de l'equipement");
		}
		if (!StringUtils.hasLength(dto.getProvenance())) {
			errors.add("Veuillez renseigner la provenance de l'equipement");
		}
		if (dto.getRoom() == null || dto.getRoom().getId() == null) {
			errors.add("Veuillez renseigner la salle de l'equipement");
		}
		/*
		 * if (dto.getDocuments() == null || dto.getDocuments().isEmpty() ) {
		 * errors.add("Veuillez renseigner les documents de l'equipement"); }
		 */
		return errors;
	}
}