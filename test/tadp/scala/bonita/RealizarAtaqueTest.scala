package tadp.scala.bonita
import org.junit.Assert
import org.junit.Test


/**
 * @author Dario
 */
class RealizarAtaqueTest 
{
  @Test
  def realizarAtaqueGastaPA
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(new RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(14, pikachu.paActual(fixture.thunderbolt))
  }
  
  @Test
  def funcionaElRecuperarPA 
  { //estrictamente hablando este test no va acá, pero lo tengo que probar YA
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    pikachu = pikachu.realizarActividad(new RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(14, pikachu.paActual(fixture.thunderbolt))
    pikachu = pikachu.recuperarPA()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt)) 
  }
  
  
  @Test
  def realizarAtaqueDeTipoPrincipalDa50Exp
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(0, pikachu.experiencia)
    pikachu = pikachu.realizarActividad(new RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(50, pikachu.experiencia)
  }
  
  @Test
  def realizarAtaqueDeTipoSecundarioDa20ExpAUnMacho
  {
    var charizard = fixture.nuevoCharizardMConFly()
    Assert.assertEquals(0, charizard.experiencia)
    charizard = charizard.realizarActividad(new RealizarAtaque(fixture.fly))
    Assert.assertEquals(20, charizard.experiencia)
  }

  @Test
  def realizarAtaqueDeTipoSecundarioDa40ExpAUnaHembra
  {
    var charizard = fixture.nuevoCharizardFConFly()
    Assert.assertEquals(0, charizard.experiencia)
    charizard = charizard.realizarActividad(new RealizarAtaque(fixture.fly))
    Assert.assertEquals(40, charizard.experiencia)
  }
  
  @Test
  def realizarAtaqueDragonDa80Exp
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    Assert.assertEquals(0, dratini.experiencia)
    dratini = dratini.realizarActividad(new RealizarAtaque(fixture.dragon_rage))
    Assert.assertEquals(80, dratini.experiencia)
  }
  
  //FALTA TESTEAR: que alguien con dragón como tipo secundario (kingdra?) use un ataque dragón
  //que se maneje bien el caso de tratar de hacer un ataque que no sé
  //que se maneje bien el caso de atacar si no tengo PP
  //que funcionen bien los ataques con efecto secundario (probar al menos los de la consigna)
  
}