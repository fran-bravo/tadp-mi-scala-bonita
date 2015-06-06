package tadp.scala.bonita


class Especie(val unPesoMaximo: Int, val unosTipos: List[Tipo], val resistEvo: Int, val condicion: Option[CondicionEvolucion], val especieEvo: Option[Especie]) { 
  val pesoMaximo: Int = unPesoMaximo
  val tipos: List[Tipo] = unosTipos
  val resistenciaEvolutiva: Int = resistEvo
  val especieDeEvolucion: Option[Especie] = especieEvo
  val condicionDeEvolucion: Option[CondicionEvolucion] = condicion
   
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
  
  def experienciaParaNivel(nivel: Int): Int = nivel match{   
    case 1 => 0
    case _ => (2 * this.experienciaParaNivel(nivel-1)) + this.resistenciaEvolutiva
    // No estoy contemplando un nivel menor a 1
  }
  
  

}