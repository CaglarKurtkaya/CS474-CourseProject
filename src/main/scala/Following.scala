object Following extends FilterUserObjects {
  def apply(userInput : String) : User => Boolean = x => predicateForFilter(userInput, x.followingCount)
}
