package tadp.scala.bonita

import scala.util.{Try, Success, Failure}

class AnalizadorDeRutina(val criterio: Pokemon => Int) {
  
 //Agarra las al pokemon y le aplica las rutinas. Se queda solo con el nombre de aquellas que no tiren error
 //(Al menos eso quise hacer :$)
  def analizar(pokemon: Pokemon, rutinas: List[Rutina]): List[(String, Pokemon)] = {  
   var resultados: List[(String, Pokemon)] =List()
   
   rutinas match  {
     case Nil => resultados     
     case rutina :: restantes =>

       val poke: Try[Pokemon] = Try(pokemon.realizarRutina(rutina))
       
       if (poke.isSuccess) {
         resultados = resultados :+ (rutina.nombre, poke.get)
       }
       
       resultados :+ analizar(pokemon, restantes)

       resultados
       
   }

   return resultados
  }
  
  
 //Le aplico las rutinas a un pokemon. Si ninguna rutina aplica para el pokemon, tiro error.
 //Si hay rutinas que si puedo hacer, elijo la mejor con mi criterio
  def elegirMejorRutina(pokemon: Pokemon, rutinas: List[Rutina]): String = {
   
    val resultados = analizar(pokemon, rutinas)
   
    resultados.size match {
      case 0 => throw new NoRutineForPokemonException("El pokemon no puede hacer ninguna rutina del conjunto")
      case _ =>
        val posiblesPokemons : List[(String, Pokemon)] = rutinas.map { rutina =>                                                                      
                                                                       (rutina.nombre, rutina.realizarRutina(pokemon)) }

        posiblesPokemons.maxBy { case (nombre, poke) => criterio(poke) }._1
       
        
    }
    
  }
}

