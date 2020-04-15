
import GitHub.GitHubObj.{CompleteGitHubObj, EmptyObj, SetAuthorization, SetHeader, SetHttp}
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils

//val gitHubObject:Option[GHQLRespone] = (new Github).Builder().setAuthorization(BEARER,GetAuthCodeFromConfig()).setHeader(ACCEPT, APPJSON).build()
case class GHQLRespone(httpUriRequest: HttpPost, client: CloseableHttpClient )

object GitHub {
  //Phantom type of Github object
  sealed trait GitHubObj
  object GitHubObj {
    sealed trait EmptyObj extends GitHubObj
    sealed trait SetHttp extends GitHubObj
    sealed trait SetAuthorization extends GitHubObj
    sealed trait SetHeader extends GitHubObj
    type CompleteGitHubObj = EmptyObj with SetHttp with SetAuthorization with SetHeader
  }
}

class GitHub[GitHubObj <: GitHub.GitHubObj](val httpUriRequest: HttpPost = null, val client: CloseableHttpClient = HttpClientBuilder.create.build) {
  //Method for building the Github object
  def Builder():(GitHub[GitHubObj with SetHttp])= {
    val BASE_GHQL_URL ="https://api.github.com/graphql"
    val httpUriRequest = new HttpPost(BASE_GHQL_URL)
    new GitHub[GitHubObj with SetHttp](httpUriRequest)
  }

  //Method for set up the authorization
  def setAuthorization(value : String, token : String): GitHub[GitHubObj with SetAuthorization] = {
    httpUriRequest.addHeader("Authorization", value + token)
    new GitHub[GitHubObj with SetAuthorization](httpUriRequest)
  }

  //Method for set up the header
  def setHeader(name : String, value : String): GitHub[GitHubObj with SetHeader] = {
    httpUriRequest.addHeader(name, value)
    new GitHub[GitHubObj with SetHeader](httpUriRequest)
  }
  //  def build(implicit ev: GitHubObj =:= CompleteGitHubObj) :String = {
  //    val temp = "{viewer {email login url}}"
  //    val gqlReq = new StringEntity("{\"query\":\"" + temp + "\"}")
  //    httpUriRequest.setEntity(gqlReq)
  //    val response: CloseableHttpResponse = client.execute(httpUriRequest)
  //    println("Response:" + response)
  //    response.getEntity match {
  //      case null => {
  //        //logger.info("Response entity is null")
  //        println("Response entity is null");
  //        null
  //      }
  //      case x if x != null => {
  //        val stringResponse = EntityUtils.toString(response.getEntity, "UTF-8")
  //        //        logger.info("Response payload {}", stringResponse)
  //        stringResponse
  //      }
  //    }
  //  }
  def build(implicit ev: GitHubObj =:= CompleteGitHubObj) : GHQLRespone = GHQLRespone(httpUriRequest, client)
}
