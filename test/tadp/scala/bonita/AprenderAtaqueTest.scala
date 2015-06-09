package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert
/**
 * @author Dario
 */
class AprenderAtaqueTest {
  
  @Test
  def `pokemon electrico aprende thunderbolt y lo incorpora a sus ataques`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertTrue(pikachu.sabeElAtaque(fixture.thunderbolt))
  }
  
  
}