package tn.dksoft.association.security.auditing;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import tn.dksoft.association.entity.User;
import tn.dksoft.association.repository.UserRepository;

public class ApplicationAuditAware implements AuditorAware<Long> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}

		String username = authentication.getName();
		Optional<User> user = userRepository.findByEmail(username);
		return Optional.ofNullable(user.get().getId());
	}
}
