package me.Jonathon594.Mythria.Tags;

import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class MythriaItemTags {
    public static final ITag<Item> ARROW_SHAFTS = wrapTag("arrow_shafts");
    public static final ITag<Item> ARROWS = wrapTag("arrows");
    public static final ITag<Item> BOWS = wrapTag("bows");
    public static final ITag<Item> AXES_AND_HATCHETS = wrapTag("axes_and_hatchets");
    public static final ITag<Item> BLADE_HANDLES = wrapTag("blade_handles");
    public static final ITag<Item> CLAY = wrapTag("clay");
    public static final ITag<Item> LOGS = wrapTag("logs");
    public static final ITag<Item> PLANKS = wrapTag("planks");
    public static final ITag<Item> SAW_HANDLES = wrapTag("saw_handles");
    public static final ITag<Item> SPEARS = wrapTag("spears");
    public static final ITag<Item> TOOL_HANDLES = wrapTag("tool_handles");
    public static final ITag<Item> TOOL_HANDLES_OVERWORLD = wrapTag("tool_handles_overworld");
    public static final ITag<Item> TOOL_HANDLES_NETHER = wrapTag("tool_handles_nether");
    public static final ITag<Item> CLUBS = wrapTag("clubs");

    private static ITag<Item> wrapTag(String name) {
        return ItemTags.getCollection().get(new MythriaResourceLocation(name));
    }
}
