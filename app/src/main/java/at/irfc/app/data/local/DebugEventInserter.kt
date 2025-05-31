package at.irfc.app.data.local

import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventLocation
import at.irfc.app.data.local.entity.relations.EventWithDetails
import java.time.LocalDateTime

object DebugEventInserter {
    private const val CACHE_VALIDITY_DURATION_MINUTES = 16 // set test time

    suspend fun insertTestEvent(database: IrfcDatabase) {
        val now = LocalDateTime.now()

        val testEvent = Event(
            id = 9999L,
            title = "🧪 Testevent mit Notification",
            startDateTime = now.plusMinutes(CACHE_VALIDITY_DURATION_MINUTES.toLong()),
            endDateTime = now.plusHours(1),
            description = "Debug-Event für lokale Notification-Tests.",
            categoryId = 999L,
            locationId = 999L,
            isFavorite = false,
            image = Event.Image("Debugbild", "/pfad/debug.jpg"),
            updated = now
        )

        val testCategory = EventCategory(999L, "Debug-Kategorie")
        val testLocation = EventLocation(999L, "Debug-Ort")

        val eventWithDetails = EventWithDetails(
            event = testEvent,
            category = testCategory,
            location = testLocation,
            additionalImages = emptyList()
        )

        database.categoryDao().upsertCategories(listOf(testCategory))
        database.locationDao().upsert(listOf(testLocation))
        database.eventDao().replaceEvent(eventWithDetails)
    }
}
