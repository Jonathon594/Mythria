package me.Jonathon594.Mythria.Entity.AI;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.EntityAttitudeGene;
import me.Jonathon594.Mythria.DataTypes.Genetic.Gene.Gene;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.player.PlayerEntity;

public class AvoidGeneticGoal extends AvoidEntityGoal<PlayerEntity> {
    private final CreatureEntity creatureEntity;

    public AvoidGeneticGoal(CreatureEntity creatureEntity) {
        super(creatureEntity, PlayerEntity.class, 6.0f, 1.0, 1.2);
        this.creatureEntity = creatureEntity;
    }

    @Override
    public boolean shouldExecute() {
        boolean avoid = false;
        if (avoidTarget != null) {
            for (Gene gene : ProfileProvider.getProfile(avoidTarget).getGenetic().getExtraGenes()) {
                if (gene instanceof EntityAttitudeGene) {
                    EntityAttitudeGene entityAttitudeGene = (EntityAttitudeGene) gene;
                    if (entityAttitudeGene.getEntityTypes().contains(creatureEntity.getType()) &&
                            entityAttitudeGene.getAttitude().equals(EntityAttitudeGene.Attitude.FEAR)) {
                        avoid = true;
                    }
                }
            }
        }
        return avoid;
    }
}
