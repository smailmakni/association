package tn.dksoft.association.validator;

import java.util.ArrayList;
import java.util.List;

import io.micrometer.common.util.StringUtils;

public class AddressValidator {
	public static List<String> validate(tn.dksoft.association.dto.AddressDTO adresseDto) {
		List<String> errors = new ArrayList<>();

		if (adresseDto == null) {
			errors.add("Veuillez renseigner la rue");
			errors.add("Veuillez renseigner la ville'");
			errors.add("Veuillez renseigner le pays'");
			errors.add("Veuillez renseigner le code postal'");
			return errors;
		}
		if (!StringUtils.isNotEmpty(adresseDto.getRue())) {
			errors.add("Veuillez renseigner la rue");
		}
		if (!StringUtils.isNotEmpty(adresseDto.getVille())) {
			errors.add("Veuillez renseigner la ville");
		}
		if (!StringUtils.isNotEmpty(adresseDto.getPays())) {
			errors.add("Veuillez renseigner le pays");
		}
		if (!StringUtils.isNotEmpty(adresseDto.getCodePostale())) {
			errors.add("Veuillez renseigner le code postal'");
		}
		return errors;
	}

}
