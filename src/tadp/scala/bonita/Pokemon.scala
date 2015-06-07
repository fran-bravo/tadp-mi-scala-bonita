package tadp.scala.bonita

case class Pokemon(
  val genero: Genero, //Macho o Hembra
  val energia: Int, //Minimo 0, maximo energiaMaxima
  val energiaMaximaBase: Int,
  val pesoBase: Int, //Minimo 0
  val fuerzaBase: Int, //De 1 a 100
  val velocidadBase: Int, //De 1 a 100
  val especie: Especie,
  val nivel: Int = 1, //De 1 a 100
  val experiencia: Int = 0,
  val estado: Estado = Saludable)
  {
  // 
  
  def peso : Int = pesoBase + especie.incPeso * nivel
  
  def energiaMaxima : Int = energiaMaximaBase + especie.incEnergiaMaxima * nivel
  
  def velocidad : Int = velocidadBase + especie.incVelocidad * nivel
  
  def fuerza : Int = fuerzaBase + especie.incFuerza * nivel
  
  //pero mira como esta ese codigo repetido papa
  
  def puedoRealizarActividad() = {
    if (this.estado.knockeado) {
      throw new KOException("Estoy Knockeado")
    }
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
  
  def pasarAKO() = {
    copy(estado = KO)
  }
  
  def pasarAEnvenenado() = {
    copy(estado = Envenenado)
  }
  
  // Metodos auxiliares de levantarPesas
  
  def tengoFuerzaSuficiente(unosKilos:Int) = {
    if (unosKilos > this.fuerzaBase * 10){
      copy(estado = Paralizado)
      throw new StrengthException("No tengo fuerza suficiente")
    }
  }
  
  def puedoLevantarPesas(unosKilos:Int): Int = {
  	this.tengoFuerzaSuficiente(unosKilos)
    return this.especie.puedeLevantar()
  
  }
  
  // Actividad levantarPesas
  
  def levantarPesas(unosKilos:Int) = {
    this.puedoRealizarActividad
    if(this.estoyParalizado){
      this.pasarAKO() 
    } else {
      copy(experiencia = this.puedoLevantarPesas(unosKilos) * unosKilos)
    }
  }
  
  
  //Evolucionar
  
  def evolucionar() = {
    copy(especie = this.especie.especieDeEvolucion.get)
  }
  
  def fingirIntercambio() = {
    this.especie.condicionDeEvolucion.map{_.fingeIntercambio(this)}.get
  }
  
  def usarPiedra(unaPiedra: PiedraAbstract) = {
    this.especie.condicionDeEvolucion.map{_.usaPiedra(this, unaPiedra)}.get
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
  
  def subirUnNivel(): Pokemon = {
    val pokemon: Pokemon = copy(nivel = nivel + 1)
    pokemon.especie.condicionDeEvolucion.map{_.subioDeNivel(pokemon)}.get
  }
  
  def pierdeCon(tipo: Tipo): Boolean = {
    this.especie.tipos.forall{tipoPok => tipo.leGanaA(tipoPok)}
  }
  
}
