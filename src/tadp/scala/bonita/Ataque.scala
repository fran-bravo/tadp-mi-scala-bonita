package tadp.scala.bonita

class Ataque(val tipoAtaque: Tipo, var PA: Int, val efectoAtaque: (Pokemon => Pokemon)){
  
  val tipo: Tipo = tipoAtaque
  val puntosAtaqueBase = PA
  val efecto: Pokemon => Pokemon = efectoAtaque
  
  def experienciaPara(pokemon: Pokemon) : Int = pokemon match
  {
    case poke if tipo == Dragon => 80
    case poke if poke.especie.tipoPrincipal() == tipo => 50
    case poke if poke.especie.tipoSecundario() == tipo && poke.genero == 'M' => 20
    case poke if poke.especie.tipoSecundario() == tipo && poke.genero == 'F' => 40
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