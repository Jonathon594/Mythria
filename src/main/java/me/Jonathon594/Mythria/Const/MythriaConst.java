package me.Jonathon594.Mythria.Const;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;

import java.util.List;

public class MythriaConst {
    public static final String NAME_SHORT = ColorConst.MAIN_COLOR + "Please make sure all names are 3 characters or more.";
    public static final String NAMES_NO_SPACES = ColorConst.MAIN_COLOR + "Please make sure all your names have no spaces.";
    public static final String ALREADY_PROFILE = ColorConst.MAIN_COLOR + "You have already made your profile.";
    public static final String CHAT_TOGGLED = ColorConst.MAIN_COLOR + "Now speaking in %s.";
    public static final String ALREADY_PERK = ColorConst.MAIN_COLOR + "You have already obtained this perk.";
    public static final String NOT_ENOUGH_ATTRIBUTE = ColorConst.MAIN_COLOR + "You don't have enough attribute points for this.";
    public static final String NOT_HAVE_REQUIRED_PERK = ColorConst.MAIN_COLOR + "You need to learn something else before this.";
    public static final String TOO_YOUNG = ColorConst.MAIN_COLOR + "You are too young for this.";
    public static final String NOT_ENOUGH_LEVEL = ColorConst.MAIN_COLOR + "You need to level up more to purchase this.";
    public static final String PERK_OBTAINED = ColorConst.MAIN_COLOR + "You have obtained " + ColorConst.CONT_COLOR + "%s" + ColorConst.MAIN_COLOR + ".";
    public static final String PLAYER_LEVEL_UP = ColorConst.HIGH_COLOR + "You have leveled up to %s";
    public static final String LEVE_UP_SKILL = ColorConst.MAIN_COLOR + "You have gained a level in %s. %s";
    public static final String NOT_RIGHT_TOOL = ColorConst.MAIN_COLOR + "That is not the right tool to break that.";
    public static final String NO_PERK = ColorConst.MAIN_COLOR + "You don't know what you are doing...";
    public static final String FIREMAKING_STICK_BROKEN = ColorConst.MAIN_COLOR + "You broke a stick...";
    public static final String FIREMAKING_NO_FUEL = ColorConst.MAIN_COLOR + "You can't light this without fuel";
    public static final String ALREADY_PERSONALITY = ColorConst.MAIN_COLOR + "You already have a personality, and you cannot get anymore.";
    public static final String NO_PERSONALITY = ColorConst.MAIN_COLOR + "You need to pick a personality first.";
    public static final String ATTRIBUTE_MAXED = ColorConst.MAIN_COLOR + "You have already maxed this attribute.";
    public static final String NOT_ENOUGH_ATTRIBUTE_INVESTMENTS = ColorConst.MAIN_COLOR + "You need to invest more into your attributes to buy this perk.";
    public static final String SOUL_LEVEL_UP = ColorConst.MAIN_COLOR + "Your soul has gained a permanent attribute point.";
    public static final double IDEAL_TEMP = 14;
    public static final String FISHING_FAIL = ColorConst.MAIN_COLOR + "You felt a tug, but you didn't catch anything. Maybe it got away.";
    public static final String ALREADY_PRIMARY_GENETIC = ColorConst.MAIN_COLOR + "You have already chosen your race.";
    public static final String NO_PRIMARY_GENETIC = ColorConst.MAIN_COLOR + "You need to pick a race first.";
    public static final String SEX_OFFER = ColorConst.MAIN_COLOR + "%s would like to have sex with you.";
    public static final String FAMILY_NAME_IN_USE = ColorConst.MAIN_COLOR + "That family name is already in use.";
    public static final String NO_PLOT_ACCESS = ColorConst.MAIN_COLOR + "You do not have time to build here.";
    public static final String NO_PROFILE = ColorConst.MAIN_COLOR + "You need to make your profile first.";
    public static final String SPELL_CARD_LEARNED = ColorConst.MAIN_COLOR + "You have learned a new magical skill %s.";
    public static final String MISSING_SPELL_CARDS = ColorConst.MAIN_COLOR + "You do not understand all of the techniques used in this spell book";
    public static final String HAS_SPELL_WITH_NAME = ColorConst.MAIN_COLOR + "You already have a spell memorized with this spells name";
    public static final String FIREMAKING_BOW_BROKEN = ColorConst.MAIN_COLOR + "You broke your bow!";
    public static final String REMEMBER_SPELL = ColorConst.MAIN_COLOR + "You have memorized the spell.";
    public static final String CANT_EDIT_SPELL = ColorConst.MAIN_COLOR + "You can't edit a spell book!";
    public static final String CANT_DECONSTRUC = ColorConst.MAIN_COLOR + "You cannot demolish that block.";
    public static final String MEDICAL_APPLIED = ColorConst.MAIN_COLOR + "%s has been treated with %s.";
    public static final String MEDICAL_NEED_MORE_TREATMENT = ColorConst.MAIN_COLOR + "It seems like %s needs more treatment.";
    public static final String MEDICAL_HEAL_SOON = ColorConst.MAIN_COLOR + "It seems like %s will be healed soon";
    public static final String MEDICAL_CURED = ColorConst.MAIN_COLOR + "%s has been cured.";
    public static final String LEVEL_UP_ATTRIBUTE = ColorConst.MAIN_COLOR + "You have gained an attribute in %s. %s";
    public static final String PERK_LEARNING_START = ColorConst.MAIN_COLOR + "You are now learning %s.";
    public static final String STILL_LEARNING_PERK = ColorConst.MAIN_COLOR + "You are still learning about this...";
    public static final String PROXIMITY_LEARN = ColorConst.MAIN_COLOR + "%s has helped you finish learning %s.";
    public static final String FINISH_LEARNING_PERK = ColorConst.MAIN_COLOR + "You have finished learning %s.";
    public static final String[] FOOD_AGE = new String[]{"Fresh ", "", "Old ", "Rancid ", "Spoiled "};
    public static final String NOT_ENOUGH_FAVOR = ColorConst.MAIN_COLOR + "You don't have enough connection with a Divine to learn this.";
    public static final String PERK_EXCLUDED = ColorConst.MAIN_COLOR + "A perk you have learned prevents you from ever learning this.";
    public static final String NAME_TOO_LONG = ColorConst.MAIN_COLOR + "Please make sure your name is no longer than 26 characters total. (Currently %s)";
    public static final String CANT_PLACE = ColorConst.MAIN_COLOR + "You do not know how to place this.";

    public static final List<EntityType> LILASIA_ENTITIES = ImmutableList.of(EntityType.SPIDER, EntityType.SKELETON,
            EntityType.WITCH, EntityType.CREEPER, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE_HORSE);
}