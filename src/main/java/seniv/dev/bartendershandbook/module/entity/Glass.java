package seniv.dev.bartendershandbook.module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Glass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 2000)
    private String description;

    @ManyToMany(mappedBy = "glasses")
    private Set<Cocktail> cocktails;

    public Glass(
            String name,
            String description,
            Set<Cocktail> cocktails
    ) {
        this.name = name;
        this.description = description;
        this.cocktails = cocktails;
    }
}
