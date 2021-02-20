package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Managers.MaterialManager;
import me.Jonathon594.Mythria.Perk.Perks;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CommonListener {
    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        MaterialManager.onTagsUpdated();
        Perks.onTagsUpdated();
    }
}
