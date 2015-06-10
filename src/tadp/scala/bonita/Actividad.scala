package tadp.scala.bonita

/**
 * @author Dario
 */
trait Actividad {
  def realizar(pokemon: Pokemon) : Pokemon = pokemon.estado match {
    case KO => throw new KOException
    case Dormido(_) => pokemon.irDespertando()
    case poke => doRealizar(pokemon).validarCaracteristicas() //esto va a venir reemplazado por un try seguramente
  }
  
  def doRealizar(pokemon:Pokemon) : Pokemon
}

case class RealizarAtaque(ataque:Ataque) extends Actividad 
{
  def doRealizar(pokemon:Pokemon) : Pokemon =
  {
    if (pokemon.paActual(ataque) == 0) throw new NoRemainingPPException("No quedan mas PP!")
   pokemon.ganarExperiencia(ataque.experienciaPara(pokemon)).decrementarPA(ataque)
    
  }
}

case class Nadar(minutos: Int) extends Actividad
{
  def doRealizar(pokemon : Pokemon) : Pokemon = pokemon match
  {
    case poke if poke.pierdeCon(Agua) => poke.pasarAKO
    case poke if poke.especie.tipos.contains(Agua) => poke.ganarExperiencia(200 * minutos).ganarVelocidad(minutos/60).perderEnergia(minutos)
    case poke => poke.ganarExperiencia(200 * minutos).perderEnergia(minutos) //FIXME codigo repetido aqui!
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


case object Descansar extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = {
    var poke : Pokemon = pokemon.recuperarPA()
    poke.estado match
    {
      case Saludable if (poke.energia < poke.energiaMaxima/2) => poke.pasarADormido()
      case _ => poke
    }
  }
}



case object ComerHierro extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = pokemon.ganarFuerza(5)
}

case object ComerCalcio extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = pokemon.ganarVelocidad(5)
}

case object ComerZinc extends Actividad
{
  def doRealizar(pokemon: Pokemon) : Pokemon = pokemon.incrementarTodosLosPAMaxEn2
}

case object UsarPocion extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = pokemon.curarEnergia(50)
}

case object UsarAntidoto extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = pokemon.estado match
  {
    case Envenenado => pokemon.pasarASaludable
    case _ => pokemon
  }
}

case object UsarEther extends Actividad{
  def doRealizar(pokemon:Pokemon) = pokemon.estado match
  {
    case KO => pokemon
    case _ => pokemon.pasarASaludable
  }
}