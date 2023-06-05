package at.irfc.app.data.remote.api

import at.irfc.app.data.remote.dto.UserVotingDto
import at.irfc.app.data.remote.dto.VotingDto
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface VotingApi {
    @GET("votings")
    suspend fun getVotings(): List<VotingDto>

    @GET("votings/{votingId}")
    suspend fun getVoting(@Path("votingId") id: Long): VotingDto?

    @POST("votings")
    suspend fun submitUserVoting(@Body voting: UserVotingDto)
}
