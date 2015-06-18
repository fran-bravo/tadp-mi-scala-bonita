package tadp.scala.bonita

abstract class PiedraAbstract{
  def dejaEvolucionarA(unPokemon: Pokemon): Boolean = {true}
  
  def perjudicasA(unPokemon: Pokemon): Boolean = {false}
}

class Piedra(val unTipo: Tipo) extends PiedraAbstract {
  val tipo: Tipo = unTipo
  
  override def dejaEvolucionarA(unPokemon: Pokemon): Boolean = {
    return (unPokemon.especie.tipoPrincipal() == tipo)
  }
  
  override def perjudicasA(unPokemon: Pokemon): Boolean = {
    return unPokemon.pierdeCon(tipo)
  }
}

case object PiedraLunar extends PiedraAbstract 