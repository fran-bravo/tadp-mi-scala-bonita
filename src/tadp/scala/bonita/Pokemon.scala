package tadp.scala.bonita

class Pokemon(var unGenero:Char, var unaEnergia: Int, var unaEnergiaMaxima: Int, var unPeso: Int, var unaFuerza: Int, var unaAgilidad: Int ) {
  var nivel: Int = 1 //De 1 a 100
  var experiencia: Int = 0
  var genero: Char = unGenero//Macho o Hembra
  var energia: Int = unaEnergia//Minimo 0, maximo energiaMaxima
  var energiaMaxima: Int = unaEnergiaMaxima
  var peso: Int = unPeso//Minimo 0
  var fuerza: Int = unaFuerza//De 1 a 100
  var agilidad: Int = unaAgilidad//De 1 a 100
  var estado: Estado = new Saludable
  
}
