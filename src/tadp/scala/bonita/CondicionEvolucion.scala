package tadp.scala.bonita

abstract class CondicionEvolucion {
  
  def cumple(unPokemon: Pokemon): Boolean
  
  def cumpleCondicion(unPokemon: Pokemon): Unit = {
    if(!this.cumple(unPokemon)){
      throw new EvolutionException("No cumple condicion de evolucion") 
    }
  }
  
}


class SubirNivel(val nivel: Int) extends CondicionEvolucion{
  
  val nivelEvolucion: Int = nivel //Si es cero, quiere decir que no evoluciona
 
  override def cumple(unPokemon: Pokemon): Boolean = {
    return nivelEvolucion == unPokemon.nivel
  }

}


class Intercambiar() extends CondicionEvolucion{
  
  override def cumple(unPokemon: Pokemon): Boolean = {
    //tiene que fijarse si el pokemon cree que lo quieren intercambiar
    return true
  }
  
}


class UsarPiedra extends CondicionEvolucion{
  
  override def cumple(unPokemon: Pokemon): Boolean = {
    //tiene que fijarse si usa una piedra evolutiva
    return true
  }
  
}

