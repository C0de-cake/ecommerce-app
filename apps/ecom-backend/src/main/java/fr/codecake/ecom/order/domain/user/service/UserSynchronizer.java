package fr.codecake.ecom.order.domain.user.service;

import fr.codecake.ecom.order.domain.user.aggregate.User;
import fr.codecake.ecom.order.domain.user.repository.UserRepository;
import fr.codecake.ecom.order.domain.user.vo.UserAddressToUpdate;
import fr.codecake.ecom.order.infrastructure.secondary.service.kinde.KindeService;
import fr.codecake.ecom.shared.authentication.application.AuthenticatedUser;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserSynchronizer {

  private final UserRepository userRepository;
  private final KindeService kindeService;

  private static final String UPDATE_AT_KEY = "last_signed_in";

  public UserSynchronizer(UserRepository userRepository, KindeService kindeService) {
    this.userRepository = userRepository;
    this.kindeService = kindeService;
  }

  public void syncWithIdp(Jwt jwtToken, boolean forceResync) {
    Map<String, Object> claims = jwtToken.getClaims();
    List<String> rolesFromToken = AuthenticatedUser.extractRolesFromToken(jwtToken);
    Map<String, Object> userInfo = kindeService.getUserInfo(claims.get("sub").toString());
    User user = User.fromTokenAttributes(userInfo, rolesFromToken);
    Optional<User> existingUser = userRepository.getOneByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      if (claims.get(UPDATE_AT_KEY) != null) {
        Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
        Instant idpModifiedDate = Instant.ofEpochSecond((Integer) claims.get(UPDATE_AT_KEY));

        if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
          updateUser(user, existingUser.get());
        }
      }
    } else {
      user.initFieldForSignup();
      userRepository.save(user);
    }

  }

  private void updateUser(User user, User existingUser) {
    existingUser.updateFromUser(user);
    userRepository.save(existingUser);
  }

  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userRepository.updateAddress(userAddressToUpdate.userPublicId(), userAddressToUpdate);
  }
}
