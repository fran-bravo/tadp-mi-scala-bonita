package tadp.scala.bonita

import scala.util.{Try, Success, Failure}

class AnalizadorDeRutina(val criterio: Pokemon => Int) {
 //Le aplico las rutinas a un pokemon. Si ninguna rutina aplica para el pokemon, tiro error.
 //Si hay rutinas que si puedo hacer, elijo la mejor con mi criterio
  def elegirMejorRutina(pokemon: Pokemon, rutinas: List[Rutina]): Rutina = {
        val resultados = for {rutina <- rutinas
            if pokemon.realizarRutina(rutina).isSuccess
            } yield rutina
             
        
        if (resultados.size != 0){
          return resultados.maxBy{rut => criterio(pokemon.realizarRutina(rut).get)}
        } else {
          throw new NoRutineForPokemonException("El pokemon no puede hacer ninguna rutina del conjunto")
        }
  }

//  def elegirMejorRutina(pokemon: Pokemon, rutinas: List[Rutina]) : Rutina = 
//  {
//    val resultados = for
//    {
//      rutina <- rutinas
//      poke = pokemon.realizarRutina(rutina)
//      if poke.isSuccess
//      resultado = criterio(poke.get)
//    }
//    yield (rutina, resultado)
//    
//    return resultados.maxBy(_._2)._1
//  }
  
}

