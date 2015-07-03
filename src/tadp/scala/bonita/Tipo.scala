package tadp.scala.bonita


abstract class Tipo(val nombresperdedores: List[String]) {

  
  def leGanaA(unTipo: Tipo): Boolean ={
    var perdedores : List[Tipo] = List()
    
    perdedores =nombresperdedores.map { nombre:String => nombre match {
                                                                        case "Planta" => Planta
                                                                        case "Fuego" => Fuego
                                                                        case "Agua" => Agua
                                                                        case "Hielo" => Hielo
                                                                        case "Dragon" => Dragon
                                                                        case "Normal" => Normal
                                                                        case "Roca" => Roca
                                                                        case "Tierra" => Tierra
                                                                        case "Veneno" => Veneno
                                                                        case "Electrico" => Electrico
                                                                        case "Volador" => Volador
                                                                        case "Bicho" => Bicho
                                                                        case "Fantasma" => Fantasma
                                                                        case "Pelea" => Pelea
                                                                        case "Psiquico" => Psiquico
                                                                      } 
    }
    perdedores.exists  {perdedor:Tipo => perdedor == unTipo}
  }
  
}

case object Fuego extends Tipo(List("Planta", "Hielo", "Bicho"))

case object Agua extends Tipo(List("Fuego", "Roca", "Tierra"))

case object Tierra extends Tipo(List("Electrico", "Roca", "Fuego", "Veneno"))

case object Roca extends Tipo(List("Volador", "Fuego", "Hielo", "Bicho"))

case object Pelea extends Tipo(List("Hielo", "Roca", "Normal")) 

case object Planta extends Tipo(List("Agua", "Tierra", "Roca"))

case object Hielo extends Tipo(List("Dragon", "Tierra", "Planta", "Volador"))

case object Fantasma extends Tipo(List("Psiquico", "Fantasma"))

case object Electrico extends Tipo(List("Agua", "Volador"))

case object Veneno extends Tipo(List("Planta"))

case object Psiquico extends Tipo(List("Veneno", "Pelea"))

case object Bicho extends Tipo(List("Psiquico", "Planta"))

case object Volador extends Tipo(List("Planta", "Pelea", "Bicho"))

case object Normal extends Tipo(List())

case object Dragon extends Tipo(List("Dragon"))

