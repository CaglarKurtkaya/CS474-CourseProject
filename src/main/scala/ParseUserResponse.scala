import play.api.libs.json.{JsArray, JsValue, Json}
import scala.collection.mutable.ListBuffer

class ParseUserResponse {
  def processUserResult(queryResult: String) : List[User] = {
    val response = Json.parse(queryResult)

    val userNodes = (response \ "data" \ "search" \ "edges").get.as[JsArray]
    val topUsers = new ListBuffer[User]

    for (userNode <- userNodes.value) {
      topUsers += createUser(userNode)
    }

    topUsers.toList
  }

  private def createUser(jsValue: JsValue): User = {
    val value = jsValue \ "node"

    User(username = (value \ "login").get.toString,
      url = (value \ "url").get.toString,
      reposCount = (value \ "repositories" \ "totalCount").as[Int],
      contribReposCount = (value \ "repositoriesContributedTo" \ "totalCount").as[Int],
      followersCount = (value \ "followers" \ "totalCount").as[Int],
      followingCount = (value \ "following" \ "totalCount").as[Int]
    )
  }
}
