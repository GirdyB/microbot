package net.runelite.client.plugins.microbot.thieving;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

import static net.runelite.client.plugins.natepainthelper.Info.*;

@PluginDescriptor(
        name = PluginDescriptor.Mocrosoft + "Thieving",
        description = "Microbot thieving plugin",
        tags = {"thieving", "microbot", "skilling"},
        enabledByDefault = false
)
@Slf4j
public class ThievingPlugin extends Plugin {
    @Inject
    private ThievingConfig config;

    @Provides
    ThievingConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ThievingConfig.class);
    }

    @Inject
    private OverlayManager overlayManager;
    @Inject
    private ThievingOverlay thievingOverlay;

    @Inject
    ThievingScript thievingScript;


    @Override
    protected void startUp() throws AWTException {
        expstarted = Microbot.getClient().getSkillExperience(Skill.THIEVING);
        startinglevel = Microbot.getClient().getRealSkillLevel(Skill.THIEVING);
        timeBegan = System.currentTimeMillis();
        if (overlayManager != null) {
            overlayManager.add(thievingOverlay);
        }
        thievingScript.run(config);
    }

    protected void shutDown() {
        thievingScript.shutdown();
        overlayManager.remove(thievingOverlay);
    }
}
