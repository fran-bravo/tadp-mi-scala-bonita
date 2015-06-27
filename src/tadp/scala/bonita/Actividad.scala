package tadp.scala.bonita

import scala.util.Try
/**
 * @author Dario
 */

//la idea es que acá el resultado cada actividad depende sólo de sus parámetros (y del pokemon)
//puede pensarse como en el micro, que cada instrucción depende sólo de sus parámetros (y del micro)
//un siguiente paso sería sacar este código de realizar la actividad a un gran pattern match
//por ahora no tiene mucho sentido que sean case classes pero va a servir más adelante

trait Actividad {
  def realizar(pokemon: Pokemon) : Try[Pokemon] = Try(pokemon.estado match {
    case KO => throw new KOException
    case Dormido(_) => pokemon.irDespertando()
    case poke => doRealizar(pokemon).validarCaracteristicas() //esto va a venir reemplazado por un try seguramente
  })
  
  def doRealizar(pokemon:Pokemon) : Pokemon
}



case class RealizarAtaque(ataque:Ataque) extends Actividad 
{
  def doRealizar(pokemon:Pokemon) : Pokemon =
  {
    ataque.efecto(pokemon.ganarExperiencia(ataque.experienciaPara(pokemon)).decrementarPA(ataque))
  }
}

case class LevantarPesas(kg: Int) extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = pokemon.estado match
    {
      case Paralizado => pokemon.pasarAKO
      case _ if kg > pokemon.fuerza*10 => pokemon.pasarAParalizado
      case _ if pokemon.tieneElTipo(Fantasma) => throw new TypeException 
      case _ => { var mult = 1: Int
                  if (pokemon.tieneElTipo(Pelea)) mult = 2
                  pokemon.ganarExperiencia(kg*mult)
                }
    }

}

case class Nadar(minutos: Int) extends Actividad
{
  def doRealizar(pokemon : Pokemon) : Pokemon = pokemon match
  {
    case poke if poke.pierdeCon(Agua) => poke.pasarAKO
    case poke if poke.tieneElTipo(Agua) => poke.ganarExperiencia(200 * minutos).ganarVelocidad(minutos/60).perderEnergia(minutos)
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

case class DarPiedra(piedra:PiedraAbstract) extends Actividad{
  def doRealizar(pokemon:Pokemon) : Pokemon = {
    pokemon.usarPiedra(piedra)
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

case object FingirIntercambio extends Actividad
{
  def doRealizar(pokemon:Pokemon) : Pokemon = {
    pokemon.fingeIntercambio()
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
  override def realizar(pokemon: Pokemon) : Try[Pokemon] = Try(pokemon.estado match {
    case KO => throw new KOException
    case poke => doRealizar(pokemon).validarCaracteristicas() //esto va a venir reemplazado por un try seguramente
  })
  
  def doRealizar(pokemon:Pokemon) = pokemon.estado match
  {
    case KO => pokemon
    case _ => pokemon.pasarASaludable
  }
}