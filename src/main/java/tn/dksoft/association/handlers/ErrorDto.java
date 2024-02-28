package tn.dksoft.association.handlers;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.dksoft.association.exception.ErrorCodes;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
	private Integer httpCode;

	private ErrorCodes code;

	private String message;

	private List<String> errors;

}
