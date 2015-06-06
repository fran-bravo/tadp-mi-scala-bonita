package tadp.scala.bonita


abstract class Tipo(val perdedores: Tipo*) {
  
  def puedoLevantar(): Int = {
     return 1
  }
  
  def noLeGanaA(unPokemon: Pokemon): Boolean = {
    !this.leGanaA(unPokemon.especie.tipoPrincipal()) || !this.leGanaA(unPokemon.especie.tipoSecundario())
  }
  
  def leGanaA(unTipo: Tipo): Boolean ={
    perdedores.contains(unTipo)
  }
  
}

case object Fuego extends Tipo(Planta, Hielo, Bicho)

case object Agua extends Tipo(Fuego, Roca, Tierra)

case object Tierra extends Tipo(Electrico, Roca, Fuego, Veneno)

case object Roca extends Tipo(Volador, Fuego, Hielo, Bicho)

case object Pelea extends Tipo(Hielo, Roca, Normal) {
  override def puedoLevantar(): Int = {
    return 2
  }
}

case object Planta extends Tipo(Agua, Tierra, Roca)

case object Hielo extends Tipo(Dragon, Tierra, Planta, Volador)

case object Fantasma extends Tipo(Psiquico) {//FIXME FALTA AGREGAR FANTASMA
  override def puedoLevantar(): Int = {
    throw new TypeException("Soy de tipo fantasma")
  }
}

case object Electrico extends Tipo(Agua, Volador)

case object Veneno extends Tipo(Planta)

case object Psiquico extends Tipo(Veneno, Pelea)

case object Bicho extends Tipo(Psiquico, Planta)

case object Volador extends Tipo(Planta, Pelea, Bicho)

case object Normal extends Tipo()

case object Dragon extends Tipo()//FIXME FALTA DRAGON