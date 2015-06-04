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
  var estado: Estado = new Saludable
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
  
  def pasarAKO() = {
    this.estado = new KO
  }
  
  // Metodos auxiliares de levantarPesas
  
  def tengoFuerzaSuficiente(unosKilos:Int) = {
    if (unosKilos > this.fuerza * 10){
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
  
}
