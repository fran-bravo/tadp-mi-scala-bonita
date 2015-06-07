package tadp.scala.bonita

/**
 * @author Dario
 */
trait Actividad {
  def realizar(pokemon: Pokemon) : Pokemon = pokemon.estado match {
    case KO => throw new KOException
    case Dormido => pokemon //????? FALTA IMPLEMENTAR LO DE QUE SE VA DESPERTANDO
    case poke => doRealizar(pokemon)
  }
  
  def doRealizar(pokemon:Pokemon) : Pokemon
}

