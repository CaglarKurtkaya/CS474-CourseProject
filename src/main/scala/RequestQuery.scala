import Command.QueryCommand
import CommandUser.QueryUserCommand
import GitHub.GitHubObj.EmptyObj
import com.typesafe.scalalogging.LazyLogging


object RequestQuery extends App with LazyLogging{
  //For Github object
  val token = "00f3cbc4f54fd97c3c422fdab6f50fb1606eb16a"
  val authorizationName = "Bearer "
  val headerName = "Accept"
  val jsonValue = "application/json"

  //For building the query -> REPOSITORY
  val queryType = "REPOSITORY"
  val language = List("Java", "C++", "Python", "Scala")
  val first = 5
  val commitComments: (String, Int) = ("last", 5)
  val pullRequests: (String, Int) = ("last", 5)
  val issues: (String, Int) = ("last", 5)

  //For building the query -> USER
  val qType = "USER"
  val name = "emily"

  def printRepoResult(rps: Seq[Repository]): Unit = {
    for(i <- rps) {
      println("Repository name: " + i.name)
      println("Description: " + i.description.getOrElse())
      println("URL: " + i.githubUrl.getOrElse())
      println("Forks: " + i.forkCount)
      println("Stars: " + i.starsCount)
      println("Issues: " + i.issuesCount)
      println("Watchers: " + i.watchersCount)
      println("Pull requests: " + i.pullRequests)
      println("Releases: " + i.releaseCount)
      println("Commits: " + i.commits)
      for(j <- i.pullRequest) {
        println("Pull request author: " + j.authorUserName.getOrElse())
        println("Pull request creation date: " + j.createdAt.getOrElse())
        println("Pull request state: " + j.state.getOrElse())
        println("Pull request title: " + j.title.getOrElse())
        println()
      }
      for(k <- i.commitComments) {
        println("Commit comment author: " + k.authorName.getOrElse())
        println("Commit comment message: " + k.message.getOrElse())
        println("Commit comment state: " + k.state)
        println("Commit comment push date: " + k.pushedDate.getOrElse())
        println()
      }
    }
  }

  def printUserResult(usrs: Seq[User]): Unit = {
    for (i <- usrs) {
      println("Username: " + i.username)
      println("URL: " + i.url)
      println("Repositories: " + i.reposCount)
      println("Contributed repositories: " + i.contribReposCount)
      println("Followers: " + i.followersCount)
      println("Following: " + i.followingCount)
      println()
    }
  }


  val gitHubObject: Option[GHQLRespone] = new GitHub[EmptyObj].setHttp().setAuthorization(authorizationName, token).setHeader(headerName, jsonValue).build

  logger.info("GitHub object built")

  val result = gitHubObject.flatMap(new Command[QueryCommand].setRepo(queryType).setLanguages(language).setFirst(first).setCommitComments("first", 2).build())
    .get.filter(Stars("_>60000")).filter(Forks("_>32000")).filter(Watchers("_>2000"))

  val result2 = gitHubObject.flatMap(new CommandUser[QueryUserCommand].setUser(qType).setName(name).setFirst(first).build())
    .get.filter(Repos("_>100")).filter(ContribRepos("_>40"))

  logger.info("Repo result generated")

  printRepoResult(result)
  //printUserResult(result2)
}
