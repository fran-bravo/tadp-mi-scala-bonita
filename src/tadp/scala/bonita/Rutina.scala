package tadp.scala.bonita


class Rutina(
  val nombre: String,
  val actividades: List[Actividad]) {
  
  
  def realizarRutina(pokemon: Pokemon): Pokemon = {
    
    var poke = pokemon.copy()
   actividades.foldLeft(poke){(actividadAnterior, actividadActual) => poke.realizarActividad(actividadActual)}
    
    return poke
   
  }
  
}