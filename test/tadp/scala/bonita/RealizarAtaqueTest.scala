package tadp.scala.bonita
import org.junit.Assert
import org.junit.Test
import scala.util.Try

/**
 * @author Dario
 */
class RealizarAtaqueTest 
{
  @Test
  def `realizar un ataque gasta PA`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    var pika = pikachu.realizarActividad(new RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(14, pika.get.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `funciona el recuperar PA`
  { //estrictamente hablando este test no va acá, pero lo tengo que probar YA
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    var pika = pikachu.realizarActividad(new RealizarAtaque(fixture.thunderbolt))
    Assert.assertEquals(14, pika.get.paActual(fixture.thunderbolt))
    pikachu = pika.get.recuperarPA()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt)) 
  }
  
  
  @Test
  def `realizar ataque de tipo principal da 50 exp`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(pikachu, fixture.thunderbolt.efecto(pikachu))
    
    var exp: BigInt = 0
    Assert.assertEquals(exp, pikachu.experiencia)
    var pika = pikachu.realizarActividad(new RealizarAtaque(fixture.thunderbolt))
    exp = 50
    Assert.assertEquals(exp, pika.get.experiencia)
  }
  
  @Test
  def `realizar ataque de tipo secundario da 20 exp a un macho`
  {
    var charizard = fixture.nuevoCharizardMConFly()
    
    var exp: BigInt = 0
    Assert.assertEquals(exp, charizard.experiencia)
    var chari = charizard.realizarActividad(new RealizarAtaque(fixture.fly))
    exp = 20
    Assert.assertEquals(exp, chari.get.experiencia)
  }

  @Test
  def `realizar ataque de tipo secundario da 40 exp a una hembra`
  {
    var charizard = fixture.nuevoCharizardFConFly()
    var exp: BigInt = 0
    Assert.assertEquals(exp, charizard.experiencia)
    var chari = charizard.realizarActividad(new RealizarAtaque(fixture.fly))
    exp = 40
    Assert.assertEquals(exp, chari.get.experiencia)
  }
  
  @Test
  def `realizar ataque dragon da 80 exp`
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    var exp: BigInt = 0
    Assert.assertEquals(exp, dratini.experiencia)
    var drati = dratini.realizarActividad(new RealizarAtaque(fixture.dragon_rage))
    exp = 80
    Assert.assertEquals(exp, drati.get.experiencia)
  }
  
  
  @Test(expected = classOf[UnknownAttackException])
  def `un pokemon no conoce el ataque y quiere realizarlo igual`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu.realizarActividad(RealizarAtaque(fixture.thunderbolt)).get
  }
  
  @Test(expected = classOf[UnknownAttackException])
  def `un pokemon no conoce el ataque, pero conoce otro y quiere realizarlo igual`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu.realizarActividad(RealizarAtaque(fixture.storm)).get

  }
  
  @Test
  def `un pokemon no conoce ataque`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.thunderbolt))
  }
  
  @Test
  def `un pokemon realiza ataque con reposar`
  {
    var abra = fixture.nuevoAbraConRest()
    abra = abra.perderEnergia(10) //Hago que pierda energía para comprobar que se recupera toda
    var aby = abra.realizarActividad(RealizarAtaque(fixture.rest))
    
    var exp: BigInt = 50
    Assert.assertEquals(exp, aby.get.experiencia)
    Assert.assertEquals(Dormido(), aby.get.estado)
    exp = 20
    Assert.assertEquals(exp, aby.get.energia)
  }
  
  @Test
  def `un pokemon realiza ataque con endurecer`
  {
    var clefairy = fixture.nuevoClefairyConEndurance()
    clefairy = clefairy.perderEnergia(20)
    var clefa = clefairy.realizarActividad(RealizarAtaque(fixture.endurecerse))
    
    var exp: BigInt = 50
    Assert.assertEquals(exp, clefa.get.experiencia)
    Assert.assertEquals(Paralizado, clefa.get.estado)
    exp = 65
    Assert.assertEquals(exp, clefa.get.energia)
    
  }
  
  @Test
  def `un pokemon realiza ataque con enfocar`
  {
    var clefairy = fixture.nuevoClefairyConFocus()
    val velocidadPrevia = clefairy.velocidad
    var clefa = clefairy.realizarActividad(RealizarAtaque(fixture.enfocarse))
    
    val exp: BigInt = 50
    Assert.assertEquals(exp, clefa.get.experiencia)
    Assert.assertEquals(velocidadPrevia+1, clefa.get.velocidad)
    
  }
    
  @Test
  def `un pokemon Agua-Dragon realiza ataque dragon`
  {
    var kingdra = fixture.nuevoKingdraConDragonRage()
    var king = kingdra.realizarActividad(RealizarAtaque(fixture.dragon_rage))
    
    val exp: BigInt = 80
    Assert.assertEquals(exp, king.get.experiencia)
  }
  
  @Test(expected = classOf[NoRemainingPPException])
  def `un pokemon quiere realiza ataque sin pps`
  {
    var clefairy = fixture.nuevoClefairyConHyperBeam()
    //Hiper rayo tiene 5 PPs
    clefairy = fixture.pokemonUsa5hiperrayos(clefairy)
    clefairy.realizarActividad(RealizarAtaque(fixture.hiper_rayo)).get

  }
  
  @Test
  def `Un pokemon con tipo secundario no gana experiencia si el ataque no es de su tipo`{
    var dragonite = fixture.nuevoDragoniteMConHiperRayo()
    var drago = dragonite.realizarActividad(RealizarAtaque(fixture.hiper_rayo))
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, drago.get.experiencia)
  }
  
  @Test
  def `Un pokemon sin tipo secundario no gana experiencia si el ataque no es de su tipo`{
    var dratini = fixture.nuevoDratiniMConHiperRayo()
    var drati = dratini.realizarActividad(RealizarAtaque(fixture.hiper_rayo))
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, drati.get.experiencia)
  }
  
  
}