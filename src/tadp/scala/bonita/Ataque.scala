package tadp.scala.bonita

class Ataque(val nombre: String,
             val tipo: Tipo, 
             val puntosAtaqueBase: Int, 
             val efecto: (Pokemon => Pokemon) = {p => p})
  {  
  
  def experienciaPara(pokemon: Pokemon) : Int = pokemon match
  {
    case poke if tipo == Dragon => 80
    case poke if poke.especie.tipoPrincipal() == tipo => 50
    case poke if poke.especie.tipoSecundario() == tipo && poke.genero == 'M' => 20
    case poke if poke.especie.tipoSecundario() == tipo && poke.genero == 'F' => 40
  }
  
  def esAfin(especie: Especie) : Boolean = tipo match
  {
    case Normal => true
    case other if especie.tipos.contains(other) => true
    case _ => false
  }
    
  
}

class AtaqueConcreto(unAtaque: Ataque)
{
  val ataque: Ataque = unAtaque
  var puntosAtaque: Int = unAtaque.puntosAtaqueBase
  var puntosAtaqueMax: Int = unAtaque.puntosAtaqueBase

   def usadoPor(pokemon: Pokemon){
    if (puntosAtaque == 0){
      throw new NoRemainingPPException("No quedan mas PP!")
    }
   ataque.efecto(pokemon) //Esto no se si deberia provocar un efecto de lado
   this.puntosAtaque -= 1    
  }
  
}