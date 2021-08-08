package me.sstorey.Challenges;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Challenges Enabled");
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Challenges Disabled");
    }

    public ItemStack ice() {
        ItemStack ice = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta icemeta = ice.getItemMeta();
        icemeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Ice Breaker");
        icemeta.setUnbreakable(true);
        icemeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        ice.setItemMeta(icemeta);
        return ice;
    }

    ArrayList<Player> cooldown = new ArrayList<Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("challenges")) {
                p.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-");
                p.sendMessage(ChatColor.GREEN + "Challenges Menu");
                p.sendMessage(ChatColor.RED + "/ice -> Ice Challenge");
                p.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-");
            }
            if (cmd.getName().equalsIgnoreCase("ice")) {
                if(cooldown.contains(p)){
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You already have the pickaxe!");
                } else if (!(p.getInventory().contains(ice()))) {
                    p.getInventory().addItem(ice());
                    cooldown.add(p);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                        public void run() {
                            cooldown.remove(p);
                        }
                    }, 51840000);
                } else {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You already have the pickaxe!");
                }
            }
        }
        return true;
    }
}