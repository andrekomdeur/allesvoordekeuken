package be.vdab.allesvoordekeuken.repositories;
import be.vdab.allesvoordekeuken.domain.Artikel;

import java.util.Optional;
/**
 * @Author Andre Komdeur
 */
public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
}