package tadp.scala.bonita

package object fixture {
	//Especies Tipo Electrico
  val raichu = new Especie(20, List(Electrico),500 , None, None, 1, 0, 0, 3)
  val pikachu = new Especie(10, List(Electrico), 350, Some(new SubirNivel(2)), Some(raichu), 1, 0, 0, 2)
  //Especies Tipo Fantasma
  val gastly = new Especie(2, List(Fantasma), 300, None, None, 2, 0, 0, 1)
  //Especies Tipo Pelea
  val machamp = new Especie(30, List(Pelea), 700, None, None, 3, 1, 3, 1)
  val machoke = new Especie(30, List(Pelea),450 , Some(new Intercambiar), Some(machamp), 2, 1, 2, 1)
  val machop = new Especie(30, List(Pelea), 250, Some(new SubirNivel(10)), Some(machoke), 1, 0, 1, 0)
  //Especies Tipo Agua
  val poliwrath = new Especie(20, List(Agua, Pelea), 650, None, None, 2, 2, 2, 1)
  val poliwhirl = new Especie(18, List(Agua), 500, Some(new UsarPiedra), Some(poliwrath), 1, 1, 1, 1)
  val poliwag = new Especie(12, List(Agua), 350, Some(new SubirNivel(25)), Some(poliwhirl), 1, 1, 1, 2)
  //Especies Tipo Normal
  val clefable = new Especie(18, List(Normal), 500, None, None, 2, 1, 1, 1)
  val clefairy = new Especie(18, List(Normal), 500, Some(new UsarPiedraLunar), Some(clefable), 1, 0, 0, 1) 
  //Especies Tipo Fuego
  val charizard = new Especie(90, List(Fuego, Volador), 700, None, None, 3, 4, 3, 2)
  val charmeleon = new Especie(19, List(Fuego), 500, Some(new SubirNivel(36)), Some(charizard), 3, 2, 3, 2)
  val charmander = new Especie(9, List(Fuego), 350, Some(new SubirNivel(16)), Some(charmeleon), 2, 1, 2, 3)
  //Especies Tipo Roca-Tierra
  val golem = new Especie(300, List(Roca, Tierra), 700, None, None, 3, 3, 3, 1)
  val graveler = new Especie(105, List(Roca, Tierra), 500, Some(new Intercambiar()), Some(golem), 2, 3, 2, 1)
  val geodude = new Especie(20, List(Roca, Tierra), 350, Some(new SubirNivel(25)), Some(graveler), 1, 2, 2, 2)
  //Especies Tipo Dragon
  val dragonite = new Especie(210, List(Dragon, Volador), 700, None, None, 3, 4, 3, 3)
  val dragonair = new Especie(16, List(Dragon), 500, Some(new SubirNivel(55)), Some(dragonite), 2, 1, 2, 3)
  val dratini = new Especie(4, List(Dragon), 350, Some(new SubirNivel(30)), Some(dragonair), 1, 1, 1, 2)
  //Especies Tipo Psiquico
  val alakazam = new Especie(48, List(Psiquico), 700, None, None, 3, 2, 2, 2)
  val kadabra = new Especie(56, List(Psiquico), 500, Some(new Intercambiar()), Some(alakazam), 2, 2, 2, 3)
  val abra = new Especie(20, List(Psiquico), 350, Some(new SubirNivel(16)), Some(kadabra), 2, 1, 1, 2)
  
  
  //Ataques
  val thunderbolt = new Ataque(Electrico, 15)
  val pound = new Ataque(Normal, 35)
  val surf = new Ataque(Agua, 15)
  val flamethrower = new Ataque(Fuego, 15)
  val giga_drain = new Ataque(Planta, 10)
  val psychic = new Ataque(Psiquico, 10)
  val fly = new Ataque(Volador, 15)
  val dragon_rage = new Ataque(Dragon, 10)
  
  //Ataques con efecto
  val enfocarse = new Ataque(Normal, 30, {p => p.ganarVelocidad(1)})
  val rest = new Ataque(Psiquico, 10, {p => p.curarTodaLaEnergia().pasarADormido()})
  val endurecerse = new Ataque(Normal, 40, {p => p.curarEnergia(5).pasarAParalizado()})
  
    
  def nuevoPikachuM(): Pokemon = {
    new Pokemon(Macho, 100, 100, 10, 2, 6, pikachu) 
  }
  
  def nuevoPikachuParaEvolucion(): Pokemon = {
    new Pokemon(Macho, 100, 100, 10, 5, 6, pikachu)
  }
  
  def nuevoPoliwhirlM(): Pokemon = {
    new Pokemon(Macho, 100, 100, 20, 5, 6, poliwhirl)
  }
  
  def nuevoClefairyM(): Pokemon = {
    new Pokemon(Macho, 100, 100, 20, 5, 6, fixture.clefairy)
  }
  
  def nuevoCharizardF(): Pokemon = {
    new Pokemon(Hembra, 100, 100, 120, 80, 7, charizard)
  }
  //pokemones que saben cosas:
  
  def nuevoPikachuConThunderbolt(): Pokemon = {
    nuevoPikachuM.incorporar(thunderbolt)
  }
  
  def nuevoCharizardConFly(): Pokemon = {
    nuevoCharizardF.incorporar(fly)
  }
  
  def nuevoCharizardConDragonRage(): Pokemon = {
    nuevoCharizardF.incorporar(dragon_rage)
  }
  
}