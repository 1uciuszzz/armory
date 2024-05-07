package org.bigben.armory;

import org.bigben.armory.axes.ModelH;
import org.bigben.armory.axes.ModelHListener;
import org.bigben.armory.boots.ModelB;
import org.bigben.armory.chestplates.ModelE;
import org.bigben.armory.helmets.ModelD;
import org.bigben.armory.leegings.ModelF;
import org.bigben.armory.swords.ModelA;
import org.bigben.armory.swords.ModelAListener;
import org.bigben.armory.swords.ModelG;
import org.bigben.armory.tools.ModelC;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Armory extends JavaPlugin {
  @Override
  public void onEnable() {
    // Model A
    ModelA modelA = new ModelA();
    NamespacedKey modelARecipeKey = new NamespacedKey(this, "bigben_armory_model_a");
    getServer().addRecipe(modelA.registerShape(modelARecipeKey, modelA.load()));
    getServer().getPluginManager().registerEvents(new ModelAListener(), this);
    // Model B
    ModelB modelB = new ModelB();
    NamespacedKey modelBRecipeKey = new NamespacedKey(this, "bigben_armory_model_b");
    getServer().addRecipe(modelB.registerShape(modelBRecipeKey, modelB.load()));
    // Model C
    ModelC modelC = new ModelC();
    NamespacedKey modelCRecipeKey = new NamespacedKey(this, "bigben_armory_model_c");
    getServer().addRecipe(modelC.registerShape(modelCRecipeKey, modelC.load()));
    // Model D
    ModelD modelD = new ModelD();
    NamespacedKey modelDRecipeKey = new NamespacedKey(this, "bigben_armory_model_d");
    getServer().addRecipe(modelD.registerShape(modelDRecipeKey, modelD.load()));
    // Model E
    ModelE modelE = new ModelE();
    NamespacedKey modelERecipeKey = new NamespacedKey(this, "bigben_armory_model_e");
    getServer().addRecipe(modelE.registerShape(modelERecipeKey, modelE.load()));
    // Model F
    ModelF modelF = new ModelF();
    NamespacedKey modelFRecipeKey = new NamespacedKey(this, "bigben_armory_model_f");
    getServer().addRecipe(modelF.registerShape(modelFRecipeKey, modelF.load()));
    // Model G
    ModelG modelG = new ModelG();
    NamespacedKey modelGRecipeKey = new NamespacedKey(this, "bigben_armory_model_g");
    getServer().addRecipe(modelG.registerShape(modelGRecipeKey, modelG.load()));
    // Model H
    ModelH modelH = new ModelH();
    NamespacedKey modelHRecipeKey = new NamespacedKey(this, "bigben_armory_model_h");
    getServer().addRecipe(modelH.registerShape(modelHRecipeKey, modelH.load()));
    getServer().getPluginManager().registerEvents(new ModelHListener(), this);
    // log
    getLogger().info("Armory has been enabled!");
  }

  @Override
  public void onDisable() {
    getLogger().info("Armory has been disabled!");
  }
}
