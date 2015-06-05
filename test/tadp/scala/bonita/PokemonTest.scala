package tadp.scala.bonita

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore


class PokemonTest {
  
  val pikachu = new Especie(10, List(new Electrico))
  val gastly = new Especie(2, List(new Fantasma))
  val machop = new Especie(30, List(new Pelea))
  val poliwrath = new Especie(20, List(new Agua, new Pelea))
  
  @Test
  def `Pokemon electrico levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, pikachu) 
    
    pokemon.levantarPesas(15)
    
    assertEquals(15, pokemon.experiencia)
    
  }
  
  @Test(expected = classOf[TypeException])
  def `Pokemon fantasma trata de levantar pesas` = {
    var pokemon : Pokemon = new Pokemon('F', 50, 50, 1, 3, 3, gastly)
    pokemon.levantarPesas(15)
  }
  
  @Test
  def `Pokemon pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('F', 50, 50, 10, 8, 3, machop)
    
    pokemon.levantarPesas(10)
    
    assertEquals(20, pokemon.experiencia)
  }
  
  @Test
  def `Pokemon subtipo pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('M', 50, 50, 10, 4, 3, poliwrath)
    
    pokemon.levantarPesas(10)
    
    assertEquals(20, pokemon.experiencia)
  }
  
  @Test(expected = classOf[StrengthException])
  def `Pokemon sin suficiente fuerza levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 2, 6, pikachu) 
   	pokemon.levantarPesas(30)    
  }
  
  @Test
  def `Pokemon paralizado levanta pesas y pasa a KO` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 2, 6, pikachu)
    pokemon.estado = new Paralizado
    
    pokemon.levantarPesas(5)
    
    assertEquals(true, pokemon.estoyKO())
    assertEquals(0, pokemon.experiencia)
  }
  
  @Test(expected = classOf[KOException])
  def `Pokemon knockeado trata de levantar pesas` = {
    var pokemon : Pokemon = new Pokemon('F', 50, 50, 10, 3, 3, pikachu)
    pokemon.estado = new KO
     pokemon.levantarPesas(1)
    
  }
  
  }