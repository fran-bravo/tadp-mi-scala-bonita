package tadp.scala.bonita
import org.junit.Test
import org.junit.Assert
import scala.util.Try

/**
 * @author Dario
 */
class UsarItemsSencillosTest 
{
   @Test
   def `pokemon que usa pocion se cura 50 de energia`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.perderEnergia(60) //quedaria en 40
     pika = pika.realizarActividad(UsarPocion).get //ahora deberia ser 90
     Assert.assertEquals(90, pika.energia)
   }
   
   @Test
   def `pokemon que usa pocion se cura pero no se exceede de su energia maxima`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.perderEnergia(20) //quedaria en 80
     pika = pika.realizarActividad(UsarPocion).get //no deberia pasar de 100
     Assert.assertEquals(100, pika.energia)
   }
   
   @Test
   def `pokemon envenenado se cura con un antidoto`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAEnvenenado()
     Assert.assertEquals(Envenenado, pika.estado)   
     pika = pika.realizarActividad(UsarAntidoto).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test
   def `ether cura veneno`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAEnvenenado()
     Assert.assertEquals(Envenenado, pika.estado)   
     pika = pika.realizarActividad(UsarEther).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test
   def `ether cura suenio`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarADormido()
     Assert.assertEquals(Dormido(3), pika.estado)  
     //whoops, esto es una limitación de JUnit, quisiera poder hacer Dormido(_)
     pika = pika.realizarActividad(UsarEther).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test
   def `ether cura paralisis`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAParalizado()
     Assert.assertEquals(Paralizado, pika.estado)   
     pika = pika.realizarActividad(UsarEther).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test(expected = classOf[KOException])
   def `ether no cura KO`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAKO()
     Assert.assertEquals(KO, pika.estado)   
     pika.realizarActividad(UsarEther).get
//     Assert.assertEquals(KO, pika.estado) 
     //todo debería romper o quedar KO sin romper? es un detalle igual
   }
   
   
   @Test
   def `hierro aumenta en 5 la fuerza de un pokemon`
   {
     var pika = fixture.nuevoPikachuM()
     Assert.assertEquals(2, pika.fuerzaBase)
     pika = pika.realizarActividad(ComerHierro).get
     Assert.assertEquals(7, pika.fuerzaBase)
   }
   
   @Test
   def `calcio aumenta en 5 la velocidad de un pokemon`
   {
     var pika = fixture.nuevoPikachuM()
     Assert.assertEquals(6, pika.velocidadBase)
     pika = pika.realizarActividad(ComerCalcio).get
     Assert.assertEquals(11, pika.velocidadBase)
   }
   
   @Test
   def `zinc aumenta en 2 el pp max de cada ataque`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.realizarActividad(AprenderAtaque(fixture.thunderbolt)).get
     pika = pika.realizarActividad(AprenderAtaque(fixture.pound)).get
     Assert.assertEquals(15, pika.paMax(fixture.thunderbolt))
     Assert.assertEquals(35, pika.paMax(fixture.pound))
     pika = pika.realizarActividad(ComerZinc).get
     Assert.assertEquals(17, pika.paMax(fixture.thunderbolt))
     Assert.assertEquals(37, pika.paMax(fixture.pound))
   }
   
  @Test
  def `un pokemon con un ataque come zinc`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    val ppThunderbolt = pikachu.paMax(fixture.thunderbolt)
    
    pikachu = pikachu.realizarActividad(ComerZinc).get
    
    Assert.assertEquals(ppThunderbolt+2, pikachu.paMax(fixture.thunderbolt))
    
  }
  
  @Test
  def `un pokemon con muchos ataques come zinc`
  {
    var pikachu = fixture.nuevoPikachuMultiplesAtaques()
    
    val ppThunderbolt = pikachu.paMax(fixture.thunderbolt)
    val ppThunder= pikachu.paMax(fixture.thunder)
    val ppStorm = pikachu.paMax(fixture.storm)
    
    pikachu = pikachu.realizarActividad(ComerZinc).get
    
    Assert.assertEquals(ppThunderbolt+2, pikachu.paMax(fixture.thunderbolt))
    Assert.assertEquals(ppThunder+2, pikachu.paMax(fixture.thunder))
    Assert.assertEquals(ppStorm+2, pikachu.paMax(fixture.storm))
    
  }
}