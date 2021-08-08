package me.scb.pissaddon.pissaddon.CMDS;

    import com.projectkorra.projectkorra.command.PKCommand;
    import me.scb.pissaddon.pissaddon.Config.Config;
    import me.scb.pissaddon.pissaddon.Pissaddon;
    import org.bukkit.ChatColor;
    import org.bukkit.command.CommandSender;


    import java.util.List;

    public class cmds extends PKCommand {
        public cmds() {
            super("PissAddon", "/bending PissAddon <reload>", "Reloads config", new String[]{ "PissAddon" });
        }

        @Override
        public void execute(CommandSender sender, List<String> args) {
            if (!hasPermission(sender) || !correctLength(sender, args.size(), 0, 1)) return;
            if (args.size() == 0) {
                sender.sendMessage(ChatColor.YELLOW + "PissAddon Version: " + ChatColor.GOLD + Pissaddon.getVersion());
            } else if (args.size() == 1) {
                if (args.get(0).equals("reload") && hasPermission(sender, "reload")) {
                    Pissaddon.getPlugin().reloadConfig();
                    Config.ConfigPath.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "PissAddon config has been reloaded.");
                }
            }
        }
    }
