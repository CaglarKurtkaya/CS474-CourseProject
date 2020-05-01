object Followers extends FilterObjects {
  def apply(userInput : String) : User => Boolean = x => predicateForFilter(userInput, x.followersCount)
}