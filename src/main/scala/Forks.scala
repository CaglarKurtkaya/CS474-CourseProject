object Forks extends FilterObjects {
  def apply(userInput : String) : Repository => Boolean = x => predicateForFilter(userInput, x.forkCount)
}