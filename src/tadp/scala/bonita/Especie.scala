package tadp.scala.bonita


class Especie(
    val pesoMaximo: Int,
    val tipoPrincipal : Tipo,
    val tipoSecundario : Option[Tipo] = None)
    (val resistenciaEvolutiva: Int,
    val evolucion: Option[Evolucion] = None)    
    (val incEnergiaMaxima: Int,
    val incPeso: Int,
    val incFuerza: Int,
    val incVelocidad: Int) {   
  
  
//  def incrementoParaNivel(stat: Int, nivel : Int) : Int = stat * (nivel-1)
//  
//  def incPesoParaLv(nivel : Int) : Int = incrementoParaNivel(incPeso, nivel)
//  def incEnergiaMaximaParaLv(nivel : Int) : Int = incrementoParaNivel(incEnergiaMaxima, nivel)
//  def incFuerzaParaLv(nivel : Int) : Int = incrementoParaNivel(incFuerza, nivel)
//  def incVelocidadParaLv(nivel : Int) : Int = incrementoParaNivel(incVelocidad, nivel)
//  
  
  
  
  
  def tieneElTipo(tipo: Tipo): Boolean = tipoPrincipal match
  {
    case `tipo` => true  //los backticks son porque, por default, poner tipo ahí es una variable libre del patrón, no el parámetro
    case _ => tipoSecundario match
    {
      case Some(tipo) => true
      case _ => false
    }
  }
  
  def tipos : List[Tipo] = tipoSecundario match {
    case Some(tipo) => List(tipoPrincipal, tipo)
    case None => List(tipoPrincipal)
    
  }
  
  def experienciaParaNivel(nivel: Int): BigInt = nivel match{   
    case 1 => 0
    case _ => (2 * this.experienciaParaNivel(nivel-1)) + this.resistenciaEvolutiva
    // No estoy contemplando un nivel menor a 1
  }
  
  def nivelParaExperiencia(experiencia : BigInt) : Int = {
    val rangoDeNiveles = 1 until 100
    rangoDeNiveles.find{ nivel => this.experienciaParaNivel(nivel) > experiencia }.getOrElse(101) - 1
  }
  
  def fingeIntercambio(pokemon:Pokemon) : Pokemon ={
    return this.condicionDeEvolucion.fingeIntercambio(pokemon)
  }
  
  def condicionDeEvolucion(): CondicionEvolucion = {
   return this.evolucion.get.condicion
  }
  
  def especieDeEvolucion(): Especie = {
    return this.evolucion.get.especie
  }
  
  def noTieneEvolucion(): Boolean = {
    return this.evolucion.isEmpty
  }
  

}