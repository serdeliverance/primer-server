package routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.scaladsl.Source
import com.dixa.model.InvalidParameterError
import com.dixa.route.PrimeNumbersRoute
import com.dixa.service.PrimeNumbersService
import org.mockito.Mockito.when
import org.mockito.MockitoSugar.mock
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PrimeNumbersRouteSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {

  private val primeNumbersService = mock[PrimeNumbersService]
  private val subject             = new PrimeNumbersRoute(primeNumbersService)

  "#PrimeNumbersRoute" should {
    "return prime numbers" in {
      when(primeNumbersService.getPrimeNumbers(3)).thenReturn(Right(Source(List(1, 2, 3))))

      Get("/prime/3") ~> subject.routes ~> check {
        status shouldEqual OK
        responseAs[String] shouldEqual "1, 2, 3, "
      }
    }

    "return bad request" in {

      when(primeNumbersService.getPrimeNumbers(0)).thenReturn(Left(InvalidParameterError("prime", "0")))

      Get("/prime/0") ~> subject.routes ~> check {
        status shouldEqual BadRequest
        responseAs[String] shouldEqual "{\"message\":\"Invalid parameter prime=0\"}"
      }
    }
  }
}
