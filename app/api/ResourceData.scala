package api

import models.Resource
import play.api.db.slick.Config.driver.simple._

/**
 * Table mapping for resource data
 */
class ResourceData(tag: Tag) extends Table[Resource](tag, "RESOURCE") {
	def url = column[String]("URL", O.PrimaryKey)
	def name = column[Option[String]]("NAME", O.Nullable)
	def user = column[Option[String]]("USER", O.Nullable)
	def organizationUUID = column[Option[String]]("ORGANIZATIONUUID", O.Nullable)
	def country = column[Option[String]]("COUNTRY", O.Nullable)
	def status = column[Option[String]]("STATUS", O.Nullable)
	def statusNotes = column[Option[String]]("STATUSNOTES", O.Nullable)
	def createdAt = column[Option[String]]("CREATEDAT", O.Nullable)
	def indexStartAt = column[Option[String]]("INDEXSTARTAT", O.Nullable)
	def indexedAt = column[Option[String]]("INDEXEDAT", O.Nullable)
	
	def * = (url, name, user, organizationUUID, country, status, statusNotes, createdAt, indexStartAt, indexedAt) <> (Resource.tupled, Resource.unapply)
}