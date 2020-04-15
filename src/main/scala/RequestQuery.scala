import GitHub.GitHubObj.EmptyObj
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.entity.StringEntity
import org.apache.http.util.EntityUtils
import org.slf4j.LoggerFactory

object RequestQuery {

  def findRepo() : String = {
    //Val for logging
    val logger = LoggerFactory.getLogger(this.getClass)

    val token = "00f3cbc4f54fd97c3c422fdab6f50fb1606eb16a"
    val authorizationName = "Bearer "
    val headerName = "Accept"
    val jsonValue = "application/json"

    //Val for setting up the query
    val language = "Java"
    val repoCount = 1

    //Instantiate new Github object
    val gitHubObject = new GitHub[EmptyObj].Builder().setAuthorization(authorizationName, token).setHeader(headerName, jsonValue).build

    //Set up the query
    val query = Query.findRepo(language, repoCount)
    //val query = "{viewer {email login url}}"

    //Get the repo
    val gqlReq = new StringEntity("{\"query\":\"" + query + "\"}")

    gitHubObject.httpUriRequest.setEntity(gqlReq)

    val response: CloseableHttpResponse = gitHubObject.client.execute(gitHubObject.httpUriRequest)

    //Check if the entity is empty or not
    response.getEntity match {
      case null => {
        val nullResponse = "Response entity is null"
        nullResponse
      }
      case x if x != null => {
        val stringResponse = EntityUtils.toString(response.getEntity, "UTF-8")
        //logger.info("Response payload {}", stringResponse)
        stringResponse
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val response = RequestQuery.findRepo()
    println(response)
  }

}
