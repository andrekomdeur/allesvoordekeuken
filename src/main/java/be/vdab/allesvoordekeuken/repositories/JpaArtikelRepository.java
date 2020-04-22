package be.vdab.allesvoordekeuken.repositories;
import be.vdab.allesvoordekeuken.domain.Artikel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;
/**
 * @Author Andre Komdeur
 */
@Repository
class JpaArtikelRepository implements ArtikelRepository {
    private final EntityManager manager;
    JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }
    @Override
    public Optional<Artikel> findById(long id) {
        return Optional.ofNullable(manager.find(Artikel.class, id));
    }
}