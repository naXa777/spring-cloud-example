package by.naxa.learning.datetimeapp.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DatetimeService(@Autowired val restTemplate: RestTemplate) {

    @HystrixCommand(fallbackMethod = "unknown")
    fun datetime(): String? = restTemplate.getForEntity("http://datetime-service/datetime", String::class.java).body

    @Suppress("unused")
    fun unknown(): String = "unknown"

}
