package tadp.scala.bonita



class Especie(val unPesoMaximo: Int, val unosTipos: List[Tipo]) {
  val pesoMaximo: Int = unPesoMaximo
  val tipos: List[Tipo] = unosTipos
  
  def puedeLevantar(): Int = {
    val listaAux = this.tipos.map{case (tipo)=>tipo.puedoLevantar()} 
    return listaAux.max
    
  }
  
}