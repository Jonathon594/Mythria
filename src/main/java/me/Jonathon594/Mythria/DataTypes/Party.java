package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public class Party {
    private final PlayerEntity owner;
    private final ArrayList<PlayerEntity> members = new ArrayList<>();
    private final ArrayList<PlayerEntity> invitations = new ArrayList<>();

    public Party(PlayerEntity player) {
        this.owner = player;
        members.add(player);
    }

    public ArrayList<PlayerEntity> getMembers() {
        return members;
    }

    public PlayerEntity getOwner() {
        return owner;
    }

    public void addInvitation(PlayerEntity target) {
        if (invitations.contains(target)) return;
        invitations.add(target);
    }

    public ArrayList<PlayerEntity> getInvitations() {
        return invitations;
    }

    public void addMember(PlayerEntity player) {
        if (members.contains(player)) return;
        members.add(player);
    }

    public void removeMember(PlayerEntity player) {
        members.remove(player);
    }
}
