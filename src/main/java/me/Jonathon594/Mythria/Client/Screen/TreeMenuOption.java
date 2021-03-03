package me.Jonathon594.Mythria.Client.Screen;

import com.google.common.collect.Sets;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.HoverEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class TreeMenuOption {
    private final TreeMenuOption parent;
    private final ResourceLocation id;
    private final Set<TreeMenuOption> children = Sets.newLinkedHashSet();
    private final ITextComponent displayText;
    private DisplayInfo display;
    private boolean highlight = false;

    public TreeMenuOption(ResourceLocation id, @Nullable TreeMenuOption parentIn, @Nullable DisplayInfo displayIn) {
        this.id = id;
        this.display = displayIn;
        this.parent = parentIn;
        if (parentIn != null) {
            parentIn.addChild(this);
        }

        if (displayIn == null) {
            this.displayText = new StringTextComponent(id.toString());
        } else {
            ITextComponent itextcomponent = displayIn.getTitle();
            TextFormatting textformatting = displayIn.getFrame().getFormat();
            ITextComponent itextcomponent1 = TextComponentUtils.func_240648_a_(itextcomponent.deepCopy(), Style.EMPTY.setFormatting(textformatting)).appendString("\n").append(displayIn.getDescription());
            ITextComponent itextcomponent2 = itextcomponent.deepCopy().modifyStyle((style) -> style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, itextcomponent1)));
            this.displayText = TextComponentUtils.wrapWithSquareBrackets(itextcomponent2).mergeStyle(textformatting);
        }
    }

    /**
     * Add the given {@code TreeMenuOption} as a child of this {@code TreeMenuOption}.
     *
     * @see #getParent()
     */
    public void addChild(TreeMenuOption treeMenuOptionIn) {
        this.children.add(treeMenuOptionIn);
    }

    /**
     * Creates a new TreeMenuOption builder with the data from this TreeMenuOption
     */
    public TreeMenuOption.Builder copy() {
        return new TreeMenuOption.Builder(this.parent == null ? null : this.parent.getId(), this.display);
    }

    /**
     * Get this {@code TreeMenuOption}'s unique identifier.
     *
     * @return this {@code TreeMenuOption}'s unique identifier
     */
    public ResourceLocation getId() {
        return this.id;
    }

    @Nullable
    public TreeMenuOption getParent() {
        return this.parent;
    }

    /**
     * Get information that defines this {@code TreeMenuOption}'s appearance in GUIs.
     *
     * @return information that defines this {@code TreeMenuOption}'s appearance in GUIs. If {@code null}, signifies an
     * invisible {@code TreeMenuOption}.
     */
    @Nullable
    public DisplayInfo getDisplay() {
        return this.display;
    }

    public void setDisplay(DisplayInfo display) {
        this.display = display;
    }

    /**
     * Get the children of this {@code TreeMenuOption}.
     *
     * @return an {@code Iterable} of this {@code TreeMenuOption}'s children.
     * @see #getParent()
     */
    public Iterable<TreeMenuOption> getChildren() {
        return this.children;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        } else if (!(p_equals_1_ instanceof TreeMenuOption)) {
            return false;
        } else {
            TreeMenuOption TreeMenuOption = (TreeMenuOption) p_equals_1_;
            return this.id.equals(TreeMenuOption.id);
        }
    }

    /**
     * Returns the {@code ITextComponent} that is shown in the chat message sent after this {@code TreeMenuOption} is
     * completed.
     *
     * @return the {@code ITextComponent} that is shown in the chat message sent after this {@code TreeMenuOption} is
     * completed. If this {@code TreeMenuOption} is {@linkplain #getDisplay() invisible}, then it consists simply of {@link
     * #getId()}. Otherwise, it is the {@linkplain DisplayInfo#getTitle() title} inside square brackets, colored by the
     * {@linkplain net.minecraft.advancements.FrameType#getFormat frame type}, and hovering over it shows the {@linkplain
     * DisplayInfo#getDescription() description}.
     */
    public ITextComponent getDisplayText() {
        return this.displayText;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public static class Builder {
        private ResourceLocation parentId;
        private TreeMenuOption parent;
        private DisplayInfo display;

        private Builder(@Nullable ResourceLocation parentIdIn, @Nullable DisplayInfo displayIn) {
            this.parentId = parentIdIn;
            this.display = displayIn;
        }

        private Builder() {
        }

        public static TreeMenuOption.Builder builder() {
            return new TreeMenuOption.Builder();
        }

        public TreeMenuOption.Builder withParent(TreeMenuOption parentIn) {
            this.parent = parentIn;
            return this;
        }

        public TreeMenuOption.Builder withParentId(ResourceLocation parentIdIn) {
            this.parentId = parentIdIn;
            return this;
        }

        public TreeMenuOption.Builder func_215092_a(ItemStack p_215092_1_, ITextComponent p_215092_2_, ITextComponent p_215092_3_, @Nullable ResourceLocation p_215092_4_, FrameType p_215092_5_, boolean p_215092_6_, boolean p_215092_7_, boolean p_215092_8_) {
            return this.withDisplay(new DisplayInfo(p_215092_1_, p_215092_2_, p_215092_3_, p_215092_4_, p_215092_5_, p_215092_6_, p_215092_7_, p_215092_8_));
        }

        public TreeMenuOption.Builder withDisplay(DisplayInfo displayIn) {
            this.display = displayIn;
            return this;
        }

        public TreeMenuOption.Builder withDisplay(IItemProvider itemIn, ITextComponent title, ITextComponent description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return this.withDisplay(new DisplayInfo(new ItemStack(itemIn.asItem()), title, description, background, frame, showToast, announceToChat, hidden));
        }

        public TreeMenuOption build(ResourceLocation id, List<TreeMenuOption> list) {
            if (list != null && !this.resolveParent((lookup) -> {
                for (TreeMenuOption option : list) {
                    if (option.getId().equals(lookup)) return option;
                }
                return null;
            })) {
                throw new IllegalStateException("Tried to build incomplete TreeMenuOption!");
            } else {
                return new TreeMenuOption(id, this.parent, this.display);
            }
        }

        /**
         * Tries to resolve the parent of this TreeMenuOption, if possible. Returns true on success.
         */
        public boolean resolveParent(Function<ResourceLocation, TreeMenuOption> lookup) {
            if (this.parentId == null) {
                return true;
            } else {
                if (this.parent == null) {
                    this.parent = lookup.apply(this.parentId);
                }

                return this.parent != null;
            }
        }
    }
}
