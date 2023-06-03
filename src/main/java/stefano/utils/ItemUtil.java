package stefano.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemUtil {
    public static ItemStack setItemMeta(String displayName, Material material){
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setCustomPotionMeta(PotionEffectType potionEffectType, Material material, String potionName){
        ItemStack itemStack = new ItemStack(material, 1);
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        potionMeta.setColor(Color.WHITE);
        potionMeta.addCustomEffect(new PotionEffect(potionEffectType, 200, 0), true);
        potionMeta.setDisplayName(potionName);
        itemStack.setItemMeta(potionMeta);
        return itemStack;

    }
}
