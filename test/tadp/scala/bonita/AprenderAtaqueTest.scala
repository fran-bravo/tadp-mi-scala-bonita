package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert
import scala.util.Try
/**
 * @author Dario
 */
class AprenderAtaqueTest {
  
  @Test
  def `pokemon electrico aprende thunderbolt y lo incorpora a sus ataques`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.thunderbolt))
    var pika : Try[Pokemon] = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertTrue(pika.get.sabeElAtaque(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprende thunderbolt y obtiene 15 pp`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt)).get
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprender thunderbolt y obtiene 15 pp max`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt)).get
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprende thunderbolt y lo usa, decrementa su pp actual pero no el max`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt)).get
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    Assert.assertEquals(12, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
    
  }
  
  @Test
  def `pokemon electrico tambien puede aprender pound, que es un ataque tipo normal`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.pound))
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.pound)).get
    Assert.assertTrue(pikachu.sabeElAtaque(fixture.pound))
  }
  
  @Test
  def `otro pokemon puede aprender pound`
  {
    var dratini = fixture.nuevoDratiniM()
    Assert.assertFalse(dratini.sabeElAtaque(fixture.pound))
    dratini = dratini.realizarActividad(AprenderAtaque(fixture.pound)).get
    Assert.assertTrue(dratini.sabeElAtaque(fixture.pound))
  }
  
  @Test
  def `un pokemon intenta aprender un ataque no afin, no lo aprende`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.dragon_rage)).get
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
  }
  
  @Test
  def `un pokemon intenta aprender un ataque no afin, queda KO`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
    pikachu = pikachu.realizarActividad(AprenderAtaque(fixture.dragon_rage)).get
    Assert.assertEquals(KO, pikachu.estado)
  }
  
}