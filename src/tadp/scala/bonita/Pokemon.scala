package tadp.scala.bonita
import scala.math



case class Pokemon(
  val genero: Genero,  //Macho o Hemb, 
  val energia: Int,  //Minimo 0, maximo energiaMaxi, 
  val energiaMaximaBase: Int, 
  val pesoBase: Int,  //Minimo, 
  val fuerzaBase: Int,  //De 1 a 1, 
  val velocidadBase: Int,  //De 1 a 1, 
  val especie: Especie, 
  val nivel: Int = 1, //De 1 a 100
  val experiencia: Int = 0, 
  val estado: Estado = Saludable,
  val ataques: Map[String, (Int, Int)] = Map[String, (Int, Int)]()) //que representa el PP que tiene para cada ataque
  {
    
  def peso : Int = pesoBase + especie.incPeso * (nivel-1)
  def energiaMaxima : Int = energiaMaximaBase + especie.incEnergiaMaxima * (nivel-1)
  def velocidad : Int = velocidadBase + especie.incVelocidad * (nivel-1)
  def fuerza : Int = fuerzaBase + especie.incFuerza * (nivel-1)
  //pero mira como esta ese codigo repetido papa
  
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
  

  
  def realizarActividad(actividad:Actividad) : Pokemon= { //este vendría a ser el accept
       
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
  
  //Evolucionar
  
  def evolucionar() = {
    copy(especie = this.especie.especieDeEvolucion.get)
  }
  
  def fingirIntercambio() = {
    this.especie.condicionDeEvolucion.fold(this){_.fingeIntercambio(this)}
  }
  
  def usarPiedra(unaPiedra: PiedraAbstract) = {
    var poke = this.especie.condicionDeEvolucion.fold(this){_.usaPiedra(this, unaPiedra)}
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
    
    if (pokemon.experiencia >= pokemon.especie.experienciaParaNivel(pokemon.nivel+1)){
      pokemon = pokemon.subirUnNivel()
    }
    return pokemon
  }
  
  def ganarVelocidad (vel: Int): Pokemon = copy(velocidadBase = velocidadBase + vel)
  def ganarFuerza(fza : Int): Pokemon = copy(fuerzaBase = fuerzaBase + fza)
  
  def subirUnNivel(): Pokemon = {
    val pokemon: Pokemon = copy(nivel = nivel + 1)
    pokemon.especie.condicionDeEvolucion.fold(pokemon){_.subioDeNivel(pokemon)}
  }
  
  def pierdeCon(tipo: Tipo): Boolean = {
    this.especie.tipos.exists{tipoPok => tipo.leGanaA(tipoPok)}
  }
  
  def sabeElAtaque(ataque:Ataque): Boolean = {
    ataques.keys.exists { key => key == ataque.nombre }
  }

  def paActual(ataque: Ataque): Int = {
    ataques(ataque.nombre)._1 //verificar condición de error acá?
  }
  def paMax(ataque: Ataque): Int = {
    ataques(ataque.nombre)._2 //idem
  }
  
  def decrementarPA(ataque: Ataque): Pokemon = { //verificar que esté el ataque 
    var nombre : String = ataque.nombre
    var pokemon : Pokemon = copy(ataques = ataques.-(nombre).+((nombre, (ataques.get(nombre).get._1 - 1, ataques.get(nombre).get._2))))
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
    return copy(ataques = ataques.+((ataque.nombre, (ataque.puntosAtaqueBase, ataque.puntosAtaqueBase))))
  }
  
    
  def noConoceAtaque(ataque: Ataque): Boolean = {
    return !ataques.contains(ataque.nombre)
  }
  
  def tieneElTipo(tipo: Tipo) : Boolean = especie.tieneElTipo(tipo)
  
  
  def realizarRutina(rutina: Rutina) = {
    rutina.realizarRutina(this)
  }
  
  def fingeIntercambio() : Pokemon = {
    this.especie.fingeIntercambio(this)
  }
}
