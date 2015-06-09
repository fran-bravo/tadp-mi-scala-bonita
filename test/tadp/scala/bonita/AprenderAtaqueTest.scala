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
  
  @Test
  def `pokemon electrico aprende thunderbolt y obtiene 15 pp`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprender thunderbolt y obtiene 15 pp max`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprende thunderbolt y lo usa, decrementa su pp actual pero no el max`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(12, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
    
  }
  
  
}