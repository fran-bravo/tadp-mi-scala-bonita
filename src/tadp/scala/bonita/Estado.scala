package tadp.scala.bonita

trait Estado

case object Saludable extends Estado

case class Dormido(val profundidad:Int = 3) extends Estado

case object Envenenado extends Estado

case object Paralizado extends Estado

case object KO extends Estado