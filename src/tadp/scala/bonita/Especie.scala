package tadp.scala.bonita



class Especie(val unPesoMaximo: Int, val unosTipos: List[Tipo], val condicion: CondicionEvolucion, val especieEvo: Especie = null) { 
  val pesoMaximo: Int = unPesoMaximo
  val tipos: List[Tipo] = unosTipos
  val especieDeEvolucion: Especie = especieEvo //Si no se especifica, queda null y significa que no puede evolucionar
  val condicionDeEvolucion: CondicionEvolucion = condicion
   
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
    this.condicionDeEvolucion.cumple(unPokemon)
  }
  

}