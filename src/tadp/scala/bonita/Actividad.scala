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

case class RealizarAtaque(ataque:Ataque) extends Actividad 
{
  def doRealizar(pokemon:Pokemon) : Pokemon =
  {
    if (pokemon.pa(ataque) == 0) throw new NoRemainingPPException("No quedan mas PP!")
   pokemon.ganarExperiencia(ataque.experienciaPara(pokemon)).decrementarPA(ataque)
    
  }
}

case class Nadar(minutos: Int) extends Actividad
{
  def doRealizar(pokemon : Pokemon) : Pokemon = pokemon match
  {
    case poke if poke.pierdeCon(Agua) => poke.pasarAKO
    case poke if poke.especie.tipos.contains(Agua) => poke.ganarExperiencia(200 * minutos).ganarVelocidad(minutos)
    case poke => poke.ganarExperiencia(200 * minutos) //FIXME codigo repetido aqui!
  }
}

case class AprenderAtaque(ataque:Ataque) extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = 
  {
    if (ataque.esAfin(pokemon.especie))
    {
      pokemon.incorporar(ataque)
    }
    
    else
    {
      pokemon.pasarAKO()
    }
    
  }

}