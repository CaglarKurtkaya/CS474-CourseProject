import java.io.{File, PrintWriter}
import Command.QueryCommand
import CommandUser.QueryUserCommand
import GitHub.GitHubObj.EmptyObj
import com.typesafe.scalalogging.LazyLogging
import com.typesafe.config.{Config, ConfigFactory}
import scala.jdk.CollectionConverters


object RequestQuery extends App with LazyLogging{
  //For loading the config file
  val conf: Config = ConfigFactory.load()

  //For Github object
  val token = conf.getString("token")
  val authorizationName = conf.getString("authorizationName")
  val headerName = conf.getString("headerName")
  val jsonValue = conf.getString("jsonValue")

  //For building the query -> REPOSITORY
  val queryType = conf.getString("queryType")
  val language = CollectionConverters.ListHasAsScala(conf.getStringList("language")).asScala.toList
  val first = conf.getInt("first")
  val commitCommentsFirst = conf.getString("commitCommentsFirst")
  val commitCommentsValue = conf.getInt("commitCommentsValue")

  //For building the query -> USER
  val qType = conf.getString("qType")
  val name = conf.getString("name")

  //For writing the output to a file
  val writer = new PrintWriter(new File(conf.getString("fileName")))

  //Printing out the result if the repo type is REPOSITORY
  def printRepoResult(rps: Seq[Repository]): Unit = {
    for(i <- rps) {
      writer.write("Repository name: " + i.name + "\n")
      writer.write("Description: " + i.description.getOrElse() + "\n")
      writer.write("URL: " + i.githubUrl.getOrElse() + "\n")
      writer.write("Forks: " + i.forkCount + "\n")
      writer.write("Stars: " + i.starsCount + "\n")
      writer.write("Issues: " + i.issuesCount + "\n")
      writer.write("Watchers: " + i.watchersCount + "\n")
      writer.write("Pull requests: " + i.pullRequests + "\n")
      writer.write("Releases: " + i.releaseCount + "\n")
      writer.write("Commits: " + i.commits + "\n\n")
      for(j <- i.pullRequest) {
        writer.write("Pull request author: " + j.authorUserName.getOrElse() + "\n")
        writer.write("Pull request creation date: " + j.createdAt.getOrElse() + "\n")
        writer.write("Pull request state: " + j.state.getOrElse() + "\n")
        writer.write("Pull request title: " + j.title.getOrElse() + "\n\n")

      }
      for(k <- i.commitComments) {
        writer.write("Commit comment author: " + k.authorName.getOrElse() + "\n")
        writer.write("Commit comment message: " + k.message.getOrElse() + "\n")
        writer.write("Commit comment state: " + k.state + "\n")
        writer.write("Commit comment push date: " + k.pushedDate.getOrElse() + "\n\n")
      }
    }
    writer.close()
  }

  //Printing out the result if the repo type is USER
  def printUserResult(usrs: Seq[User]): Unit = {
    for (i <- usrs) {
      writer.write("Username: " + i.username + "\n")
      writer.write("URL: " + i.url + "\n")
      writer.write("Repositories: " + i.reposCount + "\n")
      writer.write("Contributed repositories: " + i.contribReposCount + "\n")
      writer.write("Followers: " + i.followersCount + "\n")
      writer.write("Following: " + i.followingCount + "\n\n")
    }
    writer.close()
  }


  val gitHubObject: Option[GHQLRespone] = new GitHub[EmptyObj].setHttp().setAuthorization(authorizationName, token).setHeader(headerName, jsonValue).build

  logger.info("GitHub object built")

  val result = gitHubObject.flatMap(new Command[QueryCommand].setRepo(queryType).setLanguages(language).setFirst(first).setCommitComments(commitCommentsFirst, commitCommentsValue).build())
    .get.filter(Stars("_>60000")).filter(Forks("_>32000")).filter(Watchers("_>2000"))

  val result2 = gitHubObject.flatMap(new CommandUser[QueryUserCommand].setUser(qType).setName(name).setFirst(first).build())
    .get.filter(Repos("_>100")).filter(ContribRepos("_>40"))

  logger.info("Repo result generated")

  printRepoResult(result)
  //printUserResult(result2)
}
