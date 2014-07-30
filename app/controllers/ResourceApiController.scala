package controllers

import com.wordnik.swagger.annotations._
import models.Resource
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import api._
import play.api.mvc._
import javax.ws.rs.{QueryParam, PathParam}
import play.api.libs.json.Json
import org.slf4j.LoggerFactory

/**
 * Controllers for resource administration and job control.
 * @author SIB Colombia
 *
 */
@Api(value = "/resource", description = "Operations about resources")
class ResourceApiController extends BaseApiController {
	
	// Logger factory initialization
	val logger = LoggerFactory.getLogger(getClass)
	
	// Datastructure for resource data persistence creating an instance of the table
  val resourceData = TableQuery[ResourceData]  
	
	def getOptions(path: String) = Action {
		implicit request => JsonResponse(new value.ApiResponse(200, "Ok"))
	}
	
	@ApiOperation(value = "Find resource by URL", 
			notes = "Returns a resource", 
			response = classOf[models.Resource],
			httpMethod = "GET", 
			nickname = "findResourceByURL")
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Resource not found")))
  def getResourceByURL(
  		@ApiParam(value = "URL of the resource to fetch") @PathParam("url") url: String) = DBAction {
		implicit rs =>
			logger.info("Getting a resource with url: "+url+".")
			val resourceRequired = resourceData.filter(_.url === url)
			if(resourceRequired.length.run > 0) {
				Ok(Json.toJson(resourceRequired.list))
			} else {
				logger.info("Resource not found.")
				JsonResponse(new value.ApiResponse(404, "Resource not found"), 404)
			}
	}
	
	@ApiOperation(value = "Get all resources", 
			notes = "Returns all the resources", 
			response = classOf[models.Resource], 
			responseContainer = "List",
			httpMethod = "GET", 
			nickname = "getResource")
  @ApiResponses(Array(
    new ApiResponse(code = 404, message = "Resource not found")))
  def getResources() = DBAction {
		implicit rs =>
			logger.info("Getting list of resources.")
			if(resourceData.length.run > 0) {
				Ok(Json.toJson(resourceData.list))
			} else {
				logger.info("Resource not found. Resource list is empty.")
				JsonResponse(new value.ApiResponse(404, "Resource not found"), 404)
			}
	}
	
	/**
	 * This function assumes that sharedParameters was previously created by the child class.
	 * NullPointerException will be thrown if sharedParameters wasn't created.
	 * @param key
	 * @param obj
	 */
	@ApiOperation(value = "Add a new Resource",
		response = classOf[models.Resource],
		httpMethod = "POST",
		nickname = "addNewResource")
	@ApiResponses(Array(
		new ApiResponse(code = 400, message = "Duplicate resource url"),
		new ApiResponse(code = 405, message = "Invalid input")))
	@ApiImplicitParams(Array(
		new ApiImplicitParam(name="body", value = "Resource object to be added to the database for index.", required = true, dataType = "models.Resource", paramType = "body")))
	def addResource() = DBAction(parse.json) {
		implicit rs =>
			logger.info("Adding new resource.")
			rs.request.body.validate[Resource].map { resource =>
				try {
					resourceData += resource
					logger.info("New resource with url \"" + resource.url + "\" has been saved.")
					Ok(Json.toJson(resource))
				} catch {
					case e: Exception =>
						logger.info("Failed to add new resource.")
						logger.error("Error trying to add a new resource " + e)
						JsonResponse(new value.ApiResponse(400, "Duplicate resource url"))
				}
			}.getOrElse(
				{				
					logger.info("Failed to add new resource. Invalid input data.")
					JsonResponse(new value.ApiResponse(405, "Invalid input"))
				}
			)
	}
	
	@ApiOperation(value = "Delete resource", 
			notes = "Removes a resource from indexer backend.",
			httpMethod = "DELETE", 
			nickname = "deleteResourceByURL")
  @ApiResponses(Array(
  	new ApiResponse(code = 400, message = "Invalid URL supplied"),
    new ApiResponse(code = 200, message = "Resource deleted"),
    new ApiResponse(code = 404, message = "Resource not found")))
  def deleteResourceByURL(
    @ApiParam(value = "URL of the resource to delete") @PathParam("url") url: String) = DBAction(parse.empty) { 
		implicit rs =>
    	logger.info("Deleting a resource with url: "+url+".")
			val resourceRequired = resourceData.filter(_.url === url)
			if(resourceRequired.length.run > 0) {
				val affectedRowsCount = resourceRequired.delete
				if(affectedRowsCount > 0) {
					logger.info("Resource with url: "+url+", has been deleted.")
					JsonResponse(new value.ApiResponse(200, "Resource deleted"), 200)
				} else {
					logger.info("Invalid URL supplied.")
					JsonResponse(new value.ApiResponse(400, "Invalid URL supplied"), 400)
				}
			} else {
				logger.info("Resource not found.")
				JsonResponse(new value.ApiResponse(404, "Resource not found"), 404)
			}
  }
	
	@ApiOperation(value = "Update an existing Resource", 
			notes = "Updates a resource from indexer backend by URL.", 
			response = classOf[models.Resource], 
			httpMethod = "PUT", 
			nickname = "updateExistingResource")
  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid URL supplied"),
    new ApiResponse(code = 404, message = "Resource not found"),
    new ApiResponse(code = 405, message = "Invalid input")))
  @ApiImplicitParams(Array(
    new ApiImplicitParam(value = "Resource object that needs to be updated identified by URL.", required = true, dataType = "models.Resource", paramType = "body")))
  def updateResource() = DBAction(parse.json) {
    implicit rs =>
			rs.request.body.validate[Resource].map { resource =>
				try {
					logger.info("Updating a resource with url: "+resource.url+".")
					val resourceRequired = resourceData.filter(_.url === resource.url).update(resource)
					if(resourceRequired > 0) {
						logger.info("Resource with url \"" + resource.url + "\" has been updated.")
						Ok(Json.toJson(resource))
					} else {
						logger.info("Resource not found.")
						JsonResponse(new value.ApiResponse(404, "Resource not found"), 404)
					}
				} catch {
					case e: Exception =>
						logger.info("Failed to update existing resource.")
						logger.error("Error updating a resource " + e)
						JsonResponse(new value.ApiResponse(400, "Invalid URL supplied"))
				}
			}.getOrElse(
				{				
					logger.info("Failed to update a resource. Invalid input data.")
					JsonResponse(new value.ApiResponse(405, "Invalid input"))
				}
			)
  }
	
}

object ResourceApiController {}