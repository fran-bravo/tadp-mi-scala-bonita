package tadp.scala.bonita

package object fixture {
	//Especies Tipo Electrico
  val raichu = new Especie(20, List(Electrico),500 , None, 1, 0, 0, 3)
  val pikachu = new Especie(10, List(Electrico), 350, Some(new Evolucion((new SubirNivel(2)), raichu)), 1, 0, 0, 2)
  //Especies Tipo Fantasma
  val gastly = new Especie(2, List(Fantasma), 300, None, 2, 0, 0, 1)
  //Especies Tipo Pelea
  val machamp = new Especie(30, List(Pelea), 700, None, 3, 1, 3, 1)
  val machoke = new Especie(30, List(Pelea),450 , Some(new Evolucion(new Intercambiar, machamp)), 2, 1, 2, 1)
  val machop = new Especie(30, List(Pelea), 250, Some(new Evolucion(new SubirNivel(10), machoke)), 1, 0, 1, 0)
  //Especies Tipo Agua
  val poliwrath = new Especie(20, List(Agua, Pelea), 650, None, 2, 2, 2, 1)
  val poliwhirl = new Especie(18, List(Agua), 500, Some(new Evolucion(new UsarPiedra, poliwrath)), 1, 1, 1, 1)
  val poliwag = new Especie(12, List(Agua), 350, Some(new Evolucion(new SubirNivel(25), poliwhirl)), 1, 1, 1, 2)
  //Especies Tipo Normal
  val clefable = new Especie(18, List(Normal), 500, None, 2, 1, 1, 1)
  val clefairy = new Especie(18, List(Normal), 500, Some(new Evolucion(new UsarPiedraLunar, clefable)), 1, 0, 0, 1) 
  //Especies Tipo Fuego
  val charizard = new Especie(90, List(Fuego, Volador), 700, None, 3, 4, 3, 2)
  val charmeleon = new Especie(19, List(Fuego), 500, Some(new Evolucion(new SubirNivel(36), charizard)), 3, 2, 3, 2)
  val charmander = new Especie(9, List(Fuego), 350, Some(new Evolucion(new SubirNivel(16), charmeleon)), 2, 1, 2, 3)
  //Especies Tipo Roca-Tierra
  val golem = new Especie(300, List(Roca, Tierra), 700, None, 3, 3, 3, 1)
  val graveler = new Especie(105, List(Roca, Tierra), 500, Some(new Evolucion(new Intercambiar(), golem)), 2, 3, 2, 1)
  val geodude = new Especie(20, List(Roca, Tierra), 350, Some(new Evolucion(new SubirNivel(25), graveler)), 1, 2, 2, 2)
  //Especies Tipo Dragon
  val dragonite = new Especie(210, List(Dragon, Volador), 700, None, 3, 4, 3, 3)
  val dragonair = new Especie(16, List(Dragon), 500, Some(new Evolucion(new SubirNivel(55), dragonite)), 2, 1, 2, 3)
  val dratini = new Especie(4, List(Dragon), 350, Some(new Evolucion(new SubirNivel(30), dragonair)), 1, 1, 1, 2)
  val kingdra = new Especie(150, List(Agua, Dragon), 700, None, 3, 3, 3, 3)
  //Especies Tipo Psiquico
  val alakazam = new Especie(48, List(Psiquico), 700, None, 3, 2, 2, 2)
  val kadabra = new Especie(56, List(Psiquico), 500, Some(new Evolucion(new Intercambiar(), alakazam)), 2, 2, 2, 3)
  val abra = new Especie(20, List(Psiquico), 350, Some(new Evolucion(new SubirNivel(16), kadabra)), 2, 1, 1, 2)
  //Especie tipo Volador-Roca
  val aerodactyl = new Especie(90, List(Volador, Roca), 1000, None, 3, 1, 2, 1)
  
  //Ataques
  def thunderbolt = new Ataque("thunderbolt", Electrico, 15)
  def thunder = new Ataque("thunder", Electrico, 10)
  def storm = new Ataque("storm", Electrico, 20)
  def pound = new Ataque("pound", Normal, 35)
  def surf = new Ataque("surf", Agua, 15)
  def flamethrower = new Ataque("flamethrower", Fuego, 15)
  def giga_drain = new Ataque("giga drain", Planta, 10)
  def psychic = new Ataque("psychic", Psiquico, 10)
  def fly = new Ataque("fly", Volador, 15)
  def dragon_rage = new Ataque("dragon rage", Dragon, 10)
  def hiper_rayo = new Ataque("hiper beam", Normal, 5)
  
  //Ataques con efecto
  def enfocarse = new Ataque("focus", Normal, 30, {p => p.ganarVelocidad(1)})
  def rest = new Ataque("rest", Psiquico, 10, {p => p.curarTodaLaEnergia().pasarADormido()})
  def endurecerse = new Ataque("endurance", Normal, 40, {p => p.curarEnergia(5).pasarAParalizado()})
  
    
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
    new Pokemon(Hembra, 100, 100, 50, 40, 7, charizard)
  }
  def nuevoCharizardM(): Pokemon = {
    new Pokemon(Macho, 100, 100, 50, 40, 7, charizard)
  }
  
  def nuevoDratiniM(): Pokemon = {
    new Pokemon(Macho, 100, 100, 30, 15, 20, dratini)
  }
  
  def nuevoAerodactylF(): Pokemon = {
    new Pokemon(Hembra, 100, 100, 40, 50, 8, aerodactyl)
  }
  
  def nuevoAbraM(): Pokemon = {
    new Pokemon(Macho, 20, 20, 10, 12, 13, abra)
  }
  
  def nuevoClefairyF(): Pokemon = {
    new Pokemon(Hembra, 80, 80, 23, 20, 15, clefairy)
  }
  
  def nuevoKingdraM(): Pokemon = {
    new Pokemon(Macho, 100, 100, 40, 40, 10, kingdra)
  }
  
  //pokemones que saben cosas:
  
  def nuevoPikachuConThunderbolt(): Pokemon = {
    nuevoPikachuM.incorporar(thunderbolt)
  }
  
  def nuevoPikachuMultiplesAtaques(): Pokemon ={
    var pikachu = nuevoPikachuM.incorporar(thunderbolt)
    pikachu = pikachu.incorporar(thunder)
    pikachu = pikachu.incorporar(storm)
    return pikachu
  }
  
  def nuevoCharizardFConFly(): Pokemon = {
    nuevoCharizardF.incorporar(fly)
  }
  def nuevoCharizardMConFly(): Pokemon = {
    nuevoCharizardM.incorporar(fly)
  }
  
  def nuevoDratiniMConDragonRage(): Pokemon = {
    nuevoDratiniM.incorporar(dragon_rage)
  }
  
  def nuevoAbraConRest(): Pokemon = {
    nuevoAbraM.incorporar(rest)
  }
  
  def nuevoAbraConPsychicYRest(): Pokemon = {
    var abra = nuevoAbraM.incorporar(rest)
    abra = abra.incorporar(psychic)
    return abra
  }  
  
  def nuevoClefairyConEndurance(): Pokemon = {
    nuevoClefairyF.incorporar(endurecerse)
  }
  
  def nuevoClefairyConFocus(): Pokemon = {
    nuevoClefairyF.incorporar(enfocarse)
  }
  
  def nuevoClefairyConHyperBeam(): Pokemon = {
    nuevoClefairyF.incorporar(hiper_rayo)
  }
  
  def nuevoKingdraConDragonRage(): Pokemon = {
    nuevoKingdraM().incorporar(dragon_rage)
  }
  
  def nuevoDragoniteM(): Pokemon = {
    new Pokemon(Macho, 200, 200, 90, 70, 60, dragonite)
  }
  
  
  def pokemonUsa5hiperrayos(pokemon:Pokemon) = {
    var poke: Pokemon = pokemon
    poke = poke.realizarActividad(RealizarAtaque(hiper_rayo))
    poke = poke.realizarActividad(RealizarAtaque(hiper_rayo))
    poke = poke.realizarActividad(RealizarAtaque(hiper_rayo))
    poke = poke.realizarActividad(RealizarAtaque(hiper_rayo))
    poke = poke.realizarActividad(RealizarAtaque(hiper_rayo))
    poke  
  }

  //Alguna rutinas
   val rutinaNivel = new Rutina("Rutina Ataques", 
                      List(RealizarAtaque(fixture.dragon_rage), RealizarAtaque(fixture.dragon_rage),
                           RealizarAtaque(fixture.dragon_rage), RealizarAtaque(fixture.dragon_rage),
                           RealizarAtaque(fixture.dragon_rage), FingirIntercambio, Nadar(1)))
   val rutinaEnergia = new Rutina("Rutina Comida", 
                      List(ComerHierro, ComerZinc, ComerCalcio, FingirIntercambio))
   val rutinaMenosPeso = new Rutina("Rutina Descanso", 
                      List(Descansar, Nadar(1)))
   //Coleccion de rutinas
   val rutinas : List[Rutina] = List(rutinaNivel, rutinaEnergia, rutinaMenosPeso)
   val auxRutinas : List[Rutina] = List(rutinaEnergia, rutinaNivel, rutinaMenosPeso)
  
  //Criterios de analisis de rutina
  //val criterioMasNivel = {poke:Pokemon => poke.nivel }
  def criterioMasNivel(poke:Pokemon): Int = { poke.nivel}
  def criterioMasEnergia(poke:Pokemon): Int = { poke.energia}
  def criterioMenorPeso(poke:Pokemon): Int = { -poke.peso }
  
  
}