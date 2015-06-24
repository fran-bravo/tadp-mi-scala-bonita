package tadp.scala.bonita

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Assert
import scala.util.Try

/**
 * @author Dario

 */
class LevantarPesasTest 
{
  
  @Test
  def `Pokemon electrico levanta pesas` = {
    var pokemon : Pokemon = fixture.nuevoPikachuM() 
    
    var poke : Try[Pokemon] = pokemon.realizarActividad(LevantarPesas(15))
    
    val exp: BigInt = 15
    assertEquals(exp, poke.get.experiencia)
    
  }
  
  @Test(expected = classOf[TypeException])
  def `Pokemon fantasma trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = new Pokemon(Hembra, 50, 50, 1, 3, 3, fixture.gastly)
    pokemon.realizarActividad(LevantarPesas(15)).get
  }
  
  @Test
  def `Pokemon pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Hembra, 50, 50, 10, 8, 3, fixture.machop)
    
    var poke : Try[Pokemon] = pokemon.realizarActividad(LevantarPesas(10))
    
    val exp: BigInt = 20
    assertEquals(exp, poke.get.experiencia)
  }
  
  @Test
  def `Pokemon subtipo pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Macho, 50, 50, 10, 4, 3, fixture.poliwrath)
    
    var poke : Try[Pokemon] = pokemon.realizarActividad(LevantarPesas(10))
    
    val exp: BigInt = 20
    assertEquals(exp, poke.get.experiencia)
  }
  
  @Test
  def `Pokemon sin suficiente fuerza levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Macho, 100, 100, 10, 2, 6, fixture.pikachu) 
    var poke : Try[Pokemon] = pokemon.realizarActividad(LevantarPesas(30))
    
    val exp: BigInt = 0
    assertEquals(exp, poke.get.experiencia) 
    assertEquals(Paralizado, poke.get.estado)
  }
  
  @Test
  def `Pokemon paralizado levanta pesas y pasa a KO`: Unit = {
    var pokemon : Pokemon = fixture.nuevoPikachuM()
    pokemon = pokemon.copy(estado = Paralizado)
    
    var poke : Try[Pokemon] = pokemon.realizarActividad(LevantarPesas(5))
    
    val exp: BigInt = 0
    Assert.assertEquals(KO, poke.get.estado)
    assertEquals(exp, poke.get.experiencia)
  }
  
  @Test(expected = classOf[KOException])
  def `Pokemon knockeado trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = fixture.nuevoPikachuM()
    pokemon = pokemon.copy(estado = KO)
    pokemon.realizarActividad(LevantarPesas(1)).get
    
  }
}