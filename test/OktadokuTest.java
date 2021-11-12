import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OktadokuTest {

    @Test
    public void shouldReturn12345678GivenUserInput12345678() {
        Oktadoku oktadoku = new Oktadoku();

        assertEquals(12345678, oktadoku.getInput(), 0.0);
    }
}