package me.Jonathon594.Mythria.Entity.AI;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Genetic.EntityRelationGene;
import me.Jonathon594.Mythria.Genetic.Gene;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.player.PlayerEntity;

public class AvoidGeneticGoal extends AvoidEntityGoal<PlayerEntity> {
    private CreatureEntity creatureEntity;

    public AvoidGeneticGoal(CreatureEntity creatureEntity) {
        super(creatureEntity, PlayerEntity.class, 6.0f, 1.0, 1.2);
        this.creatureEntity = creatureEntity;
    }

    @Override
    public boolean shouldExecute() {
        boolean avoid = super.shouldExecute();
        if (avoidTarget != null) {
            for (Gene gene : ProfileProvider.getProfile(avoidTarget).getGenetic().getExtraGenes()) {
                if (gene instanceof EntityRelationGene) {
                    EntityRelationGene entityRelationGene = (EntityRelationGene) gene;
                    if (!entityRelationGene.getEntityTypes().contains(creatureEntity.getType()) ||
                            !entityRelationGene.getRelationship().equals(EntityRelationGene.Relationship.FEAR)) {
                        avoid = false;
                    }
                }
            }
        }
        return avoid;
    }
}
