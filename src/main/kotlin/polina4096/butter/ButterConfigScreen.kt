package polina4096.butter

import dev.isxander.yacl3.api.*
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder
import dev.isxander.yacl3.api.controller.DoubleFieldControllerBuilder
import net.minecraft.text.Text
import net.minecraft.client.gui.screen.Screen

fun generateConfigScreen(parent: Screen?): Screen = YetAnotherConfigLib.createBuilder()
    .save(ButterMod.config::save)
    .title(Text.of(ButterMod.NAME))
    .category(
        ConfigCategory.createBuilder()
            .name(Text.of("butter"))
            .group(
                OptionGroup.createBuilder()
                    .name(Text.of("General"))
                    .collapsed(false)
                    .option(
                        Option.createBuilder<Boolean>()
                            .name(Text.of("Indicator animation"))
                            .description(OptionDescription.of(Text.of("Enables/disables selected hotbar slot indicator animation")))
                            .binding(ButterConfig.DEFAULT_BUTTER_ENABLED,
                                { ButterMod.config.butterEnabled },
                                { ButterMod.config.butterEnabled = it })
                            .controller(BooleanControllerBuilder::create)
                            .build())

                    .option(
                        Option.createBuilder<Double>()
                            .name(Text.of("Animation speed"))
                            .description(OptionDescription.of(Text.of("Determines the speed of the slot indicator animation")))
                            .binding(ButterConfig.DEFAULT_GLIDE_SPEED,
                                { ButterMod.config.glideSpeed },
                                { ButterMod.config.glideSpeed = it })
                            .controller(DoubleFieldControllerBuilder::create)
                            .build())
                    .build())
            .build())
    .build()
    .generateScreen(parent)