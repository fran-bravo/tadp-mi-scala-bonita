package tadp.scala.bonita

abstract class CondicionEvolucion {
  
  def cumple(unPokemon: Pokemon): Boolean
  
  def cumpleCondicion(unPokemon: Pokemon): Unit = {
    if(!this.cumple(unPokemon)){
      throw new EvolutionException("No cumple condicion de evolucion") 
    }
  }
  
  def fingeIntercambio(unPokemon: Pokemon): Unit = {
    unPokemon.modificarPesoPorIntercambio()
  }
  
}


class SubirNivel(val nivel: Int) extends CondicionEvolucion{
  
  val nivelEvolucion: Int = nivel
 
  override def cumple(unPokemon: Pokemon): Boolean = {
    return nivelEvolucion == unPokemon.nivel
  }

}


class Intercambiar() extends CondicionEvolucion{
  
  var fingioIntercambio: Boolean = false
  
  override def cumple(unPokemon: Pokemon): Boolean = {
    return fingioIntercambio
  }
  
  override def fingeIntercambio(unPokemon: Pokemon) = {
    this.fingioIntercambio = true
    unPokemon.evolucionar()
  }
  
}


class UsarPiedra extends CondicionEvolucion{
  
  override def cumple(unPokemon: Pokemon): Boolean = {
    //tiene que fijarse si usa una piedra evolutiva
    return true
  }
  
}

