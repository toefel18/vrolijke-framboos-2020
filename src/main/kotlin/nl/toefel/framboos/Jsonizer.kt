package nl.toefel.framboos

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue

object Jsonizer {

    val objectMapper: ObjectMapper = ObjectMapper()
        .findAndRegisterModules()
        .registerModule(KotlinModule())
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)

    fun toJson(obj: Any): String = objectMapper.writeValueAsString(obj)
    fun toJsonFormatted(obj: Any): String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
    inline fun <reified T> fromJson(json: String): T = objectMapper.readValue(json)
    inline fun <reified T> fromJson(json: JsonNode): T = objectMapper.treeToValue(json, T::class.java)
    fun format(jsonString: String): String = toJsonFormatted(objectMapper.readValue(jsonString, Object::class.java))
}
