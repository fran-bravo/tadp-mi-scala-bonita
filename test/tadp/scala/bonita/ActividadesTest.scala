package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert
import scala.util.Try

/**
 * @author Dario
 */
class ActividadesTest {
  //Esta clase testea actividades en general
  
  @Test
  def `un pokemon dormido ignora la actividad`
  { 
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarADormido()
    var pika : Try[Pokemon] = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, pika.get.experiencia)
  }
  //lamentablemente no se me ocurre cómo más probar que no haga una actividad
  //sin probar una consecuencia, y por lo tanto se depende de otra funcionalidad
  //la forma posta posta sería con un mock pero... dale
  
  @Test
  def `un pokemon dormido despierta despues de ignorar tres actividades`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarADormido()
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    Assert.assertFalse(pikachu.estoyDormido())
  }
  
  @Test(expected = classOf[KOException])
  def `un pokemon KO rompe cuando se le pasa una actividad`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarAKO()
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get

  }
  
  @Test(expected = classOf[CaracteristicasInvalidasException])
  def `un pokemon que queda con caracteristicas invalidas rompe`
  {
    var machamp = new Pokemon(Macho, new Caracteristicas(100, 100, 45, 97, 12), fixture.machamp)
    machamp.realizarActividad(ComerHierro).get
  }
  
  
  @Test
  def `un pokemon recupera todos los pp al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(Descansar).get
    
    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `un pokemon con menos de la mitad de vida sano se duerme al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.perderEnergia(60) //Le queda 40 de vida de un total de 100
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(Descansar).get

    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(40, pikachu.energia)
    Assert.assertEquals(Dormido(), pikachu.estado)
  }
  
  @Test
  def `un pokemon con menos de la mitad de vida no sano no se duerme al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.perderEnergia(60) //Le queda 40 de vida de un total de 100
    pikachu = pikachu.pasarAEnvenenado()
    val estadoInicial = pikachu.estado
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
    pikachu = pikachu.realizarActividad(Descansar).get

    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(40, pikachu.energia)
    Assert.assertEquals(estadoInicial, pikachu.estado)
  }
  
  @Test
  def  `un pokemon dormido se cura con el ether`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.pasarADormido()
    
    pikachu = pikachu.realizarActividad(UsarEther).get
    
    Assert.assertEquals(pikachu.estado, Saludable)
    
  }
  
  
  
}