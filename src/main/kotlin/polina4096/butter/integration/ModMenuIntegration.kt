package polina4096.butter.integration

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import polina4096.butter.generateConfigScreen

@Environment(EnvType.CLIENT)
class ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory()
        = ConfigScreenFactory { generateConfigScreen(it) }
}