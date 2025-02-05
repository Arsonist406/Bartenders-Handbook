package seniv.dev.bartendershandbook.entities.glasses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Long> {
    Optional<Glass> findByName(String name);

    List<Glass> findByNameContaining(String infix);
}
