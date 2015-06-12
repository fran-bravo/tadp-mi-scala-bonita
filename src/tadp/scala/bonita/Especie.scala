package tadp.scala.bonita


class Especie(
    val pesoMaximo: Int,
    val tipos: List[Tipo],
    val resistenciaEvolutiva: Int,
    val condicionDeEvolucion: Option[CondicionEvolucion],
    val especieDeEvolucion: Option[Especie],
    val incEnergiaMaxima: Int,
    val incPeso: Int,
    val incFuerza: Int,
    val incVelocidad: Int) {   
  
   
  def puedeLevantar(): Int = {
    val listaAux = this.tipos.map{case (tipo)=>tipo.puedoLevantar()} 
    return listaAux.max
    
  }
  
  def tipoPrincipal(): Tipo = {
    return tipos.head
  }
  
  def tipoSecundario(): Tipo = {
    return tipos.last //asumiendo que solo se pueden tener como maximo 2 tipos
  }
  
  def tieneElTipo(tipo: Tipo): Boolean = tipos.contains(tipo)
  
  def experienciaParaNivel(nivel: Int): Int = nivel match{   
    case 1 => 0
    case _ => (2 * this.experienciaParaNivel(nivel-1)) + this.resistenciaEvolutiva
    // No estoy contemplando un nivel menor a 1
  }
  
  def fingeIntercambio(pokemon:Pokemon) : Pokemon ={
    var poke = this.condicionDeEvolucion.get.fingeIntercambio(pokemon)
    return poke
  }
  
  

}