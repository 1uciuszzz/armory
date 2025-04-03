package org.bigben.armory;

import org.bigben.armory.axes.Ben002;
import org.bigben.armory.axes.Ben002Listener;
import org.bigben.armory.axes.TreeFellerAxe;
import org.bigben.armory.bow.Ben003;
import org.bigben.armory.bow.Ben003Listener;
import org.bigben.armory.bow.LightningBow;
import org.bigben.armory.foods.Ben004;
import org.bigben.armory.foods.Ben004Listener;
import org.bigben.armory.foods.Ben007;
import org.bigben.armory.foods.Ben007Listener;
import org.bigben.armory.hoes.Ben006;
import org.bigben.armory.hoes.Ben006Listener;
import org.bigben.armory.swords.Ben001;
import org.bigben.armory.swords.Ben001Listener;
import org.bigben.armory.swords.Ben005;
import org.bigben.armory.swords.Ben005Listener;
import org.bigben.armory.tools.Ben008;
import org.bigben.armory.tools.LuckyShovel;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Armory extends JavaPlugin {
  private Ben001 ben001;
  private Ben002 ben002;
  private Ben003 ben003;
  private Ben004 ben004;
  private Ben005 ben005;
  private Ben006 ben006;
  private Ben007 ben007;
  private Ben008 ben008;
  private TreeFellerAxe treeFellerAxe;
  private LightningBow lightningBow;
  private LuckyShovel luckyShovel;

  @Override
  public void onEnable() {
    ben001 = new Ben001(this);
    ben002 = new Ben002(this);
    ben003 = new Ben003(this);
    ben004 = new Ben004(this);
    ben005 = new Ben005(this);
    ben006 = new Ben006(this);
    ben007 = new Ben007(this);
    ben008 = new Ben008(this);
    treeFellerAxe = new TreeFellerAxe(this);
    lightningBow = new LightningBow(this);
    luckyShovel = new LuckyShovel(this);

    registerRecipes();

    registerListeners();
    // log
    getLogger().info("[Armory] 已注册");
  }

  private void registerRecipes() {
    // 获取配方
    ShapedRecipe recipeBen001 = ben001.registerShape();
    ShapedRecipe recipeBen002 = ben002.registerShape();
    ShapedRecipe recipeBen003 = ben003.registerShape();
    ShapedRecipe recipeBen004 = ben004.registerShape();
    ShapedRecipe recipeBen005 = ben005.registerShape();
    ShapedRecipe recipeBen006 = ben006.registerShape();
    ShapedRecipe recipeBen007 = ben007.registerShape();
    ShapedRecipe recipeBen008 = ben008.registerShape();
    ShapedRecipe recipeTreeFellerAxe = treeFellerAxe.registerRecipe();
    ShapedRecipe recipeLightningBow = lightningBow.registerRecipe();
    ShapedRecipe registerLuckShovel = luckyShovel.registerRecipe();

    // 添加配方
    Bukkit.addRecipe(recipeBen001);
    Bukkit.addRecipe(recipeBen002);
    Bukkit.addRecipe(recipeBen003);
    Bukkit.addRecipe(recipeBen004);
    Bukkit.addRecipe(recipeBen005);
    Bukkit.addRecipe(recipeBen006);
    Bukkit.addRecipe(recipeBen007);
    Bukkit.addRecipe(recipeBen008);
    Bukkit.addRecipe(recipeTreeFellerAxe);
    Bukkit.addRecipe(recipeLightningBow);
    Bukkit.addRecipe(registerLuckShovel);

    getLogger().info("已成功注册合成配方！");
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new Ben001Listener(this), this);
    getServer().getPluginManager().registerEvents(new Ben002Listener(this), this);
    getServer().getPluginManager().registerEvents(new Ben003Listener(this), this);
    getServer().getPluginManager().registerEvents(new Ben004Listener(this), this);
    getServer().getPluginManager().registerEvents(new Ben005Listener(this), this);
    getServer().getPluginManager().registerEvents(new Ben006Listener(this), this);
    getServer().getPluginManager().registerEvents(new Ben007Listener(this), this);
    getServer().getPluginManager().registerEvents(treeFellerAxe, this);
    getServer().getPluginManager().registerEvents(lightningBow, this);
    getServer().getPluginManager().registerEvents(luckyShovel, this);
    getLogger().info("事件监听器已注册！");
  }

  @Override
  public void onDisable() {
    getLogger().info("[Armory] has been disabled!");
  }
}
