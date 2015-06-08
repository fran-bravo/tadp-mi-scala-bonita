package tadp.scala.bonita

import org.junit.Assert.assertEquals

import org.junit.Test

/**
 * @author Dario

 */
class LevantarPesasTest {
  
  @Test
  def `Pokemon electrico levanta pesas` = {
    var pokemon : Pokemon = fixture.nuevoPikachuM() 
    
    pokemon = pokemon.levantarPesas(15)
    
    assertEquals(15, pokemon.experiencia)
    
  }
  
  @Test(expected = classOf[TypeException])
  def `Pokemon fantasma trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = new Pokemon(Hembra, 50, 50, 1, 3, 3, fixture.gastly)
    pokemon.levantarPesas(15)
  }
  
  @Test
  def `Pokemon pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Hembra, 50, 50, 10, 8, 3, fixture.machop)
    
    pokemon = pokemon.levantarPesas(10)
    
    assertEquals(20, pokemon.experiencia)
  }
  
  @Test
  def `Pokemon subtipo pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Macho, 50, 50, 10, 4, 3, fixture.poliwrath)
    
    pokemon = pokemon.levantarPesas(10)
    
    assertEquals(20, pokemon.experiencia)
  }
  
  @Test(expected = classOf[StrengthException])
  def `Pokemon sin suficiente fuerza levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Macho, 100, 100, 10, 2, 6, fixture.pikachu) 
    pokemon.levantarPesas(30)
    
    assert(pokemon.estoyParalizado())
  }
  
  @Test
  def `Pokemon paralizado levanta pesas y pasa a KO` = {
    var pokemon : Pokemon = fixture.nuevoPikachuM()
    pokemon = pokemon.copy(estado = Paralizado)
    
    pokemon = pokemon.levantarPesas(5)
    
    assert(pokemon.estoyKO())
    assertEquals(0, pokemon.experiencia)
  }
  
  @Test(expected = classOf[KOException])
  def `Pokemon knockeado trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = fixture.nuevoPikachuM()
    pokemon = pokemon.copy(estado = KO)
    pokemon.levantarPesas(1)
    
  }
}