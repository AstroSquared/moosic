package commands;

import commands.lavaplayer.LavaPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.Color;

public class HelpCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("help", "Get the bot's commands and information");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Help Menu");
        embed.setColor(Color.ORANGE);
        embed.addField(new MessageEmbed.Field(
                "Play songs",
                "`/join`, `/leave`, `/play [name or url]`",
                false
        ));
        embed.addField(new MessageEmbed.Field(
                "Player Controls",
                "`/skip`, `/pause [paused: true/false]`, `/volume`",
                false
        ));
        embed.addField(new MessageEmbed.Field(
                "Queue",
                "`/nowplaying`, `/queue`, `/clear`, `/skip`, `/mode [mode]`, `/stop`",
                false
        ));
        embed.addField(new MessageEmbed.Field(
                "Misc",
                "`/help`, `/invite`",
                false
        ));

        event.replyEmbeds(embed.build()).queue();
    }
}
