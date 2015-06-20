package tadp.scala.bonita

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Ignore
import scala.util.Try

class UsarPiedraTest{
 
   @Test
  def `Poliwhirl evoluciona cuando se le da una piedra Agua` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = pokemon.realizarActividad(DarPiedra(new Piedra(Agua))).get
    
    assertEquals(fixture.poliwrath, pokemon.especie)
  }
  
  @Test
  def `Poliwhirl queda envenenado si se le da una piedra trueno` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = pokemon.realizarActividad(DarPiedra(new Piedra(Electrico))).get
    
    assert(pokemon.estoyEnvenenado())
  }
  
  @Test
  def `Poliwhirl no evoluciona si se le pasa una piedra lunar` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = pokemon.realizarActividad(DarPiedra(PiedraLunar)).get
    
    assertEquals(fixture.poliwhirl, pokemon.especie)
  }
  
  @Test
  def `Clefairy evoluciona si se le da una piedra lunar` = {
    var pokemon : Pokemon = fixture.nuevoClefairyM()
    
    pokemon = pokemon.realizarActividad(DarPiedra(PiedraLunar)).get
    
    assertEquals(fixture.clefable, pokemon.especie)
  }
  
  @Test
  def `Clefairy no evoluciona si se le pasa otra piedra` = {
    var pokemon : Pokemon = fixture.nuevoClefairyM()
    
    pokemon = pokemon.realizarActividad(DarPiedra(new Piedra(Agua))).get
    
    assertEquals(fixture.clefairy, pokemon.especie)
  }
  
  @Test
  def `Dragonite queda envenenado si se le da una piedra Dragon (ponele que existe)` = {
    var dragonite: Pokemon = fixture.nuevoDragoniteM()
    dragonite = dragonite.realizarActividad(DarPiedra(new Piedra(Dragon))).get
    
    assert(dragonite.estoyEnvenenado())
  }

  @Test
  def `A charizard no le pasa nada si se le da una piedra Fuego` = {
    val charizard: Pokemon = fixture.nuevoCharizardF()
    
    assertEquals(charizard, charizard.realizarActividad(DarPiedra(new Piedra(Fuego))).get)
  }
  
  @Test
  def `A aerodactyl no le pasa nada si se le da una piedra Lunar` = {
    val aerodactyl: Pokemon = fixture.nuevoAerodactylF()
    
    assertEquals(aerodactyl, aerodactyl.realizarActividad(DarPiedra(PiedraLunar)).get)
  }
}