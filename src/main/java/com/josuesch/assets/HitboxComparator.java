package com.josuesch.assets;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HitboxComparator {

    public static boolean isColiding(Placeble p1, Placeble p2) {
        return isColiding(
                p1.getX(), p1.getY(),
                p1.getWidth(), p1.getHeight(),
                p2.getX(), p2.getY(),
                p2.getWidth(), p2.getHeight());
    }

    public static boolean willColide(Movable m, Placeble p) {
        double newX = m.getX() + m.getSpeed() * Math.cos(m.getAngle());
        double newY = m.getY() + m.getSpeed() * Math.sin(m.getAngle());

        return isColiding(
                newX,
                newY,
                m.getWidth(),
                m.getHeight(),
                p.getX(),
                p.getY(),
                p.getWidth(),
                p.getHeight());
    }

    private static boolean isColiding(
            double tx,
            double ty,
            double tw,
            double th,
            double rx,
            double ry,
            double rw,
            double rh) {

        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }

        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx)
                && (rh < ry || rh > ty)
                && (tw < tx || tw > rx)
                && (th < ty || th > ry));
    }

    public static Collision getCollision(Movable m, Placeble p) {
        double dx = m.getSpeed() * Math.cos(m.getAngle());
        double dy = m.getSpeed() * Math.sin(m.getAngle());

        double timeX = 0;
        double timeY = 0;

        // Tempo necessário para atingir o plano X do bloco
        if (dx > 0) {
            double dist = p.getX() - (m.getX() + m.getWidth());
            if (dist > 0) timeX = dist / dx;
        } else if (dx < 0) {
            double dist = m.getX() - (p.getX() + p.getWidth());
            if (dist > 0) timeX = dist / Math.abs(dx);
        }

        // Tempo necessário para atingir o plano Y do bloco
        if (dy > 0) {
            double dist = p.getY() - (m.getY() + m.getHeight());
            if (dist > 0) timeY = dist / dy;
        } else if (dy < 0) {
            double dist = m.getY() - (p.getY() + p.getHeight());
            if (dist > 0) timeY = dist / Math.abs(dy);
        }

        // O contato com o bloco só ocorre quando ambos os eixos foram atingidos
        double collisionTime = Math.max(timeX, timeY);
        double maxSpeed = Math.min(m.getSpeed(), Math.max(0, collisionTime * m.getSpeed()));

        Direction direction;
        if (timeX > timeY) {
            direction = (dx < 0) ? Direction.LEFT : Direction.RIGHT;
        } else if (timeY > timeX) {
            direction = (dy < 0) ? Direction.UP : Direction.DOWN;
        } else {
            direction = getClosestWall(m, p);
        }

        return new Collision(p, maxSpeed, direction);
    }

    public static Direction getClosestWall(Placeble m, Placeble p) {
        double right = p.getX() - (m.getX() + m.getWidth());
        double left = m.getX() - (p.getX() + p.getWidth());
        double down = p.getY() - (m.getY() + m.getHeight());
        double up = m.getY() - (p.getY() + p.getHeight());
        right = right >= 0 ? right : Double.MAX_VALUE;
        left = left >= 0 ? left : Double.MAX_VALUE;
        down = down >= 0 ? down : Double.MAX_VALUE;
        up = up >= 0 ? up : Double.MAX_VALUE;

        double min = Math.min(Math.min(right, left), Math.min(down, up));
        if (min == right) return Direction.RIGHT;
        if (min == left) return Direction.LEFT;
        if (min == down) return Direction.DOWN;
        if (min == up) return Direction.UP;

        return null;
    }

    public static Optional<Collision> processCollision(Movable m, List<Placeble> placebles) {
        return placebles.stream()
                .filter(Placeble::isColidable)
                .filter(p -> !p.equals(m))
                .filter(p -> willColide(m, p)) // Mantém sua otimização inicial
                .map(p -> getCollision(m, p))
                .min(Comparator.naturalOrder());
    }
}
