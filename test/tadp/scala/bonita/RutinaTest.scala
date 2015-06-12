package tadp.scala.bonita

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore

class RutinaTest {
  
  @Test
  def `Pikachu realiza una rutina y recupera PP (no hay error)` = {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    
    var rutina = new Rutina("Rutina con descanso", 
                            List(RealizarAtaque(fixture.thunderbolt),RealizarAtaque(fixture.thunderbolt),Descansar))
    
    assertEquals(ppInicial, pikachu.realizarRutina(rutina).paActual(fixture.thunderbolt))
    
  }
  
  @Test
  def `Un pokemon dormido despierta despues de realizar una rutina con 3 actividades`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarADormido()
    
    
    var rutina = new Rutina("Rutina con 3 ataques",
                            List(RealizarAtaque(fixture.thunderbolt),RealizarAtaque(fixture.thunderbolt),RealizarAtaque(fixture.thunderbolt)))
  
    assert(!pikachu.realizarRutina(rutina).estoyDormido())                                                  
  }
  
  @Test(expected = classOf[CaracteristicasInvalidasException])
  def `Un pokemon no puede realizar rutina cuando cumple con las caracteristicas`
  {
    var machamp = new Pokemon(Macho, 100, 100, 45, 97, 12, fixture.machamp)
    var rutina = new Rutina("Rutina donde se come hierro",List(ComerHierro))
    
    machamp.realizarRutina(rutina)
  }
  
}