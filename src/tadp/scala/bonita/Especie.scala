package tadp.scala.bonita


class Especie(val unPesoMaximo: Int, val unosTipos: List[Tipo], val resistEvo: Int, val condicion: CondicionEvolucion = null, val especieEvo: Especie = null) { 
  val pesoMaximo: Int = unPesoMaximo
  val tipos: List[Tipo] = unosTipos
  val resistenciaEvolutiva: Int = resistEvo
  val especieDeEvolucion: Especie = especieEvo //Si no se especifica, queda null y significa que no puede evolucionar
  val condicionDeEvolucion: CondicionEvolucion = condicion //Si no se especifica, queda null y significa que no puede evolucionar
   
  def puedeLevantar(): Int = {
    val listaAux = this.tipos.map{case (tipo)=>tipo.puedoLevantar()} 
    return listaAux.max
    
  }
  
  
  def puedeEvolucionar(unPokemon: Pokemon): Unit = {
    this.tengoEspecieDeEvolucion()
    this.cumpleCondicion(unPokemon)
  }

  def tengoEspecieDeEvolucion() = {
    if(especieDeEvolucion == null){
     throw new EvolutionException("No tengo especie de evolucion") 
    }
  }
  
  def cumpleCondicion(unPokemon: Pokemon) = {
    this.condicionDeEvolucion.cumpleCondicion(unPokemon)
  }
  
  def experienciaParaNivel(nivel: Int): Int = nivel match{   
    case 1 => 0
    case _ => (2 * this.experienciaParaNivel(nivel-1)) + this.resistenciaEvolutiva
    // No estoy contemplando un nivel menor a 1
  }
  
  

}