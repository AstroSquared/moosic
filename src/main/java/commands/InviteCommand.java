package commands;

import commands.lavaplayer.LavaPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.Color;

public class InviteCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("invite", "Invite the bot to your server");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Invite Moosic");
        String invite = "https://discord.com/api/oauth2/authorize?client_id=886749429258924042&permissions=7728192&scope=bot%20applications.commands";
        embed.setDescription(
                "[Click to add moosic to your server!](" + invite + ")");
        embed.setColor(Color.ORANGE);
        event.replyEmbeds(embed.build()).queue();
    }
}
