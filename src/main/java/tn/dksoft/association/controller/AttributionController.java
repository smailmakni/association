package tn.dksoft.association.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.AttributionDTO;
import tn.dksoft.association.dto.DocumentDTO;
import tn.dksoft.association.services.AttributionService;

@RestController
@RequestMapping(value = "/api/attribution", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class AttributionController {

	private AttributionService attributionService;

	@GetMapping("/attributionList")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<AttributionDTO>> findAttributions(Pageable pageable) {
		List<AttributionDTO> attributions = attributionService.findAll(pageable);
		return new ResponseEntity<>(attributions, HttpStatus.OK);
	}

	@PutMapping("/updateAttribution")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<AttributionDTO> updateAttribution(@RequestBody AttributionDTO attributionDTO,
			DocumentDTO documentDTO) {
		return new ResponseEntity<>(attributionService.updateAttribution(attributionDTO, documentDTO), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	@DeleteMapping("/delete/{idAttribution}")
	public ResponseEntity<List<AttributionDTO>> delete(@PathVariable(name = "idAttribution") Long id,
			Pageable pageable) {
		attributionService.delete(id);
		List<AttributionDTO> attributions = attributionService.findAll(pageable);
		return new ResponseEntity<>(attributions, HttpStatus.OK);

	}

}
