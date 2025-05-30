package com.tometrics.api.functional

import com.tometrics.api.domain.models.LocationInfo
import com.tometrics.api.routes.models.GetGeolocationAutocompleteResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class GeolocationE2ETest : BaseE2ETest() {

    @Test
    fun `test geolocation autocomplete endpoint`() = runApp {
        val (accessToken, _) = registerAnon()

        val response = jsonClient.get("/api/v1/geolocation/autocomplete?searchQuery=london") {
            bearerAuth(accessToken)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val autocompleteResponse = response.body<GetGeolocationAutocompleteResponse>()
        assertNotEquals(0, autocompleteResponse.locations.size)

        val first = assertNotNull(autocompleteResponse.locations.firstOrNull())
        assertEquals("London", first.city)
        assertEquals("United Kingdom", first.country)
    }

    @Test
    fun `test geolocation reverse-geocoding endpoint`() = runApp {
        val (accessToken, _) = registerAnon()

        val response = jsonClient.get("/api/v1/geolocation/reverse-geocoding?lat=51.5074&lon=-0.1278") {
            bearerAuth(accessToken)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val first = response.body<LocationInfo>()
        assertEquals("City of Westminster", first.city)
        assertEquals("United Kingdom", first.country)

        val missingLatResponse = jsonClient.get("/api/v1/geolocation/reverse-geocoding") {
            bearerAuth(accessToken)
        }
        assertEquals(HttpStatusCode.BadRequest, missingLatResponse.status)

        val missingLonResponse = jsonClient.get("/api/v1/geolocation/reverse-geocoding?lat=51.5074") {
            bearerAuth(accessToken)
        }
        assertEquals(HttpStatusCode.BadRequest, missingLonResponse.status)
    }

}
