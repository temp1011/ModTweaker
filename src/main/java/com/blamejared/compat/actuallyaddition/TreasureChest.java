package com.blamejared.compat.actuallyaddition;

import com.blamejared.ModTweaker;
import com.blamejared.api.annotations.*;
import com.blamejared.mtlib.helpers.*;
import com.blamejared.mtlib.utils.*;
import crafttweaker.annotations.*;
import crafttweaker.api.item.IItemStack;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.TreasureChestLoot;
import stanhebben.zenscript.annotations.*;

import java.util.*;

@ZenClass("mods.actuallyadditions.TreasureChest")
@ModOnly("actuallyadditions")
@ZenRegister
public class TreasureChest {
    
    @ZenMethod
    public static void addLoot(IItemStack returnItem, int chance, int minAmount, int maxAmount) {
        ModTweaker.LATE_ADDITIONS.add(new Add(Collections.singletonList(new TreasureChestLoot(InputHelper.toStack(returnItem), chance, minAmount, maxAmount))));
    }
    
    @ZenMethod
    public static void removeLoot(IItemStack returnItem) {
        ModTweaker.LATE_REMOVALS.add(new Remove(returnItem));
    }
    
    private static class Add extends BaseListAddition<TreasureChestLoot> {
        
        protected Add(List<TreasureChestLoot> recipes) {
            super("Treasure Chest", ActuallyAdditionsAPI.TREASURE_CHEST_LOOT, recipes);
        }
        
        @Override
        protected String getRecipeInfo(TreasureChestLoot recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }
    }
    
    private static class Remove extends BaseListRemoval<TreasureChestLoot> {
        
        private IItemStack output;
        
        protected Remove(IItemStack output) {
            super("Treasure Chest", ActuallyAdditionsAPI.TREASURE_CHEST_LOOT);
            this.output = output;
        }
        
        @Override
        public void apply() {
            ActuallyAdditionsAPI.TREASURE_CHEST_LOOT.forEach(recipe -> {
                if(output.matches(InputHelper.toIItemStack(recipe.returnItem))) {
                    recipes.add(recipe);
                }
            });
            super.apply();
        }
        
        @Override
        protected String getRecipeInfo(TreasureChestLoot recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }
    }
}