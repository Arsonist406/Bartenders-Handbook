package seniv.dev.bartendershandbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.module.glass.Glass;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Long> {
    Optional<Glass> findByName(String name);

    List<Glass> findByNameContaining(String infix);
}
