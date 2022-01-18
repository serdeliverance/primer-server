package routes

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.dixa.routes.PrimeNumbersRoute.primeNumbersRoute
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PrimeNumbersRouteSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {

  "#PrimeNumbersRoute" should {
    "return prime numbers" in {
      Get("/prime/7") ~> primeNumbersRoute ~> {
        status mustBe OK
      }
    }

    "return bad request" in {
      Get("/prime/-1") ~> primeNumbersRoute ~> {
        status mustBe BadRequest
      }
    }
  }
}
