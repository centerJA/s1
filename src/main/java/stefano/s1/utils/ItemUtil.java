package stefano.s1.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtil {
    public static ItemStack setItemMeta(String displayName, Material material){
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setCustomPotionMeta(PotionEffectType potionEffectType, Material material, String potionName){
        ItemStack itemStack = new ItemStack(material, 1);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        if (potionMeta == null) return null;
        potionMeta.setColor(Color.WHITE);
        potionMeta.addCustomEffect(new PotionEffect(potionEffectType, 200, 0), true);
        potionMeta.setDisplayName(potionName);
        itemStack.setItemMeta(potionMeta);
        return itemStack;

    }

    public static int canBlockPlace(Player player) {
        int answer = 0;
        if (player.getName().equals("markcs11") || player.getName().equals("InfInc")) {
            return answer;
        } else {
            answer = 1;
            return answer;
        }
    }
}
