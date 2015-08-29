import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class ConnectionSpec extends Specification {

  "Connection" should {
	"send through websocket" in new WithBrowser{
	browser.goTo(s"ws://localhost:$port/ws/o/b/kyulobby/1")
	browser.pageSource must contain("kyulobby")
	}
	
  }
}