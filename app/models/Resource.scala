package models

import com.wordnik.swagger.annotations._
import scala.annotation.meta.field
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

/**
 * Data model for resource data for indexing
 */
@ApiModel("Resource")
case class Resource(
	@(ApiModelProperty @field)(position=1, required=true, value="unique identifier for the resource")url: String,
	@(ApiModelProperty @field)(position=2, value="name of the resource for indexing")name: Option[String],
	@(ApiModelProperty @field)(position=3, required=true, value="user who includes resource for indexing")user: Option[String],
	@(ApiModelProperty @field)(position=4, value="organization unique identifier")organizationUUID: Option[String],
	@(ApiModelProperty @field)(position=5, value="country to index")country: Option[String],
	@(ApiModelProperty @field)(position=6, allowableValues="not indexed, indexing, indexed, failed")status: Option[String],
	@(ApiModelProperty @field)(position=7, value="details about indexing status of the resource")statusNotes: Option[String],
	@(ApiModelProperty @field)(position=8, value="date of saving in the indexer application")createdAt: Option[String],
	@(ApiModelProperty @field)(position=9, value="start date of the last indexing process")indexStartAt: Option[String],
	@(ApiModelProperty @field)(position=10, value="end date of the last indexing process")indexedAt: Option[String]
)

object Resource extends Function10[String, Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Option[String], Resource] {
	implicit val resourceReads: Reads[Resource] = (
		(__ \ "url").read(minLength[String](1)) and
		(__ \ "name").readNullable[String] and
		(__ \ "user").readNullable[String] and
		(__ \ "organizationUUID").readNullable[String] and
		(__ \ "country").readNullable[String] and
		(__ \ "status").readNullable[String] and
		(__ \ "statusNotes").readNullable[String] and
		(__ \ "createdAt").readNullable[String].map{ value => Some(new DateTime().toString()).asInstanceOf[Option[String]] } and
		(__ \ "indexStartAt").readNullable[String].map{ value => Some(null).asInstanceOf[Option[String]] } and
		(__ \ "indexedAt").readNullable[String].map{ value => Some(null).asInstanceOf[Option[String]] }
	)(Resource.apply _)
	
	implicit val resourceWrites: Writes[Resource] = (
		(__ \ "url").write[String] and
		(__ \ "name").write[Option[String]] and
		(__ \ "user").write[Option[String]] and
		(__ \ "organizationUUID").write[Option[String]] and
		(__ \ "country").write[Option[String]] and
		(__ \ "status").write[Option[String]] and
		(__ \ "statusNotes").write[Option[String]] and
		(__ \ "createdAt").write[Option[String]] and
		(__ \ "indexStartAt").write[Option[String]] and
		(__ \ "indexedAt").write[Option[String]]
	)(unlift(Resource.unapply))
}