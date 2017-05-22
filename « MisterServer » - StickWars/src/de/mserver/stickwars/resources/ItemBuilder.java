package de.mserver.stickwars.resources;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;


public class ItemBuilder {
private ItemStack stack;
	
	public ItemBuilder(Material material) {
		this.stack = new ItemStack(material);
	}
	
	public ItemBuilder(Material material, int data) {
		this.stack = new ItemStack(material, 1, (short)data);
	}
	
	public ItemBuilder setAmount(int amount) {
		this.stack.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setDisplayName(String name) {
		ItemMeta meta = this.stack.getItemMeta();
		meta.setDisplayName(name);
		this.stack.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder setLore(String... lore) {
		ItemMeta meta = this.stack.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		this.stack.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder setColor(Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) this.stack.getItemMeta();
		meta.setColor(color);
		this.stack.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder addEnchantment(Enchantment enchantment, int level)
	  {
	    this.stack.addEnchantment(enchantment, level);
	    return this;
	  }
	  
	  public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level)
	  {
	    this.stack.addUnsafeEnchantment(enchantment, level);
	    return this;
	  }
	
	public ItemBuilder hideEnchantments() {
	    ItemMeta meta = this.stack.getItemMeta();
	    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
	    this.stack.setItemMeta(meta);
	    return this;
	}
	
	public ItemBuilder hideAttributes() {
		 ItemMeta meta = this.stack.getItemMeta();
		 meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
		 this.stack.setItemMeta(meta);
		 return this;
	}
	
	public ItemBuilder hidePlaceOn() {
	    ItemMeta meta = this.stack.getItemMeta();
	    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_PLACED_ON });
	    this.stack.setItemMeta(meta);
	    return this;
	}
	
	public static ItemStack SkullBuilder(String displayname, String owner, String... lore) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta)skull.getItemMeta();
		meta.setDisplayName(displayname);
		meta.setOwner(owner);
	    if (lore != null) {
		      meta.setLore(Arrays.asList(lore));
		}
	    skull.setItemMeta(meta);
	    return skull;
	}
	
	public ItemStack getStack() {
		return this.stack;
	}
	
}



