
package tn.dksoft.association.security.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", method = { RequestMethod.GET, RequestMethod.POST })
public class SecurityController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtEncoder jwtEncoder;

	@PostMapping("/login")
	public Map<String, String> login(String username, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Instant instant = Instant.now().minus(0, ChronoUnit.MINUTES);
		String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));

		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder().issuedAt(instant)
				.expiresAt(instant.plus(JWTUtil.EXPIRE_ACCESS_TOKEN, ChronoUnit.MINUTES)).subject(username)
				.claim("scope", scope).build();
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
				.from(JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSet);
		String access = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

		JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder().issuedAt(instant)
				.expiresAt(instant.plus(JWTUtil.EXPIRE_REFRESH_TOKEN, ChronoUnit.MINUTES)).subject(username).build();
		JwtEncoderParameters jwtEncoderParametersRefresh = JwtEncoderParameters
				.from(JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSetRefresh);
		String refresh = jwtEncoder.encode(jwtEncoderParametersRefresh).getTokenValue();

		Map<String, String> idToken = new HashMap<>();
		idToken.put("access-token", access);
		idToken.put("refresh-token", refresh);
		idToken.put("username", authentication.getPrincipal().toString());
		idToken.put("role", authentication.getAuthorities().toString());
		System.out.println(authentication);
		return idToken;
	}

}
