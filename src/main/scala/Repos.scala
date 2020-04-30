object Repos extends FilterUserObjects {
  def apply(userInput : String): User => Boolean = x => predicateForFilter(userInput, x.reposCount)
}
