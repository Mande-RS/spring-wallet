package mandepaulo.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mandepaulo.wallet.models.CurrentUserSession;

@Repository
public interface CurrentSessionRepository extends JpaRepository<CurrentUserSession, Integer> {

    public CurrentUserSession findByUuid(String uuid);

    @Query("FROM CurrentUserSession a WHERE a.userId=?1")
    public Optional<CurrentUserSession> findByUserId(Integer userId);

}