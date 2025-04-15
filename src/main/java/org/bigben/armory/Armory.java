package org.bigben.armory;

import org.bigben.armory.axes.TreeFellerAxe;
import org.bigben.armory.bow.LightningBow;
import org.bigben.armory.foods.GoldenChip;
import org.bigben.armory.hoes.GoldenScythe;
import org.bigben.armory.swords.LuckySword;
import org.bigben.armory.tools.LuckyPickaxe;
import org.bigben.armory.items.LightningScope;
import org.bigben.armory.tools.LuckyShovel;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Armory extends JavaPlugin {
  private GoldenScythe goldenScythe;
  private GoldenChip goldenChip;
  private LuckySword luckySword;
  private LuckyPickaxe luckyPickaxe;
  private TreeFellerAxe treeFellerAxe;
  private LightningBow lightningBow;
  private LuckyShovel luckyShovel;
  private LightningScope lightningScope;

  @Override
  public void onEnable() {
    goldenChip = new GoldenChip(this);
    luckyPickaxe = new LuckyPickaxe(this);
    treeFellerAxe = new TreeFellerAxe(this);
    lightningBow = new LightningBow(this);
    luckyShovel = new LuckyShovel(this);
    goldenScythe = new GoldenScythe(this);
    lightningScope = new LightningScope(this);
    luckySword = new LuckySword(this);

    registerRecipes();

    registerListeners();
    // log
    getLogger().info("[Armory] 已注册");
  }

  private void registerRecipes() {
    // 获取配方
    ShapedRecipe recipeGoldenChip = goldenChip.registerRecipe();
    ShapedRecipe recipeLuckyPickaxe = luckyPickaxe.registerShape();
    ShapedRecipe recipeTreeFellerAxe = treeFellerAxe.registerRecipe();
    ShapedRecipe recipeLightningBow = lightningBow.registerRecipe();
    ShapedRecipe registerLuckShovel = luckyShovel.registerRecipe();
    ShapedRecipe registerLuckSword = luckySword.registerRecipe();
    ShapedRecipe registerGoldenScythe = goldenScythe.registerRecipe();
    ShapedRecipe registerLightningScope = lightningScope.registerRecipe();

    // 添加配方
    Bukkit.addRecipe(recipeGoldenChip);
    Bukkit.addRecipe(recipeLuckyPickaxe);
    Bukkit.addRecipe(recipeTreeFellerAxe);
    Bukkit.addRecipe(recipeLightningBow);
    Bukkit.addRecipe(registerLuckShovel);
    Bukkit.addRecipe(registerGoldenScythe);
    Bukkit.addRecipe(registerLightningScope);
    Bukkit.addRecipe(registerLuckSword);

    getLogger().info("已成功注册合成配方！");
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(goldenChip, this);
    getServer().getPluginManager().registerEvents(treeFellerAxe, this);
    getServer().getPluginManager().registerEvents(lightningBow, this);
    getServer().getPluginManager().registerEvents(luckyShovel, this);
    getServer().getPluginManager().registerEvents(luckySword, this);
    getServer().getPluginManager().registerEvents(lightningScope, this);
    getServer().getPluginManager().registerEvents(goldenScythe, this);
    getLogger().info("事件监听器已注册！");
  }

  @Override
  public void onDisable() {
    getLogger().info("[Armory] has been disabled!");
  }
}
