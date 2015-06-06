package tadp.scala.bonita

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore


class PokemonTest {
  
  val raichu = new Especie(20, List(Electrico),500 , None, None)
  val pikachu = new Especie(10, List(Electrico), 350, Some(new SubirNivel(2)), Some(raichu))
  val gastly = new Especie(2, List(Fantasma), 300, None, None)
  val machamp = new Especie(30, List(Pelea), 700, None, None)
  val machoke = new Especie(30, List(Pelea),450 , Some(new Intercambiar), Some(machamp))
  val machop = new Especie(30, List(Pelea), 250, Some(new SubirNivel(10)), Some(machoke))
  val poliwrath = new Especie(20, List(Agua, Pelea), 650, None, None)
  val poliwhirl = new Especie(18, List(Agua), 500, Some(new UsarPiedra), Some(poliwrath))
  val clefable = new Especie(18, List(Normal), 500, None, None)
  val clefairy = new Especie(18, List(Normal), 500, Some(new UsarPiedraLunar), Some(clefable))
  
  @Test
  def `Pokemon electrico levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, pikachu) 
    
    pokemon = pokemon.levantarPesas(15)
    
    assertEquals(15, pokemon.experiencia)
    
  }
  
  @Test(expected = classOf[TypeException])
  def `Pokemon fantasma trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = new Pokemon('F', 50, 50, 1, 3, 3, gastly)
    pokemon.levantarPesas(15)
  }
  
  @Test
  def `Pokemon pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('F', 50, 50, 10, 8, 3, machop)
    
    pokemon = pokemon.levantarPesas(10)
    
    assertEquals(20, pokemon.experiencia)
  }
  
  @Test
  def `Pokemon subtipo pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('M', 50, 50, 10, 4, 3, poliwrath)
    
    pokemon = pokemon.levantarPesas(10)
    
    assertEquals(20, pokemon.experiencia)
  }
  
  @Test(expected = classOf[StrengthException])
  def `Pokemon sin suficiente fuerza levanta pesas` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 2, 6, pikachu) 
   	pokemon.levantarPesas(30)
    
    assert(pokemon.estoyParalizado())
  }
  
  @Test
  def `Pokemon paralizado levanta pesas y pasa a KO` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 2, 6, pikachu)
    pokemon = pokemon.copy(estado = Paralizado)
    
    pokemon = pokemon.levantarPesas(5)
    
    assert(pokemon.estoyKO())
    assertEquals(0, pokemon.experiencia)
  }
  
  @Test(expected = classOf[KOException])
  def `Pokemon knockeado trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = new Pokemon('F', 50, 50, 10, 3, 3, pikachu)
    pokemon = pokemon.copy(estado = KO)
    pokemon.levantarPesas(1)
    
  }
  
  @Test
  def `Pikachu evoluciona a raichu` = {    
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, pikachu) 
    
    pokemon = pokemon.subirUnNivel()
    
    assertEquals(raichu, pokemon.especie)
    
  }
  
  @Test
  def `Pikachu no cumple el nivel necesario para evolucionar a raichu` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, pikachu)
    pokemon = pokemon.copy(nivel = 0)
    
    pokemon.subirUnNivel()
    assertEquals(pikachu, pokemon.especie)
  }
  
  @Test
  def `Machoke evoluciona a Machamp cuando finge ser intercambiado` = {    
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, machoke) 
      
    pokemon = pokemon.fingirIntercambio()
    
    assertEquals(machamp, pokemon.especie)
       
  }
  
  @Test
  def `Machop masculino engorda 1 kilo cuando finge intercambio` = {    
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 10, 5, 6, machop) 
      
    pokemon = pokemon.fingirIntercambio()
    
    assertEquals(11, pokemon.peso)
       
  }
  
  @Test
  def `Machop femenino adelgaza 10 kilos cuando finge intercambio` = {    
    var pokemon : Pokemon = new Pokemon('F', 100, 100, 20, 5, 6, machop) 
      
    pokemon = pokemon.fingirIntercambio()
    
    assertEquals(10, pokemon.peso)
       
  }
  
  @Test
  def `Poliwhirl evoluciona cuando se le da una piedra Agua` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 20, 5, 6, poliwhirl)
    
    pokemon = pokemon.usarPiedra(new Piedra(Agua))
    
    assertEquals(poliwrath, pokemon.especie)
  }
  
  @Test
  def `Poliwhirl queda envenenado si se le da una piedra trueno` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 20, 5, 6, poliwhirl)
    
    pokemon = pokemon.usarPiedra(new Piedra(Electrico))
    
    assert(pokemon.estoyEnvenenado())
  }
  
  @Test
  def `Poliwhirl no evoluciona si se le pasa una piedra lunar` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 20, 5, 6, poliwhirl)
    
    pokemon.usarPiedra(PiedraLunar)
    
    assertEquals(poliwhirl, pokemon.especie)
  }
  
  @Test
  def `Clefairy evoluciona si se le da una piedra lunar` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 20, 5, 6, clefairy)
    
    pokemon = pokemon.usarPiedra(PiedraLunar)
    
    assertEquals(clefable, pokemon.especie)
  }
  
  @Test
  def `Clefairy no evoluciona si se le pasa otra piedra` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 20, 5, 6, clefairy)
    
    pokemon.usarPiedra(new Piedra(Agua))
    
    assertEquals(clefairy, pokemon.especie)
  }

  // Testeo del manejo de la experiencia
  @Test
  def `Pikachu necesita 2450 puntos de experiencia para subir al nivel 4` = {
    assertEquals(pikachu.experienciaParaNivel(4), 2450)
  }
  
  @Test
  def `Un pikachu de nivel 2 sube de nivel cuando gana suficiente experiencia` = {
    var pika: Pokemon = new Pokemon('H', 40, 40, 15, 10, 15, pikachu)
    pika = pika.copy(experiencia = 600)
    pika = pika.copy(nivel = 2) //Con 600 pts de exp, este pikachu está en nivel 2
    pika = pika.ganarExperiencia(600)
    
    assertEquals(pika.experiencia, 1200)
    assertEquals(pika.nivel, 3)
  }
  
  // Testeo de tipos
  @Test
  def `El tipo Fuego le gana al tipo Planta` = {
    assert(Fuego.leGanaA(Planta))
  }
  
  @Test
  def `El tipo Fuego no le gana al tipo Agua` = {
    assert(!Fuego.leGanaA(Agua))
  }
  
  @Test
  def `Poliwhirl pierde contra el tipo eléctrico por ser de agua` = {
    var pokemon : Pokemon = new Pokemon('M', 100, 100, 20, 5, 6, poliwhirl)
    assert(pokemon.pierdeCon(Electrico))
  }

 }