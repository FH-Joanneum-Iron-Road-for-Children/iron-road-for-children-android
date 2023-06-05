package at.irfc.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class UserVotingDto(
    val votingId: Long,
    val eventId: Long,
    val deviceId: String
)
