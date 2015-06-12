package tadp.scala.bonita
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore

class AnalizadorTest {
  
  @Test
  def `Analisis basico de 3 rutinas eligiendo la que de mas nivel`
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    val analisisPorNivel = new AnalizadorDeRutina(fixture.criterioMasNivel)
    
    var nombreRutina = analisisPorNivel.elegirMejorRutina(dratini, fixture.rutinas)

    assertEquals("Rutina Ataques", nombreRutina)
  }
  
  @Test
  def `Analisis basico de 3 rutinas eligiendo la que de mas energia final`
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    val analisisPorEnergia = new AnalizadorDeRutina(fixture.criterioMasEnergia)
    
    var nombreRutina = analisisPorEnergia.elegirMejorRutina(dratini, fixture.rutinas)

    assertEquals("Rutina Comida", nombreRutina)
  }

  @Test
  def `Analisis basico de 3 rutinas eligiendo la que aumente menos el peso`
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    val analisisPorEnergia = new AnalizadorDeRutina(fixture.criterioMenorPeso)
    
    var nombreRutina = analisisPorEnergia.elegirMejorRutina(dratini, fixture.rutinas)

    assertEquals("Rutina Descanso", nombreRutina)
  }

}