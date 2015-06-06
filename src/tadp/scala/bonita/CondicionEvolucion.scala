package tadp.scala.bonita

abstract class CondicionEvolucion {
  
  def fingeIntercambio(unPokemon: Pokemon): Unit = {
    unPokemon.modificarPesoPorIntercambio()
  }
  
  def usaPiedra(unPokemon: Pokemon, unaPiedra: Piedra){
    //Si la condicion no es usar piedra, no hace nada?
  }
  
  def subioDeNivel(unPokemon: Pokemon){}
  
}


class SubirNivel(val nivel: Int) extends CondicionEvolucion{
  
  val nivelEvolucion: Int = nivel
 
  def cumple(unPokemon: Pokemon): Boolean = {
    return nivelEvolucion == unPokemon.nivel
  }
  
  override def subioDeNivel(unPokemon: Pokemon) = {
    if(this.cumple(unPokemon))
      unPokemon.evolucionar()
  }

}


class Intercambiar() extends CondicionEvolucion{
  
  var fingioIntercambio: Boolean = false
  
  def cumple(unPokemon: Pokemon): Boolean = {
    return fingioIntercambio
  }
  
  override def fingeIntercambio(unPokemon: Pokemon) = {
    this.fingioIntercambio = true
    unPokemon.evolucionar()
  }
  
}


class UsarPiedra extends CondicionEvolucion{
   
  def cumple(unPokemon: Pokemon, unaPiedra: Piedra): Boolean = {
    return unaPiedra.matcheaTipos(unPokemon)
  }
  
  override def usaPiedra(unPokemon: Pokemon, unaPiedra: Piedra){
    if(this.cumple(unPokemon, unaPiedra))
      unPokemon.evolucionar()
    else this.penalizar(unPokemon)
  }
  
  def penalizar(unPokemon: Pokemon){
    unPokemon.pasarAEnvenenado()
  }
  
}

