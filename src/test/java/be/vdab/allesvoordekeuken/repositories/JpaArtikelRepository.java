package be.vdab.allesvoordekeuken.repositories;
import be.vdab.allesvoordekeuken.domain.Artikel;
import be.vdab.allesvoordekeuken.domain.ArtikelGroep;
import be.vdab.allesvoordekeuken.domain.FoodArtikel;
import be.vdab.allesvoordekeuken.domain.NonFoodArtikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @Author Andre Komdeur
 */
@DataJpaTest
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikelGroep.sql")
@Sql("/insertArtikel.sql")
class JpaArtikelRepositoryTest
        extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private Artikel artikel;
    private final EntityManager manager;
    private static final String ARTIKELS = "artikels";

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private long idVanTestFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testFood'", Long.class);
    }
    private long idVanTestNonFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testNonFood'", Long.class);
    }

    @Test
    void findFoodArtikelById() {
        assertThat(repository.findById(idVanTestFoodArtikel()).get().getNaam())
                .isEqualTo("testFood");
    }
    @Test
    void findNonFoodArtikelById() {
        assertThat(repository.findById(idVanTestNonFoodArtikel()).get().getNaam())
                .isEqualTo("testNonFood");
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void createFoodArtikel() {
        ArtikelGroep groep = new ArtikelGroep("testgroep");
        manager.persist(groep);
        FoodArtikel artikel =
                new FoodArtikel("testFood2",BigDecimal.ONE ,BigDecimal.TEN,100, groep);
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(
                ARTIKELS, "id=" + artikel.getId())).isOne();
    }
    @Test
    void createNonFoodArtikel() {
        ArtikelGroep groep = new ArtikelGroep("testgroep");
        manager.persist(groep);
        NonFoodArtikel artikel =
                new NonFoodArtikel("testNonFood2",BigDecimal.ONE ,BigDecimal.TEN,100, groep);
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(
                ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    void deleteFoodArtikel() {
        long id = idVanTestFoodArtikel();
        repository.delete(id);
        manager.flush();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id=" + id)).isZero();
    }
    @Test
    void deleteNonFoodArtikel() {
        long id = idVanTestNonFoodArtikel();
        repository.delete(id);
        manager.flush();
        assertThat(super.countRowsInTableWhere(ARTIKELS, "id=" + id)).isZero();
    }

    @Test
    void findAllByWord() {
        List<Artikel> docenten = repository.findAllByWord("test");
        assertThat(docenten)
                .hasSize(super.jdbcTemplate.queryForObject(
                        "select count(*) from artikels where naam like '%test%'", Integer.class))
                .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam).contains("test"))
                .isSorted();
    }
    @Test
    void algemeneVerhoging() {
        assertThat(repository.algemeneVerhoging(BigDecimal.TEN))
                .isEqualTo(super.countRowsInTable(ARTIKELS));
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id=?", BigDecimal.class,
                idVanTestFoodArtikel()))
                .isEqualByComparingTo("22");
        assertThat(super.jdbcTemplate.queryForObject(
                "select verkoopprijs from artikels where id=?", BigDecimal.class,
                idVanTestNonFoodArtikel()))
                .isEqualByComparingTo("132");
    }
    @Test
    void artikelGroepLazyLoaded() {
        assertThat(repository.findById(idVanTestFoodArtikel()).get()
                .getArtikelGroep().getNaam()).isEqualTo("testFoodGroep");
    }
}