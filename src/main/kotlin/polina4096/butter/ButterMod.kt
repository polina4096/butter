package polina4096.butter

import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory

object ButterMod : ModInitializer {
    const val ID   = "butter"
    const val NAME = "butter"

    private val logger = LoggerFactory.getLogger("butter")

    lateinit var config: ButterConfig

	override fun onInitialize() {
        val configDir = FabricLoader.getInstance().configDir
        config = ButterConfig.load(configDir.resolve("$ID.json"))

    }
}