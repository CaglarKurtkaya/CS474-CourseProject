object ContribRepos extends FilterUserObjects {
  def apply(userInput : String) : User => Boolean = x => predicateForFilter(userInput, x.contribReposCount)
}
