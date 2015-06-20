package tadp.scala.bonita

import scala.util.Try

class Rutina(
  val nombre: String,
  val actividades: List[Actividad]) {
  
  
  def realizarRutina(pokemon: Pokemon): Try[Pokemon] = {
    Try(actividades.foldLeft(pokemon){(pokeFinal, actividadActual) => pokeFinal.realizarActividad(actividadActual).get})
  }
  
}
