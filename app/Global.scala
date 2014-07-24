import play.api._
import com.wordnik.swagger.config._
import com.wordnik.swagger.model._
import play.api.mvc.EssentialAction
import play.api.mvc.WithFilters
import play.filters.gzip.GzipFilter

object Global extends WithFilters(CorsFilter, new GzipFilter()) {
	// General info about app API
	val info = ApiInfo(
		title = "SIB Colombia Indexer",
		description = """Indexer backend server. You can find out more about SIB Colombia 
    at <a href="http://www.sibcolombia.net">http://www.sibcolombia.net</a> or twitter @sibcolombia this version doesnt't need an API or special security key.""",
		termsOfServiceUrl = "http://data.sibcolombia.net/terms.htm",
		contact = "sib@humboldt.org.co",
		license = "MIT",
		licenseUrl = "http://opensource.org/licenses/MIT")

	ConfigFactory.config.info = Some(info)
}