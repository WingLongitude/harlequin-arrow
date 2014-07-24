import play.api.mvc._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object CorsFilter extends Filter {
	def apply(nextFilter: (RequestHeader) => Future[SimpleResult])
           (requestHeader: RequestHeader): Future[SimpleResult] = {
    nextFilter(requestHeader).map { result =>
      result.withHeaders( "Access-Control-Allow-Origin" -> "*",
        "Access-Control-Allow-Methods" -> "POST, GET, OPTIONS, PUT, DELETE",
        "Access-Control-Allow-Headers" -> "x-requested-with,content-type,Cache-Control,Pragma,Date"
      )
    }
  }
	
  /*def apply(next: (RequestHeader) => Result)(rh: RequestHeader) = {

    def cors(result: PlainResult): Result = {
      result.withHeaders( "Access-Control-Allow-Origin" -> "*",
        "Access-Control-Allow-Methods" -> "POST, GET, OPTIONS, PUT, DELETE",
        "Access-Control-Allow-Headers" -> "x-requested-with,content-type,Cache-Control,Pragma,Date"
      )
    }

    next(rh) match {
      case plain: PlainResult => cors(plain)
      case async: AsyncResult => async.transform(cors)
    }
  }*/
}