package tadp.scala.bonita

class Estado{
  def paralisis(): Boolean = {
    return false
  }
  
  def knockeado(): Boolean = {
    return false
  }
  
  def envenenado(): Boolean = {
    return false
  }
}

case object Saludable extends Estado

case object Dormido extends Estado

case object Envenenado extends Estado {
  override def envenenado(): Boolean = {
    return true
  }
}

case object Paralizado extends Estado{
  override def paralisis(): Boolean = {
    return true
  }
}

case object KO extends Estado{
  override def knockeado(): Boolean = {
    return true
  }
}