package tadp.scala.bonita

abstract class CondicionEvolucion {
  
  def cumple(unPokemon: Pokemon, unaPiedra: Piedra = null): Boolean
    
  def cumpleCondicion(unPokemon: Pokemon): Unit = {
    if(!this.cumple(unPokemon)){
      throw new EvolutionException("No cumple condicion de evolucion") 
    }
  }
  
  def fingeIntercambio(unPokemon: Pokemon): Unit = {
    unPokemon.modificarPesoPorIntercambio()
  }
  
  def usaPiedra(unPokemon: Pokemon, unaPiedra: Piedra){
    //Si la condicion no es usar piedra, no hace nada?
  }
  
}


class SubirNivel(val nivel: Int) extends CondicionEvolucion{
  
  val nivelEvolucion: Int = nivel
 
  override def cumple(unPokemon: Pokemon, unaPiedra: Piedra = null): Boolean = {
    return nivelEvolucion == unPokemon.nivel
  }

}


class Intercambiar() extends CondicionEvolucion{
  
  var fingioIntercambio: Boolean = false
  
  override def cumple(unPokemon: Pokemon, unaPiedra: Piedra = null): Boolean = {
    return fingioIntercambio
  }
  
  override def fingeIntercambio(unPokemon: Pokemon) = {
    this.fingioIntercambio = true
    unPokemon.evolucionar()
  }
  
}


class UsarPiedra extends CondicionEvolucion{
   
  override def cumple(unPokemon: Pokemon, unaPiedra: Piedra): Boolean = {
    return unaPiedra.matcheaTipos(unPokemon)
  }
  
  override def usaPiedra(unPokemon: Pokemon, unaPiedra: Piedra){
    unPokemon.evolucionar()
  }
  
  def penalizar(unPokemon: Pokemon){
    unPokemon.pasarAEnvenenado()
  }
  
 override def cumpleCondicion(unPokemon: Pokemon): Unit = { //Redefino para poder agregar el penalizar. Quedo choto :\
    if(!this.cumple(unPokemon)){
      penalizar(unPokemon)
      throw new EvolutionException("No cumple condicion de evolucion") 
    }
  }
  
}

