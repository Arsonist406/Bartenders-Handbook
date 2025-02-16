package seniv.dev.bartendershandbook.module.entity.glass;

import jakarta.persistence.*;
import seniv.dev.bartendershandbook.module.entity.cocktail.Cocktail;

import java.util.Set;

@Entity
@Table(name = "glasses")
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

    public Glass() {}

    public Glass(
            String name,
            String description,
            Set<Cocktail> cocktails
    ) {
        this.name = name;
        this.description = description;
        this.cocktails = cocktails;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Cocktail> getCocktails() {
        return cocktails;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
