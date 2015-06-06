package tadp.scala.bonita

abstract class CondicionEvolucion {
  
  def fingeIntercambio(unPokemon: Pokemon): Pokemon = {
    unPokemon.modificarPesoPorIntercambio()
  }
  
  def usaPiedra(unPokemon: Pokemon, unaPiedra: PiedraAbstract): Pokemon = {
    return unPokemon
  }
  
  def subioDeNivel(unPokemon: Pokemon): Pokemon = { return unPokemon}
  
}


class SubirNivel(val nivel: Int) extends CondicionEvolucion{
  
  val nivelEvolucion: Int = nivel
 
  def cumple(unPokemon: Pokemon): Boolean = {
    return nivelEvolucion == unPokemon.nivel
  }
  
  override def subioDeNivel(unPokemon: Pokemon): Pokemon = {
    if(this.cumple(unPokemon))
      return unPokemon.evolucionar()
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
    unPokemon.evolucionar()
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
                  unPokemon.evolucionar()
               else
                  this.envenenar(unPokemon)
  }
  
  def envenenar(unPokemon: Pokemon): Pokemon = {
    unPokemon.pasarAEnvenenado()
  }
  
}

class UsarPiedraLunar extends UsarPiedra{
  override def usaPiedra(unPokemon: Pokemon, unaPiedra: PiedraAbstract): Pokemon = 
    unaPiedra match {
    case PiedraLunar => unPokemon.evolucionar()
    case _ => return unPokemon
    }
}

