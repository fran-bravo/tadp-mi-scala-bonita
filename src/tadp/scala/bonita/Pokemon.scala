package tadp.scala.bonita

class Pokemon(var unGenero:Char, var unaEnergia: Int, var unaEnergiaMaxima: Int, var unPeso: Int, var unaFuerza: Int, var unaAgilidad: Int, val unaEspecie:Especie) {
  var nivel: Int = 1 //De 1 a 100
  var experiencia: Int = 0
  var genero: Char = unGenero//Macho o Hembra
  var energia: Int = unaEnergia//Minimo 0, maximo energiaMaxima
  var energiaMaxima: Int = unaEnergiaMaxima
  var peso: Int = unPeso//Minimo 0
  var fuerza: Int = unaFuerza//De 1 a 100
  var agilidad: Int = unaAgilidad//De 1 a 100
  var estado: Estado = Saludable
  var especie: Especie = unaEspecie 
  
  // 
  
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
    this.estado = KO
  }
  
  def pasarAEnvenenado() = {
    this.estado = Envenenado
  }
  
  // Metodos auxiliares de levantarPesas
  
  def tengoFuerzaSuficiente(unosKilos:Int) = {
    if (unosKilos > this.fuerza * 10){
      this.estado = Paralizado
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
      this.experiencia = this.puedoLevantarPesas(unosKilos) * unosKilos
    }
  }
  
  
  //Evolucionar
  
  def evolucionar() = {
    this.especie = this.especie.especieDeEvolucion.get
  }
  
  def fingirIntercambio() = {
    this.especie.condicionDeEvolucion.map{_.fingeIntercambio(this)}
  }
  
  def usarPiedra(unaPiedra: Piedra) = {
    this.especie.condicionDeEvolucion.map{_.usaPiedra(this, unaPiedra)}
  }
  
  // Modificar peso
  
  def modificarPeso(unPeso: Int) = {
    this.peso += unPeso
  }
  
  def modificarPesoPorIntercambio() = this.genero match{
    case 'M' => modificarPeso(1)
    case 'F' => modificarPeso(-10)
  }

  //Ganar Experiencia
  
  def ganarExperiencia (exp: Int) = {
    this.experiencia += exp
    
    if (this.experiencia >= this.especie.experienciaParaNivel(this.nivel+1)){
      this.subirUnNivel()
    }
  }
  
  def subirUnNivel() = {
    this.nivel += 1
    this.especie.condicionDeEvolucion.map{_.subioDeNivel(this)}
  }
  
  def pierdeCon(tipo: Tipo): Boolean = {
    this.especie.tipos.forall{tipoPok => tipo.leGanaA(tipoPok)}
  }
  
}
