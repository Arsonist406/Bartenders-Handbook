package seniv.dev.bartendershandbook.repository;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.module.entity.Glass;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Long> {

    Optional<Glass> findByName(String name);

    Set<Glass> findByNameContaining(
            @Size(max = 50, message = "max=50 symbols")
            String infix
    );
}
