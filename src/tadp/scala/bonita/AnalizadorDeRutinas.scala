package tadp.scala.bonita

import scala.util.{Try, Success, Failure}

class AnalizadorDeRutina(val criterio: Pokemon => Int) {
  
 //Agarra las al pokemon y le aplica las rutinas. Se queda solo con el nombre de aquellas que no tiren error
 //(Al menos eso quise hacer :$)
  def analizar(pokemon: Pokemon, rutinas: List[Rutina]): List[Rutina] = {  
   
	 return  rutinas.filter { rut => Try(pokemon.realizarRutina(rut)).isSuccess}  

  }
  
  
 //Le aplico las rutinas a un pokemon. Si ninguna rutina aplica para el pokemon, tiro error.
 //Si hay rutinas que si puedo hacer, elijo la mejor con mi criterio
  def elegirMejorRutina(pokemon: Pokemon, rutinas: List[Rutina]): Rutina = {
   
    val resultados = analizar(pokemon, rutinas)
    if (resultados.size == 0){
      return throw new NoRutineForPokemonException("El pokemon no puede hacer ninguna rutina del conjunto")
    }  else { 
      return resultados.maxBy { rut => criterio(pokemon.realizarRutina(rut).get) }
    }
  }
}

