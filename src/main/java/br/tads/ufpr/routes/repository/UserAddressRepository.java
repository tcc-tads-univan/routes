package br.tads.ufpr.routes.repository;

import br.tads.ufpr.routes.model.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    List<UserAddress> findAllByRelatedTo(Long driverId);
    Optional<UserAddress> findByUserId(Long userId);
}
