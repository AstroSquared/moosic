package commands;

import commands.lavaplayer.LavaPlayer;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface Command {
    public SlashCommandData data();
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer);
}
