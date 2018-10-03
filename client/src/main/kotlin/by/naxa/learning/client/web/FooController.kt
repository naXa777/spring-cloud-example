package by.naxa.learning.client.web

import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FooController @Autowired
constructor(private val client: EurekaClient, private val restTemplateBuilder: RestTemplateBuilder) {

    @GetMapping
    fun callService(): String? {
        val restTemplate = restTemplateBuilder.build()
        val service = client.getNextServerFromEureka("weather-service", false)
        val baseUrl = service.homePageUrl
        val response = restTemplate.getForEntity(baseUrl, String::class.java)

        return response.body
    }

}
