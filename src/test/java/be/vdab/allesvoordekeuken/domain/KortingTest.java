package be.vdab.allesvoordekeuken.domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @Author Andre Komdeur
 */
class KortingTest {
    private Korting korting1, nogEensKorting1, korting2;

    @BeforeEach
    void beforeEach() {
        korting1 = new Korting(1, BigDecimal.ONE);
        nogEensKorting1 = new Korting(1, BigDecimal.ONE);
        korting2 = new Korting(2, BigDecimal.TEN);
    }

    @Test
    void kortingenMetDezelfdeVanafAantallenZijnGelijk() {
        assertThat(korting1).isEqualTo(nogEensKorting1);
    }

    @Test
    void kortingenMetVerschillendeVanafAantallenZijnVerschillend() {
        assertThat(korting1).isNotEqualTo(korting2);
    }

    @Test
    void eenKortingVerschiltVanNull() {
        assertThat(korting1).isNotEqualTo(null);
    }

    @Test
    void eenKortingVerschiltVanEenAnderTypeObject() {
        assertThat(korting1).isNotEqualTo("");
    }

    @Test
    void gelijkeKortingenGevenDezelfdeHashCode() {
        assertThat(korting1).hasSameHashCodeAs(nogEensKorting1);
    }
}
