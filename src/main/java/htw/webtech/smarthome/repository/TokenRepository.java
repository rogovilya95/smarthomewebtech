package htw.webtech.smarthome.repository;


import htw.webtech.smarthome.domain.AuthenticationToken;
import htw.webtech.smarthome.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);

    AuthenticationToken findByToken(String token);
}
