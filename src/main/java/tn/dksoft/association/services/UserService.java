package tn.dksoft.association.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tn.dksoft.association.dto.UserDTO;
import tn.dksoft.association.entity.User;
import tn.dksoft.association.exception.EntityNotFoundException;
import tn.dksoft.association.mappers.UserMapper;
import tn.dksoft.association.repository.UserRepository;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	private UserRepository repository;
	private UserMapper userMapper;

	public UserDTO getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> user = repository.findByEmail(username);
		try {
			UserDTO userDto = new UserDTO();
			if (user.isPresent()) {
				userDto = userMapper.fromEntityToDto(user.get());
			}
			return userDto;
		} catch (Exception e) {
			throw new EntityNotFoundException("utilisateur introuvable", e);
		}
	}

	public void changePassword(String ancienPassword, String nouveauPassword, String confirmePassword) {
		User user = userMapper.fromDtoToEntity(getCurrentUser());
		if (!passwordEncoder.matches(ancienPassword, user.getPassword())) {
			throw new IllegalStateException("Wrong password");
		}
		if (!nouveauPassword.equals(confirmePassword)) {
			throw new IllegalStateException("Passwords are not the same");
		}
		user.setPassword(passwordEncoder.encode(nouveauPassword));
		repository.save(user);
	}

}
