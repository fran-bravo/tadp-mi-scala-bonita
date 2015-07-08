package tadp.scala.bonita
import scala.math
import scala.util.Try

package object Actividades{
  
  type Actividad = Pokemon => Pokemon
  val a : List[Int] = List()
  a.map { x => ??? }
  
  def irDespertando: Pokemon => Pokemon = {
    poke => poke.estado match {
      case Dormido(1) => poke.copy(estado = Saludable)
      case Dormido(n) => poke.copy(estado = Dormido(n-1))
    }
  }
  
  val realizar: (Pokemon, Actividad, Boolean) => Try[Pokemon] = {
    (poke, actividad, ignoraDormido) => Try(

        poke.estado match {
                            case KO => throw new KOException
                            case Dormido(_) => if(ignoraDormido) actividad(poke).validarCaracteristicas()
                                               else irDespertando(poke)
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
  
  def darPiedra(piedra:PiedraAbstract)(pokemon:Pokemon) = pokemon.usarPiedra(piedra)
  
  def aprenderAtaque(ataque:Ataque) (poke:Pokemon) = {
     if (ataque.esAfin(poke.especie)) poke.incorporar(ataque)
    else poke.pasarAKO()   
  }
  
  def usarPiedra(piedra:Piedra) (poke:Pokemon) = {
    poke.usarPiedra(piedra)
  }
  
  def descansar(pokemon:Pokemon) = {
    var poke : Pokemon = pokemon.recuperarPA()
    
    poke.estado match
    {
      case Saludable if (poke.energia < poke.energiaMaxima/2) => poke.pasarADormido()
      case _ => poke
    }
  }
  
  def fingirIntercambio(pokemon:Pokemon) = {
    pokemon.fingeIntercambio()
  }
  
  def comerHierro(pokemon:Pokemon) = pokemon.ganarFuerza(5)
  
  def comerCalcio(pokemon:Pokemon) = pokemon.ganarVelocidad(5)
  
  def comerZinc(pokemon:Pokemon) = pokemon.incrementarTodosLosPAMaxEn2
  
  def usarPocion(pokemon:Pokemon) = pokemon.curarEnergia(50)
  
  def usarAntidoto(pokemon:Pokemon) = pokemon.estado match
  {
    case Envenenado => pokemon.pasarASaludable
    case _ => pokemon
  }
  
  def usarEther(pokemon:Pokemon) = pokemon.pasarASaludable()
  
}