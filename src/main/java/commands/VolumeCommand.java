package commands;

import commands.lavaplayer.GuildMusicManager;
import commands.lavaplayer.LavaPlayer;
import commands.utils.ButtonBuilder;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.Color;

public class VolumeCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("volume", "Change the volume.");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        if (event.getGuild().getAudioManager().getConnectedChannel() == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("I am not in a voice channel! Use /join before using music commands!")).queue();
            return;
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Adjust Volume");
        embed.setDescription("Click the buttons to change the volume");
        embed.setColor(Color.ORANGE);
        event.replyEmbeds(embed.build()).addActionRow(ButtonBuilder.VOLUME_LOWER, ButtonBuilder.VOLUME_LOW,
                ButtonBuilder.VOLUME_NORMAL, ButtonBuilder.VOLUME_HIGH, ButtonBuilder.VOLUME_HIGHER).queue();
    }
}
