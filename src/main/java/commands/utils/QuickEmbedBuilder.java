package commands.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

public class QuickEmbedBuilder {
    public static EmbedBuilder embed = new EmbedBuilder();

    public static MessageEmbed generateErrorEmbed(String desc) {
        embed.setTitle("Error");
        embed.setDescription(desc);
        embed.setColor(Color.RED);
        return embed.build();
    }

    public static MessageEmbed generateSuccessEmbed(String desc) {
        embed.setTitle("Success!");
        embed.setDescription(desc);
        embed.setColor(Color.ORANGE);
        return embed.build();
    }

    public static MessageEmbed generateQueueActionEmbed(String desc) {
        embed.setTitle(":musical_note: **Queue Action**");
        embed.setColor(Color.ORANGE);
        embed.setDescription(desc);
        return embed.build();
    }
}
