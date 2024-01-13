import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
    public Main() throws Exception {
        JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .setActivity(Activity.competing("hell"))
                .setStatus(OnlineStatus.ONLINE)
                .enableCache(CacheFlag.VOICE_STATE)
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                .build();
        jda.awaitReady();
        jda.addEventListener(new Commands(jda));
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
