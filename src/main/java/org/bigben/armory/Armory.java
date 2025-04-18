package org.bigben.armory;

import org.bigben.armory.axes.TreeFellerAxe;
import org.bigben.armory.bow.LightningBow;
import org.bigben.armory.foods.GoldenChip;
import org.bigben.armory.hoes.GoldenScythe;
import org.bigben.armory.swords.LuckySword;
import org.bigben.armory.swords.UndeadSlayerSword;
import org.bigben.armory.tools.AquaVisionHelmet;
import org.bigben.armory.tools.BlazeWand;
import org.bigben.armory.tools.LuckyPickaxe;
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
  private BlazeWand blazeWand;
  private AquaVisionHelmet aquaVisionHelmet;
  private UndeadSlayerSword undeadSlayerSword;

  @Override
  public void onEnable() {
    goldenChip = new GoldenChip(this);
    luckyPickaxe = new LuckyPickaxe(this);
    treeFellerAxe = new TreeFellerAxe(this);
    lightningBow = new LightningBow(this);
    luckyShovel = new LuckyShovel(this);
    goldenScythe = new GoldenScythe(this);
    luckySword = new LuckySword(this);
    blazeWand = new BlazeWand(this);
    aquaVisionHelmet = new AquaVisionHelmet(this);
    undeadSlayerSword = new UndeadSlayerSword(this);

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
    ShapedRecipe registerBlazeWand = blazeWand.registerRecipe();
    ShapedRecipe registerAquaVisionHelmet = aquaVisionHelmet.registerRecipe();
    ShapedRecipe registerUndeadSlayerSword = undeadSlayerSword.registerRecipe();

    // 添加配方
    Bukkit.addRecipe(recipeGoldenChip);
    Bukkit.addRecipe(recipeLuckyPickaxe);
    Bukkit.addRecipe(recipeTreeFellerAxe);
    Bukkit.addRecipe(recipeLightningBow);
    Bukkit.addRecipe(registerLuckShovel);
    Bukkit.addRecipe(registerGoldenScythe);
    Bukkit.addRecipe(registerLuckSword);
    Bukkit.addRecipe(registerBlazeWand);
    Bukkit.addRecipe(registerAquaVisionHelmet);
    Bukkit.addRecipe(registerUndeadSlayerSword);

    getLogger().info("已成功注册合成配方！");
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(goldenChip, this);
    getServer().getPluginManager().registerEvents(treeFellerAxe, this);
    getServer().getPluginManager().registerEvents(lightningBow, this);
    getServer().getPluginManager().registerEvents(luckyShovel, this);
    getServer().getPluginManager().registerEvents(luckySword, this);
    getServer().getPluginManager().registerEvents(goldenScythe, this);
    getServer().getPluginManager().registerEvents(blazeWand, this);
    getServer().getPluginManager().registerEvents(luckyPickaxe, this);
    getServer().getPluginManager().registerEvents(aquaVisionHelmet, this);
    getServer().getPluginManager().registerEvents(undeadSlayerSword, this);
    getLogger().info("事件监听器已注册！");
  }

  @Override
  public void onDisable() {
    getLogger().info("[Armory] has been disabled!");
  }
}
