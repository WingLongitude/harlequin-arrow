package controllers

import util.RestResourceUtil
import play.api.mvc._
import com.wordnik.swagger.core.util.ScalaJsonUtil

class BaseApiController extends Controller with RestResourceUtil {
	// Util methods for JSON data rendering
	protected def JsonResponse(data: Object) = {
		val jsonValue: String = toJsonString(data)
		new SimpleResult(header = ResponseHeader(200), body = play.api.libs.iteratee.Enumerator(jsonValue.getBytes())).as("application/json")
			.withHeaders(
					("Access-Control-Allow-Origin", "*"),
					("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"),
					("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization"))
	}
	
	protected def JsonResponse(data: Object, code: Int) = {
		val jsonValue: String = toJsonString(data)
		new SimpleResult(header = ResponseHeader(code), body = play.api.libs.iteratee.Enumerator(jsonValue.getBytes())).as("application/json")
			.withHeaders(
					("Access-Control-Allow-Origin", "*"),
					("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT"),
					("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization"))
	}
	
	def toJsonString(data: Any): String = {
		if (data.getClass.equals(classOf[String])) {
			data.asInstanceOf[String]
		} else {
			ScalaJsonUtil.mapper.writeValueAsString(data)
		}
	}
	
	def preflight(all: String) = Action {
		Ok("").withHeaders("Access-Control-Allow-Origin" -> "*",
			"Allow" -> "*",
      "Access-Control-Allow-Methods" -> "POST, GET, PUT, DELETE, OPTIONS",
			"Access-Control-Allow-Headers" -> "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
  }
}