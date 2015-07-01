package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert
import scala.util.Try
import Actividades._

class ActividadesFuncionalosasTest {

  @Test
  def `un pokemon dormido ignora la actividad`
  { 
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarADormido()
    var pika : Try[Pokemon] = realizar(pikachu, (levantarPesas (4) _))
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, pika.get.experiencia)
  }
  
  @Test
  def `Pokemon electrico levanta pesas` = {
    var pokemon : Pokemon = fixture.nuevoPikachuM() 
    
    var poke : Try[Pokemon] = realizar(pokemon, (levantarPesas(15) _))
    
    val exp: BigInt = 15
    Assert.assertEquals(exp, poke.get.experiencia)    
  }
  
  @Test
  def `realizar ataque de tipo principal da 50 exp`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(pikachu, fixture.thunderbolt.efecto(pikachu))
    
    var exp: BigInt = 0
    Assert.assertEquals(exp, pikachu.experiencia)
    var pika = realizar(pikachu, realizarAtaque(fixture.thunderbolt)) //ac· est· infiriendo la aplicaciÛn parcial
    exp = 50
    Assert.assertEquals(exp, pika.get.experiencia)
  }
  
  @Test
  def `nadar aumenta en 1 punto la velocidad base por hora de nado para los tipo agua`
  {
    var poliwhirl = fixture.nuevoPoliwhirlM()
    Assert.assertEquals(6, poliwhirl.velocidadBase)
    var poli : Try[Pokemon] = realizar(poliwhirl, nadar(60))
    Assert.assertEquals(7, poli.get.velocidadBase)
    //ac√° tengo que preguntar s√≠ o s√≠ por la velocidad base
    //porque este nado le da tanta experiencia que levea, y me jode con la velocidad final
  }
  
}