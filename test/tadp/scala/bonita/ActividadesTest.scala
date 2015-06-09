package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert

/**
 * @author Dario
 */
class ActividadesTest {
  //Esta clase testea actividades en general
  
  @Test
  def unPokemonDormidoIgnoraLaActividad()
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
  def unPokemonDormidoDespiertaLuegoDeIgnorarTresActividades()
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
  
  
}