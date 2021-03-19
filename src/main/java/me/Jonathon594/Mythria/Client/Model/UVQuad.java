package me.Jonathon594.Mythria.Client.Model;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;

class UVQuad {
    public final Vector3f[] vertexPositions;
    public final Vector3f normal;
    public final Vector2f[] uvPositions;

    public UVQuad(Vector3f[] positionsIn, Vector2f[] uvsIn, Direction directionIn) {
        this.vertexPositions = positionsIn;
        this.uvPositions = uvsIn;
        this.normal = directionIn.toVector3f();
    }
}
