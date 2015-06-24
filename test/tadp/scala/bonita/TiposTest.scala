package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert

class TiposTest {
  @Test
  def `tipo dragon le gana a tipo dragon`
  {
    Assert.assertTrue(Dragon.leGanaA(Dragon))   
  }
  
  @Test
  def `tipo hielo le gana a tipo planta, tierra, volador, dragon`
  {
    Assert.assertTrue(Hielo.leGanaA(Planta))
    Assert.assertTrue(Hielo.leGanaA(Tierra))
    Assert.assertTrue(Hielo.leGanaA(Volador))
    Assert.assertTrue(Hielo.leGanaA(Dragon))
  }

  @Test
  def `tipo fuego le gana a tipo hielo, planta, bicho`
  {
    Assert.assertTrue(Fuego.leGanaA(Planta))
    Assert.assertTrue(Fuego.leGanaA(Hielo))
    Assert.assertTrue(Fuego.leGanaA(Bicho))
  }

  @Test
  def `tipo agua le gana a tipo fuego, tierra, roca`
  {
    Assert.assertTrue(Agua.leGanaA(Fuego))
    Assert.assertTrue(Agua.leGanaA(Tierra))
    Assert.assertTrue(Agua.leGanaA(Roca))
  }
  
  @Test
  def `tipo planta le gana a tipo agua, tierra, roca`
  {
    Assert.assertTrue(Planta.leGanaA(Agua))
    Assert.assertTrue(Planta.leGanaA(Tierra))
    Assert.assertTrue(Planta.leGanaA(Roca))
  }


  @Test
  def `tipo tierra le gana a tipo fuego, electrico, veneno, roca`
  {
    Assert.assertTrue(Tierra.leGanaA(Fuego))
    Assert.assertTrue(Tierra.leGanaA(Electrico))
    Assert.assertTrue(Tierra.leGanaA(Veneno))
    Assert.assertTrue(Tierra.leGanaA(Roca))
  }
  
  @Test
  def `tipo roca le gana a tipo fuego, hielo, volador, bicho`
  {
    Assert.assertTrue(Roca.leGanaA(Bicho))
    Assert.assertTrue(Roca.leGanaA(Fuego))
    Assert.assertTrue(Roca.leGanaA(Hielo))
    Assert.assertTrue(Roca.leGanaA(Volador))
  }
  
  @Test
  def `tipo electrico le gana a tipo agua, volador`
  {
    Assert.assertTrue(Electrico.leGanaA(Agua))
    Assert.assertTrue(Electrico.leGanaA(Volador))
  }
  
  @Test
  def `tipo psiquico le gana a tipo pelea, veneno`
  {
    Assert.assertTrue(Psiquico.leGanaA(Pelea))
    Assert.assertTrue(Psiquico.leGanaA(Veneno))
  }
  
  @Test
  def `tipo pelea le gana a tipo normal, hielo, roca`
  {
    Assert.assertTrue(Pelea.leGanaA(Normal))
    Assert.assertTrue(Pelea.leGanaA(Hielo))
    Assert.assertTrue(Pelea.leGanaA(Roca))
  }
  
  @Test
  def `tipo fantasma le gana a tipo psiquico, fantasma`
  {
    Assert.assertTrue(Fantasma.leGanaA(Psiquico))
    Assert.assertTrue(Fantasma.leGanaA(Fantasma))
  }
  
  @Test
  def `tipo volador le gana a tipo planta, pelea, bicho`
  {
    Assert.assertTrue(Volador.leGanaA(Planta))
    Assert.assertTrue(Volador.leGanaA(Pelea))
    Assert.assertTrue(Volador.leGanaA(Bicho))
  }
  
  @Test
  def `tipo bicho le gana a tipo planta, psiquico`
  {
    Assert.assertTrue(Bicho.leGanaA(Planta))
    Assert.assertTrue(Bicho.leGanaA(Psiquico))
  }
  
  @Test
  def `tipo veneno le gana a tipo planta`
  {
    Assert.assertTrue(Veneno.leGanaA(Planta))
  }
  
  @Test
  def `tipo normal no le gana a tipo fuego, electrico, veneno, roca`
  {
    Assert.assertFalse(Normal.leGanaA(Fuego))
    Assert.assertFalse(Normal.leGanaA(Electrico))
    Assert.assertFalse(Normal.leGanaA(Veneno))
    Assert.assertFalse(Normal.leGanaA(Roca))
  }
  
  @Test
  def `Poliwrath tiene el tipo agua`{
     var pokemon : Pokemon = new Pokemon(Macho, 50, 50, 10, 4, 3, fixture.poliwrath)
     Assert.assertTrue(pokemon.tieneElTipo(Agua))
  }
  
  @Test
  def `Poliwrath tiene el tipo pelea`{
     var pokemon : Pokemon = new Pokemon(Macho, 50, 50, 10, 4, 3, fixture.poliwrath)
     Assert.assertTrue(pokemon.tieneElTipo(Pelea))
  }
  
  @Test
  def `Poliwrath no tiene el tipo fantasma`{
     var pokemon : Pokemon = new Pokemon(Macho, 50, 50, 10, 4, 3, fixture.poliwrath)
     Assert.assertFalse(pokemon.tieneElTipo(Fantasma))
  }

}