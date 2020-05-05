package be.vdab.allesvoordekeuken.domain;
import javax.persistence.*;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
/**
 * @Author Andre Komdeur
 */
@Entity
@Table(name = "artikelgroepen")
public class ArtikelGroep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @OneToMany(mappedBy = "artikelGroep")
    @OrderBy("naam")
    private Set<Artikel> artikels;
    public ArtikelGroep(String naam) {
        this.naam = naam;
        this.artikels = new LinkedHashSet<>();
    }

    protected ArtikelGroep() { }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Artikel> getArtikels() {
        return Collections.unmodifiableSet(artikels);
    }
    public boolean add(Artikel artikel) {
        boolean toegevoegd = artikels.add(artikel);
        ArtikelGroep oudeArtikelGroep = artikel.getArtikelGroep();
        if (oudeArtikelGroep != null && oudeArtikelGroep != this) {
            oudeArtikelGroep.artikels.remove(artikel);
        }
        if (oudeArtikelGroep != this) {
            artikel.setArtikelGroep(this);
        }
        return toegevoegd;
    }
}