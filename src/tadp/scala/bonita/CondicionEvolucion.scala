package tadp.scala.bonita

class Evolucion(val condicion:CondicionEvolucion, val especie:Especie){
  
}

abstract class CondicionEvolucion {
  
  def fingeIntercambio(unPokemon: Pokemon): Pokemon = {
    unPokemon.modificarPesoPorIntercambio()
  }
  
  def usaPiedra(unPokemon: Pokemon, unaPiedra: PiedraAbstract): Pokemon = {
    return unPokemon
  }
  
  def subioDeNivel(unPokemon: Pokemon): Pokemon = { return unPokemon}
  /*Se podría extraer en este método, pero generaría el mismo problema de "que pasa si alguien le manda ese mensaje"
  def evolucionarPokemon(unPokemon: Pokemon): Pokemon = { unPokemon.copy(especie = unPokemon.especie.especieDeEvolucion) }
  */
}


class SubirNivel(val nivel: Int) extends CondicionEvolucion{
  
  val nivelEvolucion: Int = nivel
 
  def cumple(unPokemon: Pokemon): Boolean = {
    return nivelEvolucion == unPokemon.nivel
  }
  
  override def subioDeNivel(unPokemon: Pokemon): Pokemon = {
    if(this.cumple(unPokemon))
      return unPokemon.copy(especie = unPokemon.especie.especieDeEvolucion)
    return unPokemon
  }

}


class Intercambiar() extends CondicionEvolucion{
  
  var fingioIntercambio: Boolean = false
  
  def cumple(unPokemon: Pokemon): Boolean = {
    return fingioIntercambio
  }
  
  override def fingeIntercambio(unPokemon: Pokemon): Pokemon = {
    this.fingioIntercambio = true
    unPokemon.copy(especie = unPokemon.especie.especieDeEvolucion)
  }
  
}


class UsarPiedra extends CondicionEvolucion{
   
  def cumple(unPokemon: Pokemon, unaPiedra: PiedraAbstract): Boolean = {
    return unaPiedra.dejaEvolucionarA(unPokemon)
  }
  
  override def usaPiedra(unPokemon: Pokemon, unaPiedra: PiedraAbstract): Pokemon =
    unaPiedra match{
    case PiedraLunar => return unPokemon
    case _ =>  if(this.cumple(unPokemon, unaPiedra))
                  unPokemon.copy(especie = unPokemon.especie.especieDeEvolucion)
               else
                  unPokemon
  }
  
}

class UsarPiedraLunar extends UsarPiedra{
  override def usaPiedra(unPokemon: Pokemon, unaPiedra: PiedraAbstract): Pokemon = 
    unaPiedra match {
    case PiedraLunar => unPokemon.copy(especie = unPokemon.especie.especieDeEvolucion)
    case _ => return unPokemon
    }
}

