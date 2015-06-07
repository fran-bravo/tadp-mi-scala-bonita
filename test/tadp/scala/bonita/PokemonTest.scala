package tadp.scala.bonita

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore

class PokemonTest {
  
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
  
  @Test
  def `Pikachu evoluciona a raichu` = {    
    var pokemon : Pokemon = fixture.nuevoPikachuParaEvolucion()
    
    pokemon = pokemon.subirUnNivel()
    
    assertEquals(fixture.raichu, pokemon.especie)
    
  }
  
  @Test
  def `Pikachu no cumple el nivel necesario para evolucionar a raichu` = {
    var pokemon : Pokemon = fixture.nuevoPikachuParaEvolucion()
    pokemon = pokemon.copy(nivel = 0)
    
    pokemon.subirUnNivel()
    assertEquals(fixture.pikachu, pokemon.especie)
  }
  
  @Test
  def `Machoke evoluciona a Machamp cuando finge ser intercambiado` = {    
    var pokemon : Pokemon = new Pokemon(Macho, 100, 100, 10, 5, 6, fixture.machoke) 
      
    pokemon = pokemon.fingirIntercambio()
    
    assertEquals(fixture.machamp, pokemon.especie)
       
  }
  
  @Test
  def `Machop masculino engorda 1 kilo cuando finge intercambio` = {    
    var pokemon : Pokemon = new Pokemon(Macho, 100, 100, 10, 5, 6, fixture.machop) 
      
    pokemon = pokemon.fingirIntercambio()
    
    assertEquals(11, pokemon.peso)
       
  }
  
  @Test
  def `Machop femenino adelgaza 10 kilos cuando finge intercambio` = {    
    var pokemon : Pokemon = new Pokemon(Hembra, 100, 100, 20, 5, 6, fixture.machop) 
      
    pokemon = pokemon.fingirIntercambio()
    
    assertEquals(10, pokemon.peso)
       
  }
  
  @Test
  def `Poliwhirl evoluciona cuando se le da una piedra Agua` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = pokemon.usarPiedra(new Piedra(Agua))
    
    assertEquals(fixture.poliwrath, pokemon.especie)
  }
  
  @Test
  def `Poliwhirl queda envenenado si se le da una piedra trueno` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = pokemon.usarPiedra(new Piedra(Electrico))
    
    assert(pokemon.estoyEnvenenado())
  }
  
  @Test
  def `Poliwhirl no evoluciona si se le pasa una piedra lunar` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon.usarPiedra(PiedraLunar)
    
    assertEquals(fixture.poliwhirl, pokemon.especie)
  }
  
  @Test
  def `Clefairy evoluciona si se le da una piedra lunar` = {
    var pokemon : Pokemon = fixture.nuevoClefairyM()
    
    pokemon = pokemon.usarPiedra(PiedraLunar)
    
    assertEquals(fixture.clefable, pokemon.especie)
  }
  
  @Test
  def `Clefairy no evoluciona si se le pasa otra piedra` = {
    var pokemon : Pokemon = fixture.nuevoClefairyM()
    
    pokemon.usarPiedra(new Piedra(Agua))
    
    assertEquals(fixture.clefairy, pokemon.especie)
  }

  // Testeo del manejo de la experiencia
  @Test
  def `Pikachu necesita 2450 puntos de experiencia para subir al nivel 4` = {
    assertEquals(fixture.pikachu.experienciaParaNivel(4), 2450)
  }
  
  @Test
  def `Un pikachu de nivel 2 sube de nivel cuando gana suficiente experiencia` = {
    var pika: Pokemon = new Pokemon(Hembra, 40, 40, 15, 10, 15, fixture.pikachu)
    pika = pika.copy(experiencia = 600)
    pika = pika.copy(nivel = 2) //Con 600 pts de exp, este pikachu est� en nivel 2
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
  def `Poliwhirl pierde contra el tipo electrico por ser de agua` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    assert(pokemon.pierdeCon(Electrico))
  }

 }