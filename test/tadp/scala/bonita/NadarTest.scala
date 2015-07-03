package tadp.scala.bonita
import org.junit.Assert
import org.junit.Test
import scala.util.Try

/**
 * @author Dario
 */
class NadarTest {
  
  @Test
  def `pokemon con tipo principal que pierde con agua queda KO y no gana experiencia si intenta nadar`
  {
    var charizard = fixture.nuevoCharizardM()
    var chari : Try[Pokemon] = charizard.realizarActividad(Nadar(1)) 
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, chari.get.experiencia)
    Assert.assertEquals(KO, chari.get.estado)
  }
  
  @Test
  def `pokemon con tipo secundario que pierde con agua queda KO y no gana experiencia si intenta nadar`
  {
    var aerodactyl = fixture.nuevoAerodactylF()
    aerodactyl = aerodactyl.realizarActividad(Nadar(1)).get 
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, aerodactyl.experiencia)
    Assert.assertEquals(KO, aerodactyl.estado)
  }
  
  @Test 
  def `nadar quita tanta energia como minutos se nade`
  {
    var dratini = fixture.nuevoDratiniM()
    Assert.assertEquals(100, dratini.energia)
    dratini = dratini.realizarActividad(Nadar(8)).get
    Assert.assertEquals(92, dratini.energia)
  }
  
  @Test 
  def `nadar da 200 de exp por cada minuto que se nade`
  {
    var dratini = fixture.nuevoDratiniM()
    var exp: BigInt = 0
    Assert.assertEquals(exp, dratini.experiencia)
    dratini = dratini.realizarActividad(Nadar(8)).get
    exp = 1600
    Assert.assertEquals(exp, dratini.experiencia)
  }
  
  @Test
  def `nadar aumenta en 1 punto la velocidad base por hora de nado para los tipo agua`
  {
    var poliwhirl = fixture.nuevoPoliwhirlM()
    Assert.assertEquals(6, poliwhirl.velocidadBase)
    poliwhirl = poliwhirl.realizarActividad(Nadar(60)).get
    Assert.assertEquals(7, poliwhirl.velocidadBase)
    //acá tengo que preguntar sí o sí por la velocidad base
    //porque este nado le da tanta experiencia que levea, y me jode con la velocidad final
  }
  
   @Test
   def `nadar menos de una hora no aumenta la velocidad base para los tipo agua`
   {
    var poliwhirl = fixture.nuevoPoliwhirlM()
    Assert.assertEquals(6, poliwhirl.velocidadBase)
    poliwhirl = poliwhirl.realizarActividad(Nadar(59)).get
    Assert.assertEquals(6, poliwhirl.velocidadBase)
   }
   
   def `nadar dos horas aumenta en dos la velocidad base a los tipo agua`
   {
     var poliwhirl = fixture.nuevoPoliwhirlM()
     Assert.assertEquals(6, poliwhirl.velocidadBase)
     poliwhirl = poliwhirl.realizarActividad(Nadar(130)).get //nado dos horas y un puchito
     Assert.assertEquals(8, poliwhirl.velocidadBase)
     //TODO duda importante!! acá poliwhirl se mata nadando demasiado (energía = 0) y queda KO
     //pero el enunciado no dice que tengamos que pararlo....
     //cuenta como característica inválida? no empezó la actividad estando KO 
     //pero sí llegó a KO durante la actividad... ¿cómo se cuenta eso?
   }
  
}