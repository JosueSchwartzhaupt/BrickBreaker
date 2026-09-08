package com.josuesch.assets;

import java.awt.Rectangle;

public class HitboxComparator {

    public static boolean isColiding(Placeble p1, Placeble p2){
        double tw = p1.getWidth();
        double th = p1.getHeight();
        double rw = p2.getWidth();
        double rh = p2.getHeight();
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        double tx = p1.getX();
        double ty = p1.getY();
        double rx = p2.getX();
        double ry = p2.getY();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    public static boolean willColide(Movable m, Placeble p){
        double newX = m.getX() + m.getSpeed() * Math.cos(m.getAngle());
        double newY = m.getY() + m.getSpeed() * Math.sin(m.getAngle());

        double tw = m.getWidth();
        double th = m.getHeight();
        double rw = p.getWidth();
        double rh = p.getHeight();
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        double tx = newX;
        double ty = newY;
        double rx = p.getX();
        double ry = p.getY();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    public static double getMaxSpeed(Movable m, Placeble p){
        if(!willColide(m,p)) return m.getSpeed();

        double dx = m.getSpeed() * Math.cos(m.getAngle());
        double dy = m.getSpeed() * Math.sin(m.getAngle());

        double maxX = m.getSpeed();
        //movendo pra direita
        if(dx > 0){
            maxX = p.getX() - (m.getX() + m.getWidth());
        }
        // esquerda
        else if(dx < 0){
            maxX = m.getX() - (p.getX() + p.getWidth());
        }
        double maxY = m.getSpeed();
        // movendo pra baixo
        if(dy > 0){
            maxY = p.getY() - (m.getY() + m.getHeight());
        }
        // movendo pra cima
        else if(dy < 0){
            maxY = m.getY() - (p.getY() + p.getHeight());
        }
        maxX = maxX >= 0 ? maxX : m.getSpeed();
        maxY = maxY >= 0 ? maxY : m.getSpeed();

        if(maxX < maxY)
        {
            return maxX/Math.abs(dx) * m.getSpeed();
        }
        else{
            return maxY/Math.abs(dy) * m.getSpeed();
        }
    }

    public static Direction getClosestWall(Placeble m, Placeble p){
        double right = p.getX() - (m.getX() + m.getWidth());
        double left = m.getX() - (p.getX() + p.getWidth());
        double down = p.getY() - (m.getY() + m.getHeight());
        double up = m.getY() - (p.getY() + p.getHeight());
        right = right > 0 ? right : Double.MAX_VALUE;
        left = left > 0 ? left : Double.MAX_VALUE;
        down = down > 0 ? down : Double.MAX_VALUE;
        up = up > 0 ? up : Double.MAX_VALUE;

        double min = Math.min(Math.min(right, left), Math.min(down, up));
        if(min == right) return Direction.RIGHT;
        if(min == left) return Direction.LEFT;
        if(min == down) return Direction.DOWN;
        if(min == up) return Direction.UP;

        return null;
    }

}
