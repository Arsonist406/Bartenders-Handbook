package seniv.dev.bartendershandbook.repository;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seniv.dev.bartendershandbook.module.entity.glass.Glass;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Long> {

    Optional<Glass> findByName(String name);

    List<Glass> findByNameContaining(
            @Size(min = 1, max = 50, message = "min=1, max=50 symbols")
            String infix
    );
}
