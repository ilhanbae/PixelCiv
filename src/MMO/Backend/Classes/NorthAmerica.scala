package MMO.Backend.Classes

class NorthAmerica(username: String) extends Continent {
  override var Name: String = username

  override def resourcePerSecond(): Double = {
    0.0
  }
}


//class NorthAmerica() extends Continent(1000000,1000000, 500, Map("Money" -> 1000, "Troops" -> 100, "Resources" -> 1000)){
//
//}
