package com.example.tpapimcpserver

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors
import java.util.stream.Stream

@Component
class MyTools(restClientBuilder : RestClient.Builder) {

    private final var restClient: RestClient

    companion object {
        const val THEMEPARK_API_URL = "https://api.themeparks.wiki/v1/"
    }

    init {
        this.restClient = restClientBuilder.baseUrl(THEMEPARK_API_URL).build()
    }

    @Tool(
        name = "getAllParks",
        description = "Get a list of all parks (including their name and entity ID)"
    )
    fun getAllParks(): List<Park> {
        return getParkStream { park -> true }
            .collect(Collectors.toList())
    }

    @Tool(
        name = "getParksByName", description = "Get a list of parks (including their name and " +
                "entity ID) given a park name or resort name"
    )
    fun getParksByName(parkName: String): List<Park> {
        val lcParkName = parkName.lowercase(Locale.getDefault())
        return getParkStream { park ->
            park.name.contains(lcParkName, true)
                    || park.resortName!!.contains(lcParkName, true)
        }
        .collect(Collectors.toList())
    }

    private fun getParkStream(filter: Predicate<Park>): Stream<Park> {
        val destinationList = restClient.get()
            .uri("/destinations")
            .retrieve()
            .body(DestinationList::class.java)

        return destinationList!!.destinations.asList().stream()
            .flatMap { destination: Destination ->
                destination.parks.stream()
                    .map { park ->
                        Park(
                            park.id,
                            stripNonAlphanumeric(park.name),
                            destination.id,
                            stripNonAlphanumeric(destination.name)
                        )
                    }
                    .filter(filter)
            }
    }

    private fun stripNonAlphanumeric(input: String): String {
        return input.replace("[^a-zA-Z0-9\\s-]".toRegex(), "")
    }


    @Tool(name = "getEntity",
          description = "Get an entity given its entity ID")
    fun getEntity(@ToolParam(description = "The entity ID") id: String): Entity? {
        return restClient.get()
            .uri("/entity/$id")
            .retrieve()
            .body(Entity::class.java)
    }

    @Tool(name = "getEntityChildren",
            description = "Get a list of attractions and shows in a park " +
                    "given the park's entity ID")
    fun getEntityChildren(@ToolParam(description = "The entity ID") id: String): EntityParent? {
        return restClient.get()
            .uri("/entity/$id/children")
            .retrieve()
            .body(EntityParent::class.java)
    }

    @Tool(
        name = "getEntityScheduleForDate", description = "Get a park's operating hours given the park's " +
                "entity ID and a specific date (in yyyy-MM-dd format)."
    )
    fun getEntitySchedule(
        entityId: String?, date: String
    ): List<ScheduleEntry> {
        val dateSplit = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() // <1>
        val year = dateSplit[0]
        val month = dateSplit[1]

        val schedule: EntitySchedule? = restClient.get()
            .uri("/entity/{entityId}/schedule/{year}/{month}", entityId, year, month)
            .retrieve()
            .body(EntitySchedule::class.java)

        return schedule!!.schedule.stream()
            .filter { scheduleEntry -> scheduleEntry.date.equals(date) }
            .toList()
    }

    @Tool(
        name = "getEntityLive", description = "Get an attraction's wait times or a show's show " +
                "times given the attraction or show entity ID"
    )
    fun getEntityLive(entityId: String?): EntityLive? {
        return restClient.get()
            .uri("/entity/{entityId}/live", entityId)
            .retrieve()
            .body(EntityLive::class.java)
    }

}