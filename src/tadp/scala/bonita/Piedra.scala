package tadp.scala.bonita

class Piedra(val unTipo: Tipo) {
  val tipo: Tipo = unTipo
  
  def matcheaTipos(unPokemon: Pokemon): Boolean = {
    return (unPokemon.especie.tipoPrincipal() == tipo) || (!unPokemon.pierdeCon(tipo))
  }
  
}