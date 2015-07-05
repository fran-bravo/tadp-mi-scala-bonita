package tadp.scala.bonita

import scala.util.{Try, Success, Failure}

class AnalizadorDeRutina(val criterio: Pokemon => Int) {
 //Le aplico las rutinas a un pokemon. Si ninguna rutina aplica para el pokemon, tiro error.
 //Si hay rutinas que si puedo hacer, elijo la mejor con mi criterio
  def elegirMejorRutina(pokemon: Pokemon, rutinas: List[Rutina]): Rutina = {
    (for {rutina <- rutinas
        if pokemon.realizarRutina(rutina).isSuccess
        } yield if (rutinas.size != 0) {
          rutina
        } else {
          throw new NoRutineForPokemonException("El pokemon no puede hacer ninguna rutina del conjunto")
        }).maxBy{rut => criterio(pokemon.realizarRutina(rut).get)}

  }
  
}

