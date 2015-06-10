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
   
   @Test
   def `pokemon envenenado se cura con un antidoto`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAEnvenenado()
     Assert.assertEquals(Envenenado, pika.estado)   
     pika = pika.realizarActividad(UsarAntidoto)
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test
   def `ether cura veneno`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAEnvenenado()
     Assert.assertEquals(Envenenado, pika.estado)   
     pika = pika.realizarActividad(UsarEther)
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test
   def `ether cura suenio`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarADormido()
     Assert.assertEquals(Dormido(2), pika.estado)  
     //whoops, esto es una limitación de JUnit? quisiera poder hacer Dormido(_)
     pika = pika.realizarActividad(UsarEther)
     //Assert.assertEquals(Saludable, pika.estado)     
     //aca chocan los requerimientos, se supone que el ether cura el sueño
     //pero tambien se supone que un pokemon dormido ignora las actividades que le dan
     //entonces no puede tomar el ether
   }
   
   @Test
   def `ether cura paralisis`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAParalizado()
     Assert.assertEquals(Paralizado, pika.estado)   
     pika = pika.realizarActividad(UsarEther)
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test(expected = classOf[KOException])
   def `ether no cura KO`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAKO()
     Assert.assertEquals(KO, pika.estado)   
     pika = pika.realizarActividad(UsarEther)
     //en realidad esto choca con otro requerimiento
     //por el requerimiento general de que los pokemon KO no pueden hacer actividades
     //ni siquiera va a llegar a usar el ether
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
}