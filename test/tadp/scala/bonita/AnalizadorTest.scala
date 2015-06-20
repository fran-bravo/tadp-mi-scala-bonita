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
    
    var rutinaResultante = analisisPorNivel.elegirMejorRutina(dratini, fixture.rutinas)

    assertEquals(fixture.rutinaNivel, rutinaResultante)
  }
  
  @Test
  def `Analisis basico de 3 rutinas eligiendo la que de mas energia final`
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    val analisisPorEnergia = new AnalizadorDeRutina(fixture.criterioMasEnergia)
    
    var rutinaResultante = analisisPorEnergia.elegirMejorRutina(dratini, fixture.rutinas)

    assertEquals(fixture.rutinaEnergia, rutinaResultante)
  }

  @Test
  def `Analisis basico de 3 rutinas eligiendo la que aumente menos el peso`
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    val analisisPorEnergia = new AnalizadorDeRutina(fixture.criterioMenorPeso)
    
    var rutinaResultante = analisisPorEnergia.elegirMejorRutina(dratini, fixture.rutinas)

    assertEquals(fixture.rutinaMenosPeso, rutinaResultante)
  }

}