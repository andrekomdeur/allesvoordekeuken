package be.vdab.allesvoordekeuken.repositories;
import be.vdab.allesvoordekeuken.domain.Artikel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
/**
 * @Author Andre Komdeur
 */
public interface ArtikelRepository {
    Optional<Artikel> findById(long id);
    void create(Artikel artikel);
    void delete(long id);
    List<Artikel> findBijNaamContains(String woord);
    int algemeneVerhoging(BigDecimal percentage);
}