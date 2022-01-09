package htw.webtech.smarthome.repository;

import htw.webtech.smarthome.domain.Cart;
import htw.webtech.smarthome.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
