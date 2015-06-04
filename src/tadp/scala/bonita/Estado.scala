package tadp.scala.bonita

class Estado{
  def paralisis(): Boolean = {
    return false
  }
  
  def knockeado(): Boolean = {
    return false
  }
}

class Saludable extends Estado{
  
}

class Dormido extends Estado {
  
}

class Envenenado extends Estado {
  
}

class Paralizado extends Estado{
  override def paralisis(): Boolean = {
    return true
  }
}

class KO extends Estado{
  override def knockeado(): Boolean = {
    return true
  }
}