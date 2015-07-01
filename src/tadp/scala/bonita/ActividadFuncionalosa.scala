package tadp.scala.bonita
import scala.math
import scala.util.Try

package object Actividades{
  
  type Actividad = Pokemon => Pokemon
  
  def irDespertando: Pokemon => Pokemon = {
    poke => poke.estado match {
      case Dormido(1) => poke.copy(estado = Saludable)
      case Dormido(n) => poke.copy(estado = Dormido(n-1))
    }
  }

  val realizar: (Pokemon, Actividad) => Try[Pokemon] = {
    (poke, actividad) => Try(poke.estado match {
      case KO => throw new KOException
      case Dormido(_) => irDespertando(poke)
      case _ => actividad(poke).validarCaracteristicas()
    })
  }
  
  def levantarPesas (pesa: Int) (poke: Pokemon) = {
    poke.estado match
    {
      case Paralizado => poke.pasarAKO
      case _ if pesa > poke.fuerza*10 => poke.pasarAParalizado
      case _ if poke.tieneElTipo(Fantasma) => throw new TypeException 
      case _ => { var mult = 1: Int
                  if (poke.tieneElTipo(Pelea)) mult = 2
                  poke.ganarExperiencia(pesa*mult)
                }
    }
  }
  
  def realizarAtaque (ataque: Ataque) (poke: Pokemon) = {
     ataque.efecto(poke.ganarExperiencia(ataque.experienciaPara(poke)).decrementarPA(ataque))
  }
  
  def nadar (min: Int) (poke: Pokemon) = {
    poke match{
      case poke if poke.pierdeCon(Agua) => poke.pasarAKO
      case poke => poke.ganarExperiencia(200 * min).perderEnergia(min) match {
        case pokemon if pokemon.tieneElTipo(Agua) => pokemon.ganarVelocidad(min/60)
        case pokemon => pokemon
      }
    }
  }
  
}