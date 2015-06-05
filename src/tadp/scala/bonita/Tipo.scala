package tadp.scala.bonita


class Tipo {
  
  def puedoLevantar(): Int = {
     return 1
  }
  
  def noLeGanaA(unPokemon: Pokemon): Boolean = {
    !this.leGanaA(unPokemon.especie.tipoPrincipal()) || !this.leGanaA(unPokemon.especie.tipoSecundario())
  }
  
  def leGanaA(unTipo: Tipo): Boolean
  
}

class Fuego extends Tipo {
  
}

class Agua extends Tipo {
  
}

class Tierra extends Tipo {
  
}

class Roca extends Tipo {
  
}

class Pelea extends Tipo {
  override def puedoLevantar(): Int = {
    return 2
  }
}

class Planta extends Tipo {
  
}

class Hielo extends Tipo {
  
}

class Fantasma extends Tipo {
  override def puedoLevantar(): Int = {
    throw new TypeException("Soy de tipo fantasma")
  }
}

class Electrico extends Tipo {
  
}

class Veneno extends Tipo {
  
}

class Psiquico extends Tipo {
  
}

class Bicho extends Tipo {
  
}

class Volador extends Tipo {
  
}

class Normal extends Tipo {
  
}

class Dragon extends Tipo {
  
}