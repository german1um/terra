package com.terra

import com.terra.model.Place
import com.terra.repository.PlaceRepository
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
        val place = Place(name = "firstPlace", lng = 30.4123, lat = 59.3987)

        placeRepository.save(place)

        val savedPlace = placeRepository.findById(place.id)

        assert(savedPlace.isPresent)

        placeRepository.deleteAll()
    }

}
