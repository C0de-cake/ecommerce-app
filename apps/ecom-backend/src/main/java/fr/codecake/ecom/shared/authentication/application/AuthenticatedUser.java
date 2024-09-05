package fr.codecake.ecom.shared.authentication.application;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import fr.codecake.ecom.shared.authentication.domain.Role;
import fr.codecake.ecom.shared.authentication.domain.Roles;
import fr.codecake.ecom.shared.authentication.domain.Username;
import fr.codecake.ecom.shared.error.domain.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is an utility class to get authenticated user information
 */
public final class AuthenticatedUser {

    public static final String PREFERRED_USERNAME = "email";

    private AuthenticatedUser() {
    }

    /**
     * Get the authenticated user username
     *
     * @return The authenticated user username
     * @throws NotAuthenticatedUserException  if the user is not authenticated
     * @throws UnknownAuthenticationException if the user uses an unknown authentication scheme
     */
    public static Username username() {
        return optionalUsername().orElseThrow(NotAuthenticatedUserException::new);
    }

    /**
     * Get the authenticated user username
     *
     * @return The authenticated user username or empty if the user is not authenticated
     * @throws UnknownAuthenticationException if the user uses an unknown authentication scheme
     */
    public static Optional<Username> optionalUsername() {
        return authentication().map(AuthenticatedUser::readPrincipal).flatMap(Username::of);
    }

    /**
     * Read user principal from authentication
     *
     * @param authentication authentication to read the principal from
     * @return The user principal
     * @throws UnknownAuthenticationException if the authentication can't be read (unknown token type)
     */
    public static String readPrincipal(Authentication authentication) {
        Assert.notNull("authentication", authentication);

        if (authentication.getPrincipal() instanceof UserDetails details) {
            return details.getUsername();
        }

        if (authentication instanceof JwtAuthenticationToken token) {
            return (String) token.getToken().getClaims().get(PREFERRED_USERNAME);
        }

        if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
            return (String) oidcUser.getAttributes().get(PREFERRED_USERNAME);
        }

        if (authentication.getPrincipal() instanceof String principal) {
            return principal;
        }

        throw new UnknownAuthenticationException();
    }

    /**
     * Get the authenticated user roles
     *
     * @return The authenticated user roles or empty roles if the user is not authenticated
     */
    public static Roles roles() {
        return authentication().map(toRoles()).orElse(Roles.EMPTY);
    }

    private static Function<Authentication, Roles> toRoles() {
        return authentication ->
                new Roles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).map(Role::from).collect(Collectors.toSet()));
    }

    /**
     * Get the authenticated user token attributes
     *
     * @return The authenticated user token attributes
     * @throws NotAuthenticatedUserException  if the user is not authenticated
     * @throws UnknownAuthenticationException if the authentication scheme is unknown
     */
    public static Map<String, Object> attributes() {
        Authentication token = authentication().orElseThrow(NotAuthenticatedUserException::new);

        if (token instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes();
        }

        throw new UnknownAuthenticationException();
    }

    private static Optional<Authentication> authentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public static List<String> extractRolesFromToken(Jwt jwtToken) {
        List<LinkedTreeMap<String, String>> realmAccess =
                (List<LinkedTreeMap<String, String>>) jwtToken.getClaims().get("roles");
        return realmAccess.stream()
                .map(roleTreeMap -> roleTreeMap.get("key"))
                .toList();
    }
}
