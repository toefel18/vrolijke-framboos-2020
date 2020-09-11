package nl.toefel.framboos

import nl.toefel.framboos.dto.Scoreboard
import nl.toefel.framboos.dto.SolutionRequest
import nl.toefel.framboos.dto.SolutionResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import java.io.IOException
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Base64

enum class API {
    PUBLIC,
    PRIVATE
}

class Client(val okhttp: OkHttpClient = OkHttpClient()) {
    val log = LoggerFactory.getLogger(Client::class.java)
    var authorization: String? = null;

    fun configureUser(username: String, password: String) {
        authorization = Base64.getEncoder().encodeToString("$username:$password".toByteArray())
    }

    fun getScoreboard(api: API): Scoreboard {
        val boardType = if (api == API.PRIVATE) "private" else "public"
        val req = Request.Builder()
        .url("https://www.framboos.ga/$boardType/scoreboard")
        .addHeader("accept", "application/json")
        .addHeader("authorization", "basic $authorization")
        .addHeader("content-Type", "application/json")
        .get()
        .build()

        val res = okhttp.newCall(req).execute()
        log.info("Call to ${req.url} was ${res.code}")
        val responseBody = res.body?.string() ?: throw IOException("server returned empty body on call to scoreboard")
        log.info("ResponseBody was $responseBody")

        if (!res.isSuccessful) {
            throw IOException("Invalid status code ${res.code}")
        }
        return Jsonizer.fromJson(responseBody)
    }

    fun setEndTime(api: API, time: ZonedDateTime) {
        val boardType = if (api == API.PRIVATE) "private" else "public"
        val reqBody = """"${time.withZoneSameInstant(ZoneOffset.UTC)}""""
        val req = Request.Builder()
            .url("https://www.framboos.ga/$boardType/endtime")
            .addHeader("accept", "application/json")
            .addHeader("authorization", "basic $authorization")
            .put(reqBody.toRequestBody("application/json".toMediaType()))
            .build()

        log.info("requestBody = $reqBody")
        val res = okhttp.newCall(req).execute()
        log.info("Call to ${req.url} was ${res.code}")
        val responseBody = res.body?.string() ?: throw IOException("server returned empty body on call to scoreboard")
        log.info("ResponseBody was $responseBody")

        if (!res.isSuccessful) {
            throw IOException("Invalid status code ${res.code}")
        }
    }

    fun reset(api: API) {
        val boardType = if (api == API.PRIVATE) "private" else "public"
        val req = Request.Builder()
            .url("https://www.framboos.ga/$boardType/reset")
            .addHeader("authorization", "basic $authorization")
            .post("".toRequestBody())
            .build()

        val res = okhttp.newCall(req).execute()
        log.info("Call to ${req.url} was ${res.code}")

        if (!res.isSuccessful) {
            throw IOException("Invalid status code ${res.code}")
        }
    }

    fun postSolution(api: API, requestBody: SolutionRequest): SolutionResponse {
        val boardType = if (api == API.PRIVATE) "private" else "public"
        val reqBody = Jsonizer.toJson(requestBody)
        val req = Request.Builder()
            .url("https://www.framboos.ga/$boardType/solution")
            .addHeader("accept", "application/json")
            .addHeader("authorization", "basic $authorization")
            .post(reqBody.toRequestBody("application/json".toMediaType()))
            .build()

        log.info("requestBody = $reqBody")
        val res = okhttp.newCall(req).execute()
        log.info("Call to ${req.url} was ${res.code}")
        val responseBody = res.body?.string() ?: throw IOException("server returned empty body on call to scoreboard")
        log.info("ResponseBody was $responseBody")

        if (!res.isSuccessful) {
            throw IOException("Invalid status code ${res.code}")
        }
        return Jsonizer.fromJson(responseBody)
    }
}