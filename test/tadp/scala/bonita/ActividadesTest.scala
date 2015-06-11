package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert

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
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(0, pikachu.experiencia)
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
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    Assert.assertFalse(pikachu.estoyDormido())
  }
  
  @Test(expected = classOf[KOException])
  def `un pokemon KO rompe cuando se le pasa una actividad`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarAKO()
    pikachu = pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
  }
  
  @Test(expected = classOf[CaracteristicasInvalidasException])
  def `un pokemon que queda con caracteristicas invalidas rompe`
  {
    var machamp = new Pokemon(Macho, 100, 100, 45, 97, 12, fixture.machamp)
    machamp.realizarActividad(ComerHierro)
  }
  
  
  @Test
  def `un pokemon recupera todos los pp al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu.realizarActividad(Descansar)
    
    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `un pokemon con menos de la mitad de vida sano se duerme al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.perderEnergia(60) //Le queda 40 de vida de un total de 100
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(Descansar)

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
    
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(Descansar)

    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(40, pikachu.energia)
    Assert.assertEquals(estadoInicial, pikachu.estado)
  }
  
  
  
}