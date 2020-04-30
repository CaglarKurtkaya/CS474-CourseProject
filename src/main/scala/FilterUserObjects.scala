class FilterUserObjects {
  def predicateForFilter(userInput: String, value : Int) : Boolean = {
    val parse: List[Char] = userInput.toList
    val symbol = getGreaterOrLesser(parse)
    val intVal = getInt(parse)

    symbol match{
      case ">" => if(value > intVal) true else false
      case "<" => if(value < intVal) true else false
    }
  }

  private def getGreaterOrLesser(parsedInput: List[Char]): String = {
    parsedInput.filter(x => x == '>' || x == '<') .mkString
  }

  private def getInt(parsedInput: List[Char]) = {
    parsedInput.filter(x => x != '_' && x != '>' && x != '<').mkString.toInt
  }
}
