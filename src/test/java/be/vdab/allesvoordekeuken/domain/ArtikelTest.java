package be.vdab.allesvoordekeuken.domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * @Author Andre Komdeur
 */
public class ArtikelTest {
    private Artikel artikel1;
    private Artikel artikel2;
    private Artikel artikel3;
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;
    private ArtikelGroep artikelGroep3;
    private Artikel nogEensArtikel1;

    @BeforeEach
    void beforeEach(){
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        artikelGroep3 = new ArtikelGroep("testNon1");
        artikel1 = new FoodArtikel( "Test", BigDecimal.ONE, BigDecimal.TEN, 10, artikelGroep1);
        artikel2 = new FoodArtikel("test2", BigDecimal.ONE, BigDecimal.TEN, 10, artikelGroep1);
        artikel3 = new NonFoodArtikel("test3", BigDecimal.ONE, BigDecimal.TEN, 10, artikelGroep3);
        nogEensArtikel1 = new FoodArtikel( "Test", BigDecimal.ONE, BigDecimal.TEN, 10, artikelGroep1);
    }
    @Test
    void artikel1KomtVoorInArtikelGroep1() {
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).contains(artikel1);
    }
    @Test
    void artikel1VerhuistNaarArtikelGroep2() {
        artikel1.setArtikelGroep(artikelGroep2);
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep2);
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel2);
        assertThat(artikelGroep2.getArtikels()).containsOnly(artikel1);
    }
    @Test
    void eenNullArtikelGroepInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(()->artikel1.setArtikelGroep(null));
    }
    @Test
    void artikelenZijnGelijkAlsHunEmailAdressenGelijkZijn() {
        assertThat(artikel1).isEqualTo(nogEensArtikel1);
    }
    @Test
    void artikelenZijnVerschillendAlsHunIdsVerschillen() {
        assertThat(artikel1).isNotEqualTo(artikel2);
    }
    @Test
    void eenArtikelVerschiltVanNull() {
        assertThat(artikel1).isNotEqualTo(null);
    }
    @Test
    void eenArtikelVerschiltVanEenAnderTypeObject() {
        assertThat(artikel1).isNotEqualTo("");
    }
    @Test
    void gelijkeArtikelenGevenDezelfdeHashCode() {
        assertThat(artikel1).hasSameHashCodeAs(nogEensArtikel1);
    }
    @Test
    void meerdereArtikelenKunnenTotDezelfdeArtikelGroepBehoren() {
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel1, artikel2);
    }
}