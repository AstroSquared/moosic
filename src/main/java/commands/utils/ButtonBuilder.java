package commands.utils;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class ButtonBuilder {
    public static Button PAUSE = Button.of(ButtonStyle.PRIMARY, "pause", "Pause"); //, Emoji.fromUnicode("⏸")
    public static Button PLAY = Button.of(ButtonStyle.PRIMARY, "play", "Play"); //, Emoji.fromUnicode("▶")
    public static Button SKIP = Button.of(ButtonStyle.PRIMARY, "skip", "Skip"); //, Emoji.fromUnicode("⏩")
    public static Button QUEUE = Button.of(ButtonStyle.PRIMARY, "queue", "View Queue"); //, Emoji.fromUnicode("📜")

    public static Button VOLUME_LOWER = Button.of(ButtonStyle.PRIMARY, "volume_lower", "Lower (20%)");
    public static Button VOLUME_LOW = Button.of(ButtonStyle.PRIMARY, "volume_low", "Low (60%)");
    public static Button VOLUME_NORMAL = Button.of(ButtonStyle.PRIMARY, "volume_normal", "Normal (100%)");
    public static Button VOLUME_HIGH = Button.of(ButtonStyle.PRIMARY, "volume_high", "High (140%)");
    public static Button VOLUME_HIGHER = Button.of(ButtonStyle.PRIMARY, "volume_higher", "Higher (180%)");
}
