package tadp.scala.bonita
import org.junit.Assert
import org.junit.Test

/**
 * @author Dario
 */
class NadarTest {
  
  @Test
  def `pokemon electrico queda KO y no gana experiencia si intenta nadar`
  {
    var charizard = fixture.nuevoCharizardM()
    charizard = charizard.realizarActividad(Nadar(1)) 
    Assert.assertEquals(0, charizard.experiencia)
    Assert.assertEquals(KO, charizard.estado)
  }
  
}