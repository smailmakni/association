package tn.dksoft.association.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.RoomDTO;
import tn.dksoft.association.services.CenterService;

@RestController
@RequestMapping(value = "/api/center", method = { RequestMethod.GET, RequestMethod.POST })
@AllArgsConstructor
public class CenterController {

	private CenterService centerService;

	@GetMapping("/roomsList")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<RoomDTO>> findRooms(Pageable pageable) {
		List<RoomDTO> rooms = centerService.findAllRooms(pageable);
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@GetMapping(path = "/roomListByCenter/{idCenter}", produces = "application/json")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<RoomDTO>> findAllByCenterId(@PathVariable(value = "idCenter") Long id,
			Pageable pageable) {
		List<RoomDTO> rooms = centerService.findAllRoomsByCenterId(id, pageable);
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@PostMapping("/addRoom")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<RoomDTO> save(@RequestBody RoomDTO room) {
		return new ResponseEntity<>(centerService.addRoom(room), HttpStatus.OK);
	}

	@DeleteMapping("/deleteRoom/{idRoom}")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<RoomDTO>> delete(@PathVariable(name = "idRoom") Long id, Pageable pageable) {
		centerService.deleteRoom(id, pageable);
		List<RoomDTO> rooms = centerService.findAllRooms(pageable);
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@GetMapping("/searchRoom")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<List<RoomDTO>> findRooms(@RequestParam(name = "search", defaultValue = "") String search,
			Pageable pageable) {
		List<RoomDTO> rooms = centerService.searchRoom(search, pageable);
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@PutMapping("/updateRoom")
	@PreAuthorize("hasAuthority('SCOPE_ADMIN') || hasAuthority('SCOPE_AGENCY') || hasAuthority('SCOPE_CENTER')")
	public ResponseEntity<RoomDTO> updateRoom(@RequestBody RoomDTO room) {
		return new ResponseEntity<>(centerService.updateRoom(room), HttpStatus.OK);
	}

}
