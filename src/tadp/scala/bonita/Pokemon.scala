package tadp.scala.bonita
import scala.math
import scala.util.Try

case object Poke{
  def unapply(poke: Pokemon)
  {
    Some((poke.genero, poke.especie.tipoPrincipal, poke.especie.tipoSecundario))
  }
}

case class Pokemon(
  val genero: Genero,  //Macho o Hemb, 
  val energia: Int,  //Minimo 0, maximo energiaMaxi, 
  val energiaMaximaBase: Int, 
  val pesoBase: Int,  //Minimo, 
  val fuerzaBase: Int,  //De 1 a 1, 
  val velocidadBase: Int,  //De 1 a 1, 
  val especie: Especie,
  val experiencia: BigInt = 0, 
  val estado: Estado = Saludable,
  val ataques: Map[Ataque, (Int, Int)] = Map[Ataque, (Int, Int)]()) //que representa el PP que tiene para cada ataque
  {
  

  
  def peso : Int = statEfectiva(pesoBase, especie.incPeso)
  def energiaMaxima : Int = statEfectiva(energiaMaximaBase, especie.incEnergiaMaxima)
  def velocidad : Int = statEfectiva(velocidadBase, especie.incVelocidad)
  def fuerza : Int = statEfectiva(fuerzaBase, especie.incFuerza)
  
  def nivel: Int = especie.nivelParaExperiencia(experiencia)
  
  def statEfectiva(valorBase : Int, incremento : Int) = valorBase + incremento * (nivel-1)
  
  def validarCaracteristicas() =
  {
    if ((peso > 100) || (peso <1)
        || (fuerza > 100) || (fuerza <1)
        || (velocidad > 100) || (velocidad < 1)
        || (nivel > 100) || (nivel < 1))
      throw new CaracteristicasInvalidasException("Se llegó a un pokemon con características inválidas")
    //else
    this
  }
  

  
  def realizarActividad(actividad:Actividad) : Try[Pokemon] = { //este vendría a ser el accept
       
    actividad.realizar(this)
   
  }
  
  
  // Metodos asociados al estado
  
  def estoyParalizado(): Boolean = {
    return this.estado.paralisis 
  }
  
  def estoyKO(): Boolean = {
    return this.estado.knockeado
  }
  
  def estoyEnvenenado(): Boolean = {
    return this.estado.envenenado()
  }
  
  def estoyDormido() : Boolean = {
    return this.estado.dormido()
  }
  
  def pasarASaludable() = copy(estado = Saludable)
  def pasarAKO() = copy(estado = KO)
  def pasarAEnvenenado() = copy(estado = Envenenado)  
  def pasarAParalizado() = copy(estado = Paralizado)
  def pasarADormido() = copy(estado = Dormido()) //dormido defaultea a profundidad=3
  
  def irDespertando() = estado match {
    case Dormido(1) => copy(estado = Saludable)
    case Dormido(n) => copy(estado = Dormido(n-1))
    //match error acá significa que traté de ir despertando a alguien que no estaba dormido
    //no debería ocurrir porque sólo se llama después de entrar por un pattern match
    //que tiene que matchear el estado del pokemon con dormido
  }
  
  def perderEnergia(nrg : Int) = {
    if (energia <= nrg) copy(energia=0, estado=KO) //esto no lo dice pero... es muy lógico
    else copy(energia = energia - nrg)
  }
  
  def curarEnergia(nrg : Int) = {
    copy(energia = math.min(energiaMaxima, energia+nrg))
  }
  
  def curarTodaLaEnergia() = {
    copy(energia = energiaMaxima)
  }
  
  //Auxiliar
  
  def aplicarACondicion(func : (CondicionEvolucion => Pokemon)): Pokemon = {
    this.especie.evolucion.fold(this)(especie => func(especie.condicion))
  }
  
  //Evolucionar 
  def fingirIntercambio() = {
    this.aplicarACondicion({con => con.fingeIntercambio(this)})
  }
  
  def usarPiedra(unaPiedra: PiedraAbstract) = {
    var poke = this
   	poke = this.aplicarACondicion({con => con.usaPiedra(this, unaPiedra)}) 
  
    if (unaPiedra.perjudicasA(poke))
      poke = poke.pasarAEnvenenado()
    poke
  }
  
  // Modificar peso
  
  def modificarPeso(unPeso: Int) = {
    copy(pesoBase = pesoBase + unPeso)
  }
  
  def modificarPesoPorIntercambio() = this.genero match{
    case Macho => modificarPeso(1)
    case Hembra => modificarPeso(-10)
  }

  //Ganar Experiencia
  
  def ganarExperiencia (exp: Int): Pokemon = {
    var pokemon: Pokemon = copy(experiencia = experiencia + exp)
    var nivelAnterior = this.nivel
    var nivelActual = pokemon.nivel
    
    if (nivelAnterior < nivelActual ){
      pokemon = pokemon.subioUnNivel()
    } //esto esta solo para gatillar la evolucion
    return pokemon
  }
  
  def ganarVelocidad (vel: Int): Pokemon = copy(velocidadBase = velocidadBase + vel)
  def ganarFuerza(fza : Int): Pokemon = copy(fuerzaBase = fuerzaBase + fza)
  
  def subioUnNivel(): Pokemon = {
    	return this.aplicarACondicion({con => con.subioDeNivel(this)})
  }
  
  def pierdeCon(tipo: Tipo): Boolean = {
    this.especie.tipos.exists{tipoPok => tipo.leGanaA(tipoPok)}
  }
  
//  def obtenerPPSAtaque(ataque:Ataque): (Int, Int) = ataques.get(ataque) match {
//    case Some(tupla) => tupla
//    case None => throw new RuntimeException
//  }
//  ehh esto es como el get de option

  def obtenerPPSAtaque(ataque:Ataque): Option[(Int, Int)] = ataques.get(ataque)
  
  def sabeElAtaque(ataque:Ataque): Boolean = obtenerPPSAtaque(ataque) match {
    case Some(_) => true
    case None => false        
  }

  def paActual(ataque: Ataque): Int = obtenerPPSAtaque(ataque) match {
    case Some((actual,_)) => actual
    case None => throw new UnknownAttackException("el pokemon no conoce el ataque pedido")
  }
  
  def paMax(ataque: Ataque): Int = obtenerPPSAtaque(ataque) match {
    case Some((_, max)) => max
    case None => throw new UnknownAttackException("el pokemon no conoce el ataque pedido")
  }
  
  def decrementarPA(ataque: Ataque): Pokemon = { 
    var nombre : String = ataque.nombre
    var pokemon : Pokemon = copy(ataques = ataques.-(ataque).+((ataque, (ataques.get(ataque).get._1 - 1, ataques.get(ataque).get._2))))
    pokemon    
  }
  
  def recuperarPA(): Pokemon = {
    def restaurarPP : ((Int, Int)) => (Int, Int) = { case (actual, max) => (max, max)}
    copy(ataques = ataques.mapValues(restaurarPP))
  }
  
  def incrementarTodosLosPAMaxEn2(): Pokemon = {
    def incrementarPPMaxEn2: ((Int, Int)) => (Int, Int) = { case (actual, max) => (actual,  max+2)} 
    copy(ataques = ataques.mapValues(incrementarPPMaxEn2))
    //para parametrizar ese 2 creo que necesito saber aplicación parcial
    //no es que sea necesario parametrizarlo anyway, pero quedaría copado
  }
  
  def incorporar(ataque: Ataque): Pokemon =
  {
    return copy(ataques = ataques.+((ataque, (ataque.puntosAtaqueBase, ataque.puntosAtaqueBase))))
  }
  
    
  def noConoceAtaque(ataque: Ataque): Boolean = {
    return !ataques.contains(ataque)
  }
  
  def tieneElTipo(tipo: Tipo) : Boolean = especie.tieneElTipo(tipo)
  
  
  def realizarRutina(rutina: Rutina) = {
    rutina.realizarRutina(this)
  }
  
  def fingeIntercambio() : Pokemon = {
    return this.aplicarACondicion({con => con.fingeIntercambio(this)})
  }
}
