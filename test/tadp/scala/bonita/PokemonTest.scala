package tadp.scala.bonita

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore


class PokemonTest {
  
  val raichu = new Especie(20, List(new Electrico),500 , new SubirNivel(0))
  val pikachu = new Especie(10, List(new Electrico), 350, new SubirNivel(2), raichu)
  val gastly = new Especie(2, List(new Fantasma), 300, new SubirNivel(10))
  val machamp = new Especie(30, List(new Pelea), 700)
  val machoke = new Especie(30, List(new Pelea),450 , new Intercambiar, machamp)
  val machop = new Especie(30, List(new Pelea), 250, new SubirNivel(10))
  val poliwrath = new Especie(20, List(new Agua, new Pelea), 650, new SubirNivel(10))
  
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
    
    assertEquals(true, pokemon.estoyParalizado())
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
 
  @Test(expected = classOf[EvolutionException])
  def `Raichu no puede evolucionar` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, raichu) 
    
    pokemon.evolucionar()
       
  }
  
  @Test
  def `Pikachu evoluciona a raichu` = {    
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, pikachu) 
    pokemon.nivel = 2
    
    pokemon.evolucionar()
    
    assertEquals(raichu, pokemon.especie)
    
  }
  
  @Test(expected = classOf[EvolutionException])
  def `Pikachu no cumple el nivel necesario para evolucionar a raichu` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, pikachu) 
    
    pokemon.evolucionar()
    
  }
  
  @Test
  def `Machoke evoluciona a Machamp cuando finge ser intercambiado` = {    
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, machoke) 
      
    pokemon.fingirIntercambio()
    
    assertEquals(machamp, pokemon.especie)
       
  }
  
  @Test
  def `Machop masculino engorda 1 kilo cuando finge intercambio` = {    
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, machop) 
      
    pokemon.fingirIntercambio()
    
    assertEquals(11, pokemon.peso)
       
  }
  
  @Test
  def `Machop femenino adelgaza 10 kilos cuando finge intercambio` = {    
    var pokemon : Pokemon = new Pokemon('F', 100, 100, 20, 5, 6, machop) 
      
    pokemon.fingirIntercambio()
    
    assertEquals(10, pokemon.peso)
       
  }

  // Testeo del manejo de la experiencia
  @Test
  def `Pikachu necesita 2450 puntos de experiencia para subir al nivel 4` = {
    assertEquals(pikachu.experienciaParaNivel(4), 2450)
  }
  
  @Test
  def `Un pikachu de nivel 2 sube de nivel cuando gana suficiente experiencia` = {
    var pika: Pokemon = new Pokemon('H', 40, 40, 15, 10, 15, pikachu)
    pika.experiencia = 600
    pika.nivel = 2 //Con 600 pts de exp, este pikachu está en nivel 2
    pika.ganarExperiencia(600)
    
    assertEquals(pika.experiencia, 1200)
    assertEquals(pika.nivel, 3)
  }

 }