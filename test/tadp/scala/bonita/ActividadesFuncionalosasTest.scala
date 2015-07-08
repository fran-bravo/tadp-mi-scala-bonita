package tadp.scala.bonita

import org.junit.Test
import org.junit.Assert
import scala.util.Try
import tadp.scala.bonita.Actividades._

class ActividadesFuncionalosasTest {

  @Test
  def `un pokemon dormido ignora la actividad`
  { 
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarADormido()
    var pika : Try[Pokemon] = realizar(pikachu, (levantarPesas (4) _), false)
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, pika.get.experiencia)
  }
  
  @Test
  def `Pokemon electrico levanta pesas` = {
    var pokemon : Pokemon = fixture.nuevoPikachuM() 
    
    var poke : Try[Pokemon] = realizar(pokemon, (levantarPesas(15) _), false)
    
    val exp: BigInt = 15
    Assert.assertEquals(exp, poke.get.experiencia)    
  }
  
  @Test
  def `realizar ataque de tipo principal da 50 exp`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(pikachu, fixture.thunderbolt.efecto(pikachu))
    
    var exp: BigInt = 0
    Assert.assertEquals(exp, pikachu.experiencia)
    var pika = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false) //ac� est� infiriendo la aplicaci�n parcial
    exp = 50
    Assert.assertEquals(exp, pika.get.experiencia)
  }
  
  @Test
  def `nadar aumenta en 1 punto la velocidad base por hora de nado para los tipo agua`
  {
    var poliwhirl = fixture.nuevoPoliwhirlM()
    Assert.assertEquals(6, poliwhirl.velocidadBase)
    var poli : Try[Pokemon] = realizar(poliwhirl, nadar(60), false)
    Assert.assertEquals(7, poli.get.velocidadBase)
    //acá tengo que preguntar sí o sí por la velocidad base
    //porque este nado le da tanta experiencia que levea, y me jode con la velocidad final
  }
 
  /* Usar Items Sencillos*/
  
   @Test
   def `ether cura suenio`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarADormido()
     Assert.assertEquals(Dormido(3), pika.estado)  
     //whoops, esto es una limitación de JUnit, quisiera poder hacer Dormido(_)
     pika = realizar(pika, usarEther, true).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
     
   @Test
   def `pokemon que usa pocion se cura 50 de energia`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.perderEnergia(60) //quedaria en 40
     pika = realizar(pika, usarPocion, false).get //ahora deberia ser 90
     Assert.assertEquals(90, pika.energia)
   }
   
   @Test
   def `pokemon que usa pocion se cura pero no se exceede de su energia maxima`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.perderEnergia(20) //quedaria en 80
     pika = realizar(pika, usarPocion, false).get //no deberia pasar de 100
     Assert.assertEquals(100, pika.energia)
   }
   
   @Test
   def `pokemon envenenado se cura con un antidoto`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAEnvenenado()
     Assert.assertEquals(Envenenado, pika.estado)   
     pika = realizar(pika, usarAntidoto, false).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test
   def `ether cura veneno`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAEnvenenado()
     Assert.assertEquals(Envenenado, pika.estado)   
     pika = realizar(pika, usarEther, true).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
     
   @Test
   def `ether cura paralisis`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAParalizado()
     Assert.assertEquals(Paralizado, pika.estado)   
     pika = realizar(pika, usarEther, true).get
     Assert.assertEquals(Saludable, pika.estado)     
   }
   
   @Test(expected = classOf[KOException])
   def `ether no cura KO`
   {
     var pika = fixture.nuevoPikachuM()
     pika = pika.pasarAKO()
     Assert.assertEquals(KO, pika.estado)   
     realizar(pika, usarEther, true).get
//     Assert.assertEquals(KO, pika.estado) 
     //todo debería romper o quedar KO sin romper? es un detalle igual
   }
   
   
   @Test
   def `hierro aumenta en 5 la fuerza de un pokemon`
   {
     var pika = fixture.nuevoPikachuM()
     Assert.assertEquals(2, pika.fuerzaBase)
     pika = realizar(pika, comerHierro, false).get
     Assert.assertEquals(7, pika.fuerzaBase)
   }
   
   @Test
   def `calcio aumenta en 5 la velocidad de un pokemon`
   {
     var pika = fixture.nuevoPikachuM()
     Assert.assertEquals(6, pika.velocidadBase)
     pika = realizar(pika, comerCalcio, false).get
     Assert.assertEquals(11, pika.velocidadBase)
   }
   
   @Test
   def `zinc aumenta en 2 el pp max de cada ataque`
   {
     var pika = fixture.nuevoPikachuM()
     pika = realizar(pika, aprenderAtaque(fixture.thunderbolt), false).get
     pika = realizar(pika, aprenderAtaque(fixture.pound), false).get
     Assert.assertEquals(15, pika.paMax(fixture.thunderbolt))
     Assert.assertEquals(35, pika.paMax(fixture.pound))
     pika = realizar(pika, comerZinc, false).get
     Assert.assertEquals(17, pika.paMax(fixture.thunderbolt))
     Assert.assertEquals(37, pika.paMax(fixture.pound))
   }
   
  @Test
  def `un pokemon con un ataque come zinc`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    val ppThunderbolt = pikachu.paMax(fixture.thunderbolt)
    
    pikachu = realizar(pikachu, comerZinc, false).get
    
    Assert.assertEquals(ppThunderbolt+2, pikachu.paMax(fixture.thunderbolt))
    
  }
  
  @Test
  def `un pokemon con muchos ataques come zinc`
  {
    var pikachu = fixture.nuevoPikachuMultiplesAtaques()
    
    val ppThunderbolt = pikachu.paMax(fixture.thunderbolt)
    val ppThunder= pikachu.paMax(fixture.thunder)
    val ppStorm = pikachu.paMax(fixture.storm)
    
    pikachu = realizar(pikachu, comerZinc, false).get
    
    Assert.assertEquals(ppThunderbolt+2, pikachu.paMax(fixture.thunderbolt))
    Assert.assertEquals(ppThunder+2, pikachu.paMax(fixture.thunder))
    Assert.assertEquals(ppStorm+2, pikachu.paMax(fixture.storm))
    
  } 
  
  /* Actividades */
  
  @Test
  def `un pokemon dormido despierta despues de ignorar tres actividades`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarADormido()
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    Assert.assertTrue(pikachu.estoyDormido())
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    Assert.assertFalse(pikachu.estoyDormido())
  }
  
  @Test(expected = classOf[KOException])
  def `un pokemon KO rompe cuando se le pasa una actividad`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.pasarAKO()
    realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get

  }
  
  @Test(expected = classOf[CaracteristicasInvalidasException])
  def `un pokemon que queda con caracteristicas invalidas rompe`
  {
    var machamp = new Pokemon(Macho, new Caracteristicas(100, 100, 45, 97, 12), fixture.machamp)
    realizar(machamp, comerHierro, false).get
  }
  
  
  @Test
  def `un pokemon recupera todos los pp al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, descansar, false).get
    
    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `un pokemon con menos de la mitad de vida sano se duerme al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.perderEnergia(60) //Le queda 40 de vida de un total de 100
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, descansar, false).get

    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(40, pikachu.energia)
    Assert.assertEquals(Dormido(), pikachu.estado)
  }
  
  @Test
  def `un pokemon con menos de la mitad de vida no sano no se duerme al descansar`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    pikachu = pikachu.perderEnergia(60) //Le queda 40 de vida de un total de 100
    pikachu = pikachu.pasarAEnvenenado()
    val estadoInicial = pikachu.estado
    val ppInicial = pikachu.paActual(fixture.thunderbolt)
    
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, descansar, false).get

    Assert.assertEquals(ppInicial, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(40, pikachu.energia)
    Assert.assertEquals(estadoInicial, pikachu.estado)
  }
  
  @Test
  def  `un pokemon dormido se cura con el ether`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = pikachu.pasarADormido()
    
    pikachu = realizar(pikachu, usarEther, true).get
    
    Assert.assertEquals(pikachu.estado, Saludable)
    
  }
  
  /* Aprender Ataques*/
  
  @Test
  def `pokemon electrico aprende thunderbolt y lo incorpora a sus ataques`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.thunderbolt))
    var pika : Try[Pokemon] = realizar(pikachu, aprenderAtaque(fixture.thunderbolt), false)
    Assert.assertTrue(pika.get.sabeElAtaque(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprende thunderbolt y obtiene 15 pp`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = realizar(pikachu, aprenderAtaque(fixture.thunderbolt), false).get
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprender thunderbolt y obtiene 15 pp max`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = realizar(pikachu, aprenderAtaque(fixture.thunderbolt), false).get
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
  }
  
  @Test
  def `pokemon electrico aprende thunderbolt y lo usa, decrementa su pp actual pero no el max`
  {
    var pikachu = fixture.nuevoPikachuM()
    pikachu = realizar(pikachu, aprenderAtaque(fixture.thunderbolt), false).get
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    pikachu = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
    Assert.assertEquals(12, pikachu.paActual(fixture.thunderbolt))
    Assert.assertEquals(15, pikachu.paMax(fixture.thunderbolt))
    
  }
  
  @Test
  def `pokemon electrico tambien puede aprender pound, que es un ataque tipo normal`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.pound))
    pikachu = realizar(pikachu, aprenderAtaque(fixture.pound), false).get
    Assert.assertTrue(pikachu.sabeElAtaque(fixture.pound))
  }
  
  @Test
  def `otro pokemon puede aprender pound`
  {
    var dratini = fixture.nuevoDratiniM()
    Assert.assertFalse(dratini.sabeElAtaque(fixture.pound))
    dratini = realizar(dratini, aprenderAtaque(fixture.pound), false).get
    Assert.assertTrue(dratini.sabeElAtaque(fixture.pound))
  }
  
  @Test
  def `un pokemon intenta aprender un ataque no afin, no lo aprende`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
    pikachu = realizar(pikachu, aprenderAtaque(fixture.dragon_rage), false).get
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
  }
  
  @Test
  def `un pokemon intenta aprender un ataque no afin, queda KO`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.dragon_rage))
    pikachu = realizar(pikachu, aprenderAtaque(fixture.dragon_rage), false).get
    Assert.assertEquals(KO, pikachu.estado)
  }
  
  /* Levantar Pesas*/
  
  @Test(expected = classOf[TypeException])
  def `Pokemon fantasma trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = new Pokemon(Hembra, new Caracteristicas(50, 50, 1, 3, 3), fixture.gastly)
    realizar(pokemon, levantarPesas(15), false).get
  }
  
  @Test
  def `Pokemon pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Hembra, new Caracteristicas(50, 50, 10, 8, 3), fixture.machop)
    
    pokemon = realizar(pokemon, levantarPesas(10), false).get
    
    val exp: BigInt = 20
    Assert.assertEquals(exp, pokemon.experiencia)
  }
  
  @Test
  def `Pokemon subtipo pelea levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Macho, new Caracteristicas(50, 50, 10, 4, 3), fixture.poliwrath)
    
    pokemon = realizar(pokemon, levantarPesas(10), false).get
    
    val exp: BigInt = 20
    Assert.assertEquals(exp, pokemon.experiencia)
  }
  
  @Test
  def `Pokemon sin suficiente fuerza levanta pesas` = {
    var pokemon : Pokemon = new Pokemon(Macho, new Caracteristicas(100, 100, 10, 2, 6), fixture.pikachu) 
    pokemon = realizar(pokemon, levantarPesas(30), false).get
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, pokemon.experiencia) 
    Assert.assertEquals(Paralizado, pokemon.estado)
  }
  
  @Test
  def `Pokemon paralizado levanta pesas y pasa a KO`: Unit = {
    var pokemon : Pokemon = fixture.nuevoPikachuM()
    pokemon = pokemon.copy(estado = Paralizado)
    
    pokemon = realizar(pokemon, levantarPesas(5), false).get
    
    val exp: BigInt = 0
    Assert.assertEquals(KO, pokemon.estado)
    Assert.assertEquals(exp, pokemon.experiencia)
  }
  
  @Test(expected = classOf[KOException])
  def `Pokemon knockeado trata de levantar pesas`: Unit = {
    var pokemon : Pokemon = fixture.nuevoPikachuM()
    pokemon = pokemon.copy(estado = KO)
    realizar(pokemon, levantarPesas(1), false).get
    
  }
  
  /* Nadar */
  
   @Test
  def `pokemon con tipo principal que pierde con agua queda KO y no gana experiencia si intenta nadar`
  {
    var charizard = fixture.nuevoCharizardM()
    charizard = realizar(charizard, nadar(1), false).get 
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, charizard.experiencia)
    Assert.assertEquals(KO, charizard.estado)
  }
  
  @Test
  def `pokemon con tipo secundario que pierde con agua queda KO y no gana experiencia si intenta nadar`
  {
    var aerodactyl = fixture.nuevoAerodactylF()
    aerodactyl = realizar(aerodactyl, nadar(1), false).get 
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, aerodactyl.experiencia)
    Assert.assertEquals(KO, aerodactyl.estado)
  }
  
  @Test 
  def `nadar quita tanta energia como minutos se nade`
  {
    var dratini = fixture.nuevoDratiniM()
    Assert.assertEquals(100, dratini.energia)
    dratini = realizar(dratini, nadar(8), false).get
    Assert.assertEquals(92, dratini.energia)
  }
  
  @Test 
  def `nadar da 200 de exp por cada minuto que se nade`
  {
    var dratini = fixture.nuevoDratiniM()
    var exp: BigInt = 0
    Assert.assertEquals(exp, dratini.experiencia)
    dratini = realizar(dratini, nadar(8), false).get
    exp = 1600
    Assert.assertEquals(exp, dratini.experiencia)
  }
  
   @Test
   def `nadar menos de una hora no aumenta la velocidad base para los tipo agua`
   {
    var poliwhirl = fixture.nuevoPoliwhirlM()
    Assert.assertEquals(6, poliwhirl.velocidadBase)
    poliwhirl = realizar(poliwhirl, nadar(59), false).get
    Assert.assertEquals(6, poliwhirl.velocidadBase)
   }
   
   def `nadar dos horas aumenta en dos la velocidad base a los tipo agua`
   {
     var poliwhirl = fixture.nuevoPoliwhirlM()
     Assert.assertEquals(6, poliwhirl.velocidadBase)
     poliwhirl = realizar(poliwhirl, nadar(130), false).get //nado dos horas y un puchito
     Assert.assertEquals(8, poliwhirl.velocidadBase)
   }
   
   /* Realizar Ataque*/
   
    @Test
  def `realizar un ataque gasta PA`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    var pika = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false)
    Assert.assertEquals(14, pika.get.paActual(fixture.thunderbolt))
  }
  
  @Test
  def `funciona el recuperar PA`
  { 
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt))
    var pika = realizar(pikachu, realizarAtaque(fixture.thunderbolt), false)
    Assert.assertEquals(14, pika.get.paActual(fixture.thunderbolt))
    pikachu = pika.get.recuperarPA()
    Assert.assertEquals(15, pikachu.paActual(fixture.thunderbolt)) 
  }
  
  
  @Test
  def `realizar ataque de tipo secundario da 20 exp a un macho`
  {
    var charizard = fixture.nuevoCharizardMConFly()
    
    var exp: BigInt = 0
    Assert.assertEquals(exp, charizard.experiencia)
    var chari = realizar(charizard, realizarAtaque(fixture.fly), false)
    exp = 20
    Assert.assertEquals(exp, chari.get.experiencia)
  }

  @Test
  def `realizar ataque de tipo secundario da 40 exp a una hembra`
  {
    var charizard = fixture.nuevoCharizardFConFly()
    var exp: BigInt = 0
    Assert.assertEquals(exp, charizard.experiencia)
    var chari = realizar(charizard, realizarAtaque(fixture.fly), false)
    exp = 40
    Assert.assertEquals(exp, chari.get.experiencia)
  }
  
  @Test
  def `realizar ataque dragon da 80 exp`
  {
    var dratini = fixture.nuevoDratiniMConDragonRage()
    var exp: BigInt = 0
    Assert.assertEquals(exp, dratini.experiencia)
    var drati = realizar(dratini, realizarAtaque(fixture.dragon_rage), false)
    exp = 80
    Assert.assertEquals(exp, drati.get.experiencia)
  }
  
  
  @Test(expected = classOf[UnknownAttackException])
  def `un pokemon no conoce el ataque y quiere realizarlo igual`
  {
    var pikachu = fixture.nuevoPikachuM()
    realizar(pikachu, realizarAtaque(fixture.thunderbolt), false).get
  }
  
  @Test(expected = classOf[UnknownAttackException])
  def `un pokemon no conoce el ataque, pero conoce otro y quiere realizarlo igual`
  {
    var pikachu = fixture.nuevoPikachuConThunderbolt()
    realizar(pikachu, realizarAtaque(fixture.storm), false).get

  }
  
  @Test
  def `un pokemon no conoce ataque`
  {
    var pikachu = fixture.nuevoPikachuM()
    Assert.assertFalse(pikachu.sabeElAtaque(fixture.thunderbolt))
  }
  
  @Test
  def `un pokemon realiza ataque con reposar`
  {
    var abra = fixture.nuevoAbraConRest()
    abra = abra.perderEnergia(10) //Hago que pierda energía para comprobar que se recupera toda
    var aby = realizar(abra, realizarAtaque(fixture.rest), false)
    
    var exp: BigInt = 50
    Assert.assertEquals(exp, aby.get.experiencia)
    Assert.assertEquals(Dormido(), aby.get.estado)
    exp = 20
    Assert.assertEquals(exp, aby.get.energia)
  }
  
  @Test
  def `un pokemon realiza ataque con endurecer`
  {
    var clefairy = fixture.nuevoClefairyConEndurance()
    clefairy = clefairy.perderEnergia(20)
    var clefa = realizar(clefairy, realizarAtaque(fixture.endurecerse), false)
    
    var exp: BigInt = 50
    Assert.assertEquals(exp, clefa.get.experiencia)
    Assert.assertEquals(Paralizado, clefa.get.estado)
    exp = 65
    Assert.assertEquals(exp, clefa.get.energia)
    
  }
  
  @Test
  def `un pokemon realiza ataque con enfocar`
  {
    var clefairy = fixture.nuevoClefairyConFocus()
    val velocidadPrevia = clefairy.velocidad
    var clefa = realizar(clefairy, realizarAtaque(fixture.enfocarse), false)
    
    val exp: BigInt = 50
    Assert.assertEquals(exp, clefa.get.experiencia)
    Assert.assertEquals(velocidadPrevia+1, clefa.get.velocidad)
    
  }
    
  @Test
  def `un pokemon Agua-Dragon realiza ataque dragon`
  {
    var kingdra = fixture.nuevoKingdraConDragonRage()
    var king = realizar(kingdra, realizarAtaque(fixture.dragon_rage), false)
    
    val exp: BigInt = 80
    Assert.assertEquals(exp, king.get.experiencia)
  }
  
  @Test(expected = classOf[NoRemainingPPException])
  def `un pokemon quiere realiza ataque sin pps`
  {
    var clefairy = fixture.nuevoClefairyConHyperBeam()
    //Hiper rayo tiene 5 PPs
    clefairy = fixture.pokemonUsa5hiperrayos(clefairy)
    realizar(clefairy, realizarAtaque(fixture.hiper_rayo), false).get

  }
  
  @Test
  def `Un pokemon con tipo secundario no gana experiencia si el ataque no es de su tipo`{
    var dragonite = fixture.nuevoDragoniteMConHiperRayo()
    var drago = realizar(dragonite, realizarAtaque(fixture.hiper_rayo), false)
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, drago.get.experiencia)
  }
  
  @Test
  def `Un pokemon sin tipo secundario no gana experiencia si el ataque no es de su tipo`{
    var dratini = fixture.nuevoDratiniMConHiperRayo()
    var drati = realizar(dratini, realizarAtaque(fixture.hiper_rayo), false)
    
    val exp: BigInt = 0
    Assert.assertEquals(exp, drati.get.experiencia)
  }
  
  /* Usar Piedra */
  
  @Test
  def `Poliwhirl evoluciona cuando se le da una piedra Agua` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = realizar(pokemon, darPiedra(new Piedra(Agua)), false).get
    
    Assert.assertEquals(fixture.poliwrath, pokemon.especie)
  }
  
  @Test
  def `Poliwhirl queda envenenado si se le da una piedra trueno` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = realizar(pokemon, darPiedra(new Piedra(Electrico)), false).get
    
    assert(pokemon.estoyEnvenenado())
  }
  
  @Test
  def `Poliwhirl no evoluciona si se le pasa una piedra lunar` = {
    var pokemon : Pokemon = fixture.nuevoPoliwhirlM()
    
    pokemon = realizar(pokemon, darPiedra(PiedraLunar), false).get
    
    Assert.assertEquals(fixture.poliwhirl, pokemon.especie)
  }
  
  @Test
  def `Clefairy evoluciona si se le da una piedra lunar` = {
    var pokemon : Pokemon = fixture.nuevoClefairyM()
    
    pokemon = realizar(pokemon, darPiedra(PiedraLunar), false).get
    
    Assert.assertEquals(fixture.clefable, pokemon.especie)
  }
  
  @Test
  def `Clefairy no evoluciona si se le pasa otra piedra` = {
    var pokemon : Pokemon = fixture.nuevoClefairyM()
    
    pokemon = realizar(pokemon, darPiedra(new Piedra(Agua)), false).get
    
    Assert.assertEquals(fixture.clefairy, pokemon.especie)
  }
  
  @Test
  def `Dragonite queda envenenado si se le da una piedra Dragon (ponele que existe)` = {
    var dragonite: Pokemon = fixture.nuevoDragoniteM()
    dragonite = realizar(dragonite, darPiedra(new Piedra(Dragon)), false).get
    
    assert(dragonite.estoyEnvenenado())
  }

  @Test
  def `A charizard no le pasa nada si se le da una piedra Fuego` = {
    val charizard: Pokemon = fixture.nuevoCharizardF()
    
    Assert.assertEquals(charizard, realizar(charizard, darPiedra(new Piedra(Fuego)), false).get)
  }
  
  @Test
  def `A aerodactyl no le pasa nada si se le da una piedra Lunar` = {
    val aerodactyl: Pokemon = fixture.nuevoAerodactylF()
    
    Assert.assertEquals(aerodactyl, realizar(aerodactyl, darPiedra(PiedraLunar), false).get)
  }
}