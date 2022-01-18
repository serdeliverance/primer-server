package routes

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.scaladsl.Source
import com.dixa.model.InvalidParameterError
import com.dixa.route.PrimeNumbersRoute
import com.dixa.service.PrimeNumbersService
import org.mockito.Mockito.when
import org.mockito.MockitoSugar.mock
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PrimeNumbersRouteSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {

  private val primeNumbersService = mock[PrimeNumbersService]
  private val subject             = new PrimeNumbersRoute(primeNumbersService)

  "#PrimeNumbersRoute" should {
    "return prime numbers" in {

      // FIXME
//      when(primeNumbersService.getPrimeNumbers(7)).thenReturn(Source(List(1, 2, 3, 5, 7)))

      // FIXME
      Get("/prime/7") ~> subject.routes ~> {
        status mustBe OK
      }
    }

    // FIXME
    "return bad request" in {

      when(primeNumbersService.getPrimeNumbers(-1)).thenReturn(Left(InvalidParameterError("number", "-1")))

      Get("/prime/-1") ~> subject.routes ~> {
        status mustBe BadRequest
      }
    }
  }
}
