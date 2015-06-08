package tadp.scala.bonita
import org.junit.Assert
import org.junit.Test


/**
 * @author Dario
 */
class RealizarAtaqueTest 
{
  @Test
  def realizarAtaqueDeTipoPrincipalDa50Exp
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(pikachu.experiencia, 0)
    pikachu = new RealizarAtaque(fixture.thunderbolt).realizar(pikachu)
    Assert.assertEquals(50, pikachu.experiencia)
  }
}