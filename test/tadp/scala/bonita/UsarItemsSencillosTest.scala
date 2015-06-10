package tadp.scala.bonita
import org.junit.Test
import org.junit.Assert

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
     pika = pika.realizarActividad(UsarPocion) //ahora deberia ser 90
     Assert.assertEquals(90, pika.energia)
   }
   
   @Test
   def `pokemon que usa pocion se cura pero no se exceede de su energia maxima`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.perderEnergia(20) //quedaria en 80
     pika = pika.realizarActividad(UsarPocion) //no deberia pasar de 100
     Assert.assertEquals(100, pika.energia)
   }
}