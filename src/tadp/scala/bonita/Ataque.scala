package tadp.scala.bonita

class Ataque(val tipoAtaque: Tipo, var PA: Int, var PAMax: Int, val efectoAtaque: (Pokemon => Pokemon)){
  
  val tipo: Tipo = tipoAtaque
  var puntosAtaque: Int = PA
  var puntosAtaqueMax: Int = PAMax
  val efecto: Pokemon => Pokemon = efectoAtaque
  
  def usardoPor(pokemon: Pokemon){
    if (puntosAtaque == 0){
      throw new NoRemainingPPException("No quedan m�s PP!")
    }
    efecto(pokemon) //Esto no s� si deber�a provocar un efecto de lado
    this.puntosAtaque -= 1    
  }
  
}