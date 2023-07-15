package polina4096.butter

import kotlinx.serialization.*
import kotlinx.serialization.Transient
import kotlinx.serialization.json.*
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.writeText

private val json = Json { prettyPrint = true }

@Serializable
class ButterConfig(@Transient var path: Path? = null) {
    var butterEnabled : Boolean = DEFAULT_BUTTER_ENABLED
    var glideSpeed    : Double  = DEFAULT_GLIDE_SPEED

    fun save() {
        path!!.writeText(json.encodeToString(this))
    }

    companion object {
        const val DEFAULT_BUTTER_ENABLED = true
        const val DEFAULT_GLIDE_SPEED    = 0.25

        fun load(path: Path): ButterConfig {
            if (!path.exists()) return ButterConfig(path)

            val inputStream = Files.newInputStream(path)
            val inputString = inputStream.bufferedReader().use { it.readText() }
            return Json.decodeFromString<ButterConfig>(inputString).also { it.path = path }
        }
    }
}