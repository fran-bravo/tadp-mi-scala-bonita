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
    Assert.assertEquals(0, pikachu.experiencia)
    pikachu = new RealizarAtaque(fixture.thunderbolt).realizar(pikachu)
    Assert.assertEquals(50, pikachu.experiencia)
  }
  
  @Test
  def realizarAtaqueDeTipoSecundarioDa20ExpAUnMacho
  {
    var charizard = fixture.nuevoCharizardMConFly()
    Assert.assertEquals(0, charizard.experiencia)
    charizard = new RealizarAtaque(fixture.fly).realizar(charizard)
    Assert.assertEquals(20, charizard.experiencia)
  }

  @Test
  def realizarAtaqueDeTipoSecundarioDa40ExpAUnaHembra
  {
    var charizard = fixture.nuevoCharizardFConFly()
    Assert.assertEquals(0, charizard.experiencia)
    charizard = new RealizarAtaque(fixture.fly).realizar(charizard)
    Assert.assertEquals(40, charizard.experiencia)
  }
  
  @Test
  def realizarAtaqueDragonDa80Exp
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    Assert.assertEquals(0, dratini.experiencia)
    dratini = new RealizarAtaque(fixture.dragon_rage).realizar(dratini)
    Assert.assertEquals(80, dratini.experiencia)
  }
  
}