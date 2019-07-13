package com.terra

import com.terra.model.Place
import com.terra.repository.PlaceRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`in`
import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class TerraApplicationTests {

    @Autowired
    lateinit var placeRepository: PlaceRepository



	@Test
	fun contextLoads() {
	}

    @Test
    fun savePlace() {
        val place = Place(name = "firstPlace", lng = 2.4123, lat = 41.3987)

        placeRepository.save(place)

        val allInRadius = placeRepository.findAllByLatBetweenAndLngBetween(30.0, 31.0, 59.0, 60.0)

        println(allInRadius)
        assertThat(place, `is`(`in`(allInRadius)))

        placeRepository.deleteAll()
    }

}
