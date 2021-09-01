package me.Jonathon594.Mythria.Util;

import me.Jonathon594.Mythria.Blocks.MythriaOre;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.DataTypes.Date;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.DataTypes.SpawnPos;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Items.MythriaDaggerItem;
import me.Jonathon594.Mythria.Items.MythriaHammerItem;
import me.Jonathon594.Mythria.Managers.TimeManager;
import me.Jonathon594.Mythria.Mythria;
import me.Jonathon594.Mythria.Perk.Perks;
import me.Jonathon594.Mythria.Worlds.MythriaWorlds;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class MythriaUtil {
    private static final Random RANDOM = new Random();

    public static void DropAllItems(final PlayerEntity player, final boolean armor, final boolean offhand) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            final ItemStack is = player.inventory.getStackInSlot(i);
            if (is != null) {
                if (i > 35 && i < 40)
                    if (!armor)
                        continue;
                if (i == 40)
                    if (!offhand)
                        continue;
                final ItemEntity item = new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), is);
                item.setPickupDelay(60);
                player.world.addEntity(item);
                player.inventory.removeStackFromSlot(i);
            }
        }
    }

    public static void addExperienceToAllAroundPoint(World world, int radius, BlockPos pos, MythicSkills skill, double amount, int effectiveLevel) {
        for (PlayerEntity playerEntity : getPlayersWithinRadius(world, radius, pos)) {
            Profile profile = ProfileProvider.getProfile(playerEntity);
            profile.addSkillExperience(skill, amount, (ServerPlayerEntity) playerEntity, effectiveLevel);
        }
    }

    public static void addLoreToItemStack(final ItemStack stack, boolean replace, String... lines) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(lines));

        addLoreToItemStack(stack, list, replace);
    }

    public static void addLoreToItemStack(final ItemStack stack, final ArrayList<String> lines, boolean replace) {
        CompoundNBT nbt = stack.getTag();
        if (nbt == null)
            nbt = new CompoundNBT();

        final CompoundNBT display = nbt.getCompound("display");
        ListNBT lore = new ListNBT();
        if (!replace) {
            if (display.contains("Lore")) {
                lore = display.getList("Lore", 8);
            }
        }
        for (final String s : lines)
            lore.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(new StringTextComponent(ColorConst.MAIN_COLOR + s))));


        display.put("Lore", lore);

        nbt.put("display", display);
        stack.setTag(nbt);
    }

    public static void addRecipesFromPerk(PlayerEntity player, Perk attribute) {
        if (player.world.isRemote) return;
        HashSet<IRecipe<?>> toUnlock = new HashSet<>();
        for (Item item : attribute.getCraftable()) {
            collectRecipesForItem(player, toUnlock, item);
        }

        if (toUnlock.size() > 0) player.unlockRecipes(toUnlock);
    }

    public static void addRecipesFromPerks(ServerPlayerEntity player, List<Perk> perks) {
        HashSet<Item> craftableItems = new HashSet<>();
        for (Perk perk : perks) {
            craftableItems.addAll(perk.getCraftable());
        }
        HashSet<IRecipe<?>> craftableRecipes = new HashSet<>();
        for (Item item : craftableItems) {
            collectRecipesForItem(player, craftableRecipes, item);
        }
        player.unlockRecipes(craftableRecipes);
    }

    public static void applyMythriaAttributeModifier(final PlayerEntity p, final String string, final double value,
                                                     final Attribute movementSpeed) {
        final ModifiableAttributeInstance atr = p.getAttribute(movementSpeed);
        for (AttributeModifier modifier : atr.getModifierListCopy()) {
            if (modifier.getName().equals(string)) {
                atr.removeModifier(modifier);
            }
        }
        atr.applyNonPersistentModifier(new AttributeModifier(string, value, AttributeModifier.Operation.ADDITION));
    }

    /**
     * Calculates the weighted average of a map.
     *
     * @param map A map of values and weights
     * @return The weighted average of the map
     * @throws ArithmeticException If divide by zero happens
     */
    public static Double calculateWeightedAverage(Map<Double, Integer> map) throws ArithmeticException {
        double num = 0;
        double denom = 0;
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            num += entry.getKey() * entry.getValue();
            denom += entry.getValue();
        }

        return num / denom;
    }

    public static String capitalize(final String s) {
        if (s.length() == 0)
            return s;
        String lower = s.toLowerCase();
        final char[] c = lower.toCharArray();
        if (Character.isUpperCase(c[0]))
            return s;
        else {
            c[0] = Character.toUpperCase(c[0]);
            return String.valueOf(c);
        }
    }

    public static String capitalizeWords(final String s) {
        if (s.length() == 0)
            return s;
        StringBuilder result = new StringBuilder();
        for (String part : s.split(" ")) {
            String lower = part.toLowerCase();
            final char[] c = lower.toCharArray();
            c[0] = Character.toUpperCase(c[0]);
            result.append(String.valueOf(c)).append(" ");
        }
        result = new StringBuilder(result.toString().trim());
        return result.toString();
    }

    public static float celciusFromBiomeTemperature(float temperature) {
        return temperature * 20f;
    }

    public static boolean destroyBlockWithTool(World world, BlockPos pos, boolean drops, @Nullable Entity entity, ItemStack stack) {
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.isAir(world, pos)) {
            return false;
        } else {
            FluidState FluidState = world.getFluidState(pos);
            world.playEvent(2001, pos, Block.getStateId(blockstate));
            if (drops) {
                TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
                Block.spawnDrops(blockstate, world, pos, tileentity, entity, stack);
            }

            return world.setBlockState(pos, FluidState.getBlockState(), 3);
        }
    }

    public static Block[] getAllBlocksOfType(Class clazz) {
        Iterator<Block> blockIterator = ForgeRegistries.BLOCKS.iterator();
        List<Block> blocks = new ArrayList<>();
        while (blockIterator.hasNext()) {
            Block b = blockIterator.next();
            if (b.getClass().isAssignableFrom(clazz)) {
                blocks.add(b);
            }
        }
        return blocks.toArray(new Block[blocks.size()]);
    }

    public static Item[] getAllItemsOfType(Class clazz) {
        Iterator<Item> itemIterator = ForgeRegistries.ITEMS.iterator();
        List<Item> items = new ArrayList<>();
        while (itemIterator.hasNext()) {
            Item b = itemIterator.next();
            if (b.getClass().isAssignableFrom(clazz)) {
                items.add(b);
            }
        }
        return items.toArray(new Item[0]);
    }

    public static List<Block> getBlockCollectionFromTag(ResourceLocation resourceLocationIn) {
        return BlockTags.getCollection().get(resourceLocationIn).getAllElements();
    }

    public static Date getDateFromAgeMonthDay(int age, final int month, final int day) {
        final Date currentDate = TimeManager.getCurrentDate();
        final Date d = new Date();
        final int currentYear = currentDate.getYear();
        d.setMGDFromDayMonthYear(day, month, currentYear);
        if (currentDate.getMonth() >= d.getMonth())
            if (currentDate.getDayInMonth() >= d.getDayInMonth())
                age -= 1;
        d.setMGD(d.getMGD() - age * TimeManager.getDaysPerYear());

        return d;
    }

    public static float getDeltaA(float a1, float a2) {
        float deltaA = a1 - a2;
        deltaA += deltaA > 180 ? -360 : deltaA < -180 ? 360 : 0;
        return deltaA;
    }

    public static double getExperienceForLevel(int level) {
        return 750.0 * Math.pow(2, (double) level / 7) - 750.0;
    }

    public static double getExperienceForPlayerLevel(int level) {
        return 7500.0 * Math.pow(2, (double) level / 7) - 7500.0;
    }

    public static List<Item> getItemCollectionFromTag(ResourceLocation resourceLocation) {
        return ItemTags.getCollection().get(resourceLocation).getAllElements();
    }

    public static Item[] getItemsFromTag(ResourceLocation resourceLocation) {
        return getItemCollectionFromTag(resourceLocation).toArray(new Item[0]);
    }

    public static int getLevelForXP(final double y) {
        if (y == 0) return 0;
        return (int) Math.floor((7 * Math.log(y / 750.0 + 1)) / Math.log(2));
    }

    public static int getPlayerLevelForXP(final double y) {
        if (y == 0) return 0;
        return (int) Math.floor(
                (7 * Math.log(y / 7500.0 + 1)) / Math.log(2)
        );
    }

    @NotNull
    public static List<PlayerEntity> getPlayersWithinRadius(World world, float radius, BlockPos pos) {
        return world.getEntitiesWithinAABB(EntityType.PLAYER,
                new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
                        pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius), playerEntity -> pos.distanceSq(playerEntity.getPosition()) < radius * radius);
    }

    public static SpawnPos getRandomPositionAroundPoint(SpawnPos center, int radius, ServerWorld world) {
        double angle = Math.random() * Math.PI * 2;
        double x = Math.cos(angle) * Math.random() * radius;
        double z = Math.sin(angle) * Math.random() * radius;
        return new SpawnPos(+center.getX(), (int) z + center.getZ());
    }

    public static void grantRecipeAdvancements(ServerPlayerEntity player) {
        ArrayList<Advancement> toGrant = new ArrayList<>();
        Advancement root = player.server.getAdvancementManager().getAdvancement(new ResourceLocation("minecraft:recipes/root"));
        toGrant.add(root);
        addChildren(root, toGrant);
        for (Advancement a : toGrant) {
            AdvancementProgress progress = player.getAdvancements().getProgress(a);
            if (progress.isDone()) {
                continue;
            }

            for (String s : progress.getRemaningCriteria()) {
                player.getAdvancements().grantCriterion(a, s);
            }
        }
    }

    public static boolean isAxe(final Item i) {
        return i instanceof AxeItem;
    }

    public static boolean isDagger(Item item) {
        return item instanceof MythriaDaggerItem;
    }

    public static boolean isHammer(Item item) {
        return item instanceof MythriaHammerItem;
    }

    public static boolean isMeleeWeapon(Item item) {
        return item instanceof TieredItem || item == Items.AIR;
    }

    public static boolean isOre(Block block) {
        return block instanceof MythriaOre;
    }

    public static boolean isSword(Item item) {
        return item instanceof SwordItem && !(item instanceof MythriaDaggerItem);
    }

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static double scaleChance(int maxValue, double minChance, double maxChance, Integer value) {
        if (maxChance == 0) return 0.0;
        double prop = MathHelper.clamp((double) value / maxValue, 0, 1);
        double slider = maxChance - minChance;
        double adjusted = slider * prop;
        return minChance + adjusted;
    }

    public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack, Vector3d velocity, int delay) {
        float f = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
        float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty()) {
            ItemEntity entityitem = new ItemEntity(worldIn, x + (double) f, y + (double) f1, z + (double) f2, stack);
            float f3 = 0.05F;
            entityitem.setVelocity(RANDOM.nextGaussian() * 0.05000000074505806D + velocity.x, RANDOM.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D + velocity.y,
                    RANDOM.nextGaussian() * 0.05000000074505806D + velocity.z);
            entityitem.setPickupDelay(delay);
            worldIn.addEntity(entityitem);
        }
    }

    public static void spawnSmokeParticles(World worldIn, BlockPos pos, boolean isSignalFire, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        BasicParticleType basicparticletype = isSignalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        worldIn.addOptionalParticle(basicparticletype, true, (double) pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble(), (double) pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (spawnExtraSmoke) {
            worldIn.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + 0.4D, (double) pos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }
    }

    public static void teleportPlayerToSpawnDimension(ServerPlayerEntity player) {
        ServerWorld spawn = player.getServer().getWorld(MythriaWorlds.SPAWN_KEY);
        player.teleport(spawn, 0, 64, 0, 0, 0);
    }

    public static double thermalConduction(double efficiency, double t1, double t2) {
        return efficiency * (t2 - t1);
    }

    public static void unlockDefaultRecipes(ServerPlayerEntity player) {
        ArrayList<IRecipe<?>> toUnlock = new ArrayList<>();
        for (IRecipe recipe : player.server.getRecipeManager().getRecipes()) {
            if (!recipe.getId().getNamespace().equals(Mythria.MODID)) continue;
            if (!Perks.requiresPerk(recipe.getRecipeOutput().getItem())) {
                toUnlock.add(recipe);
            }
        }

        if (toUnlock.size() > 0) player.unlockRecipes(toUnlock);
    }

    public static int wrapInt(int kX, final int kLowerBound, final int kUpperBound) {
        final int range_size = kUpperBound - kLowerBound + 1;
        if (kX < kLowerBound)
            kX += range_size * ((kLowerBound - kX) / range_size + 1);
        return kLowerBound + (kX - kLowerBound) % range_size;
    }

    public static int wrapLong(long kX, final int kLowerBound, final int kUpperBound) {
        final int range_size = kUpperBound - kLowerBound + 1;
        if (kX < kLowerBound)
            kX += range_size * ((kLowerBound - kX) / range_size + 1);
        return Math.toIntExact(kLowerBound + (kX - kLowerBound) % range_size);
    }

    private static void addChildren(Advancement root, ArrayList<Advancement> toGrant) {
        for (Advancement a : root.getChildren()) {
            toGrant.add(a);
            addChildren(a, toGrant);
        }
    }

    private static void collectRecipesForItem(PlayerEntity player, HashSet<IRecipe<?>> recipes, Item item) {
        for (IRecipe recipe : player.world.getServer().getRecipeManager().getRecipes()) {
            if (recipe.getRecipeOutput().getItem().equals(item)) {
                recipes.add(recipe);
            }
        }
    }
}
