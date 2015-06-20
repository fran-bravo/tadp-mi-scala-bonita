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
    var pika : Try[Pokemon] = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertEquals(15, pika.get.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprender thunderbolt y obtiene 15 pp max`
  {
    var pikachu = fixture.nuevoPikachuM()
    var pika : Try[Pokemon] = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertEquals(15, pika.get.paMax(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprende thunderbolt y lo usa, decrementa su pp actual pero no el max`
  {
    var pikachu = fixture.nuevoPikachuM()
    var pika : Try[Pokemon] = pikachu.realizarActividad(AprenderAtaque(fixture.thunderbolt))
    Assert.assertEquals(15, pika.get.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pika.get.paMax(fixture.thunderbolt))
    pika = pika.get.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pika = pika.get.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pika = pika.get.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(12, pika.get.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pika.get.paMax(fixture.thunderbolt))
    
  }
  
  @Test
  def `pokemon electrico tambien puede aprender pound, que es un ataque tipo normal`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.pound))
    var pika : Try[Pokemon] = pikachu.realizarActividad(AprenderAtaque(fixture.pound))
    Assert.assertTrue(pika.get.sabeElAtaque(fixture.pound))
  }
  
  @Test
  def `otro pokemon puede aprender pound`
  {
    var dratini = fixture.nuevoDratiniM()
    Assert.assertFalse(dratini.sabeElAtaque(fixture.pound))
    var drati : Try[Pokemon] = dratini.realizarActividad(AprenderAtaque(fixture.pound))
    Assert.assertTrue(drati.get.sabeElAtaque(fixture.pound))
  }
  
  @Test
  def `un pokemon intenta aprender un ataque no afin, no lo aprende`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
    var pika : Try[Pokemon] = pikachu.realizarActividad(AprenderAtaque(fixture.dragon_rage))
    Assert.assertFalse(pika.get.sabeElAtaque(fixture.dragon_rage))
  }
  
  @Test
  def `un pokemon intenta aprender un ataque no afin, queda KO`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
    var pika : Try[Pokemon] = pikachu.realizarActividad(AprenderAtaque(fixture.dragon_rage))
    Assert.assertEquals(KO, pika.get.estado)
  }
  
}