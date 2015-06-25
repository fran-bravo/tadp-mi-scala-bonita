package tadp.scala.bonita

case class Ataque(val nombre: String,
             val tipo: Tipo, 
             val puntosAtaqueBase: Int) 
             (val efecto: (Pokemon => Pokemon) = {p => p})
  {  
  
 /* def experienciaPara(pokemon: Pokemon) : Int = pokemon match
  {
    case poke if tipo == Dragon => 80
    case poke if poke.especie.tipoPrincipal == tipo => 50
    case poke if poke.especie.tipoSecundario == tipo && poke.genero == Macho => 20
    case poke if poke.especie.tipoSecundario == tipo && poke.genero == Hembra => 40
  }*/
  
  def experienciaPara(pokemon: Pokemon) : Int = pokemon match
  {
    case poke if tipo == Dragon => 80
    case poke if poke.especie.tipoPrincipal == tipo => 50
    case poke => poke.especie.tipoSecundario match {
      case Some(`tipo`) => poke.genero match {
        case Macho => 20
        case Hembra => 40
      }
      case _ => 0 // Ya sea porque no tiene tipo secundario o porque los tipos no coinciden
    }
  }
  
  def esAfin(especie: Especie) : Boolean = tipo match
  {
    case Normal => true
    case other if especie.tieneElTipo(tipo) => true
    case _ => false
  }
    
  
}