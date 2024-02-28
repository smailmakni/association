package tn.dksoft.association.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.AgencyDTO;
import tn.dksoft.association.dto.UserDTO;
import tn.dksoft.association.entity.Center;
import tn.dksoft.association.entity.User;
import tn.dksoft.association.exception.EntityNotFoundException;
import tn.dksoft.association.exception.ErrorCodes;
import tn.dksoft.association.exception.InvalidEntityException;
import tn.dksoft.association.exception.InvalidOperationException;
import tn.dksoft.association.mappers.AgencyMapper;
import tn.dksoft.association.mappers.UserMapper;
import tn.dksoft.association.repository.AgencyRepository;
import tn.dksoft.association.repository.CenterRepository;
import tn.dksoft.association.repository.UserRepository;
import tn.dksoft.association.validator.AgencyValidator;
import tn.dksoft.association.validator.UserValidator;

@Service
@AllArgsConstructor
public class AdminService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private AgencyRepository agencyRepository;
	private AgencyMapper agencyMapper;
	private CenterRepository centerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDTO register(UserDTO userDto) {
		Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

		if (optionalUser.isPresent()) {
			throw new InvalidEntityException("email already exists", ErrorCodes.EMAIL_UTILISE);
		}
		List<String> errors = UserValidator.validate(userDto);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("utilisateur n'est pas valide", ErrorCodes.USER_NOT_VALID, errors);
		}
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return userMapper.fromEntityToDto(userRepository.save(userMapper.fromDtoToEntity(userDto)));
	}

	public List<UserDTO> findUsers(Pageable pageable) {
		try {
			List<UserDTO> userDTOs = userRepository.findAll(pageable).getContent().stream()
					.map(userMapper::fromEntityToDto).collect(Collectors.toList());
			return userDTOs;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching user data", e);
		}
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	public List<UserDTO> searchUsers(String search, Pageable pageable) {
		try {
			return userRepository.findByFirstnameContains(search, pageable).stream().map(userMapper::fromEntityToDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching user data", e);
		}
	}

	public UserDTO updateUser(@RequestBody UserDTO user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userMapper.fromEntityToDto(userRepository.save(userMapper.fromDtoToEntity(user)));
	}

	public AgencyDTO addAgency(AgencyDTO agency) {
		List<String> errors = AgencyValidator.validate(agency);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("L'agence n'est pas valide", ErrorCodes.AGENCY_NOT_VALID, errors);
		}
		return agencyMapper.fromEntityToDto(agencyRepository.save(agencyMapper.fromDtoToEntity(agency)));
	}

	public List<AgencyDTO> findAgencies(Pageable pageable) {
		try {
			List<AgencyDTO> agencyDTOs = agencyRepository.findAll(pageable).getContent().stream()
					.map(agencyMapper::fromEntityToDto).collect(Collectors.toList());
			return agencyDTOs;
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching agency data", e);
		}
	}

	public AgencyDTO updateAgency(@RequestBody AgencyDTO agency) {
		List<String> errors = AgencyValidator.validate(agency);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("agence n'est pas valide", ErrorCodes.AGENCY_NOT_VALID, errors);
		}
		return agencyMapper.fromEntityToDto(agencyRepository.save(agencyMapper.fromDtoToEntity(agency)));
	}

	public void deleteAgency(Long id, Pageable pageable) {
		try {
			Page<Center> centers = centerRepository.findAllByAgencyId(id, pageable);
			if (!centers.isEmpty()) {
				throw new InvalidOperationException("Impossible de supprimer une agence deja utilisé",
						ErrorCodes.AGENCY_ALREADY_IN_USE);
			}
			agencyRepository.deleteById(id);
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching agency data", e);
		}
	}

	public List<AgencyDTO> searchAgencies(String search, Pageable pageable) {
		try {
			return agencyRepository.findByNom(search, pageable).stream().map(agencyMapper::fromEntityToDto)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new EntityNotFoundException("Error while fetching agency data", e);
		}
	}
}
