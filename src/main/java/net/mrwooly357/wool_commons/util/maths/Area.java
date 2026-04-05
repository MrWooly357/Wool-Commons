package net.mrwooly357.wool_commons.util.maths;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.Codecs;
import net.mrwooly357.wool_commons.util.codec.StringIdentifiable;
import net.mrwooly357.wool_commons.util.function.Functions;
import net.mrwooly357.wool_commons.util.maths.pos.Pos2F;

import java.util.Arrays;
import java.util.Locale;

public sealed interface Area {

    Codec<Area> CODEC = Type.CODEC.dispatched(
            Functions.identified(Area::getType, "Area", "Area.Type"),
            Functions.identified(type -> switch (type) {
        case POINT -> Point.CODEC;
        case RECTANGLE -> Rectangle.CODEC;
        case TRIANGLE -> Triangle.CODEC;
        case CIRCLE -> Circle.CODEC;
        case COMBINED -> Combined.CODEC;
    }, "Area.Type", "Codec<Area>")
    );


    Type getType();

    Area move(float x, float y);

    boolean contains(Pos2F pos);

    boolean intersects(Area other);


    record Point(Pos2F pos) implements Area {

        public static final Codec<Point> CODEC = Pos2F.CODEC
                .map(
                        Functions.identified(Point::pos, Point.class, Pos2F.class),
                        Functions.identified(Point::new, Pos2F.class, Point.class)
                );


        @Override
        public Type getType() {
            return Type.POINT;
        }

        @Override
        public Area move(float x, float y) {
            return new Point(pos.add(x, y));
        }

        @Override
        public boolean contains(Pos2F pos) {
            return this.pos.equals(pos);
        }

        @Override
        public boolean intersects(Area other) {
            return other.contains(pos);
        }

        @Override
        public String toString() {
            return "Area.Point[pos: " + pos + "]";
        }
    }


    record Rectangle(Pos2F min, Pos2F max) implements Area {

        public static final Codec<Rectangle> CODEC = Codec.group(
                Pos2F.CODEC.fieldOf("min", Functions.identified(Rectangle::min, Rectangle.class, Pos2F.class)),
                Pos2F.CODEC.fieldOf("max", Functions.identified(Rectangle::min, Rectangle.class, Pos2F.class))
        ).apply(Rectangle::new);


        @Override
        public Type getType() {
            return Type.RECTANGLE;
        }

        @Override
        public Area move(float x, float y) {
            return new Rectangle(min.add(x, y), max.add(x, y));
        }

        @Override
        public boolean contains(Pos2F pos) {
            float x = pos.x();
            float y = pos.y();

            return x >= min.x() && x <= max.x()
                    && y >= min.y() && y <= max.y();
        }

        @Override
        public boolean intersects(Area other) {
            return switch (other.getType()) {
                case POINT -> contains(((Point) other).pos);
                case RECTANGLE -> {
                    Rectangle r = (Rectangle) other;
                    Pos2F oMin = r.min;
                    Pos2F oMax = r.max;

                    yield min.x() <= oMax.x() && max.x() >= oMin.x()
                            && min.y() <= oMax.y() && max.y() >= oMin.y();
                }
                case TRIANGLE, COMBINED -> other.intersects(this);
                case CIRCLE -> {
                    Circle c = (Circle) other;
                    Pos2F centre = c.centre;

                    yield c.contains(new Pos2F(
                            Math.clamp(centre.x(), min.x(), max.x()),
                            Math.clamp(centre.y(), min.y(), max.y())
                    ));
                }
            };
        }

        @Override
        public String toString() {
            return "Area.Rectangle[min: " + min
                    + ", max: " + max + "]";
        }
    }


    record Triangle(Pos2F a, Pos2F b, Pos2F c) implements Area {

        public static final Codec<Triangle> CODEC = Codec.group(
                Pos2F.CODEC.fieldOf("a", Functions.identified(Triangle::a, Triangle.class, Pos2F.class)),
                Pos2F.CODEC.fieldOf("b", Functions.identified(Triangle::b, Triangle.class, Pos2F.class)),
                Pos2F.CODEC.fieldOf("c", Functions.identified(Triangle::c, Triangle.class, Pos2F.class))
        ).apply(Triangle::new);


        @Override
        public Type getType() {
            return Type.TRIANGLE;
        }

        @Override
        public Area move(float x, float y) {
            return new Triangle(a.add(x, y), b.add(x, y), c.add(x, y));
        }

        @Override
        public boolean contains(Pos2F pos) {
            float ax = a.x();
            float ay = a.y();
            float bx = b.x();
            float by = b.y();
            float cx = c.x();
            float cy = c.y();
            float area = (-by * cx + ay * (-bx + cx) + ax * (by - cy) + bx * cy);
            float s = (ay * cx -ax * cy + (cy - ay) * pos.x() + (ax - cx) * pos.y());
            float t = (ax * by - ay * bx + (ay - by) * pos.x() + (bx - ax) * pos.y());

            return area < 0.0F ? s <= 0.0F && t <= 0.0F && (s + t) >= area : s >= 0.0F && t >= 0.0F && (s + t) <= area;
        }

        @Override
        public boolean intersects(Area other) {
            return switch (other.getType()) {
                case POINT -> contains(((Point) other).pos);
                case RECTANGLE -> {
                    Rectangle r = (Rectangle) other;


                    if (r.contains(a) || r.contains(b) || r.contains(c))
                        yield true;
                    else {
                        Pos2F min = r.min;
                        Pos2F max = r.max;

                        if (contains(new Pos2F((min.x() + max.x()) / 2.0F, (min.y() + max.y()) / 2.0F)))
                            yield true;
                        else
                            yield segmentIntersectsRectangle(a, b, r)
                                    || segmentIntersectsRectangle(b, c, r)
                                    || segmentIntersectsRectangle(c, a, r);
                    }
                }
                case TRIANGLE -> {
                    Triangle t = (Triangle) other;
                    Pos2F ta = t.a;

                    if (contains(ta) || t.contains(a))
                        yield true;
                    else {
                        Pos2F tb = t.b;
                        Pos2F tc = t.c;

                        yield segmentsIntersect(a, b, ta, tb) || segmentsIntersect(a, b, tb, tc) || segmentsIntersect(a, b, tc, ta)
                                || segmentsIntersect(b, c, ta, tb) || segmentsIntersect(b, c, tb, tc) || segmentsIntersect(b, c, tc, ta)
                                || segmentsIntersect(c, a, ta, tb) || segmentsIntersect(c, a, tb, tc) || segmentsIntersect(c, a, tc, ta);
                    }

                }
                case CIRCLE -> {
                    Circle c = (Circle) other;

                    if (contains(c.centre))
                        yield true;
                    else
                        yield intersectsSegmentCircle(a, b, c)
                                || intersectsSegmentCircle(b, this.c, c)
                                || intersectsSegmentCircle(this.c, a, c);
                }
                case COMBINED -> other.intersects(this);
            };
        }

        private static boolean segmentIntersectsRectangle(Pos2F start, Pos2F end, Rectangle r) {
            float minX = Math.min(start.x(), end.x());
            float minY = Math.min(start.y(), end.y());
            float maxX = Math.max(start.x(), end.x());
            float maxY = Math.max(start.y(), end.y());

            if (maxX < r.min.x() || minX > r.max.x() || maxY < r.min.y() || minY > r.max.y())
                return false;
            else {
                Pos2F r1 = r.min;
                Pos2F r2 = new Pos2F(r.max.x(), r.min.y());
                Pos2F r3 = r.max;
                Pos2F r4 = new Pos2F(r.min.x(), r.max.y());

                return segmentsIntersect(start, end, r1, r2)
                        || segmentsIntersect(start, end, r2, r3)
                        || segmentsIntersect(start, end, r3, r4)
                        || segmentsIntersect(start, end, r4, r1);
            }
        }

        private static boolean intersectsSegmentCircle(Pos2F v1, Pos2F v2, Circle c) {
            float dx = v2.x() - v1.x();
            float dy = v2.y() - v1.y();
            float lengthSquared = dx * dx + dy * dy;

            if (lengthSquared == 0.0F)
                return c.contains(v1);

            float t = Math.clamp(((c.centre.x() - v1.x()) * dx + (c.centre.y() - v1.y()) * dy) / lengthSquared, 0.0F, 1.0F);
            Pos2F closest = new Pos2F(v1.x() + t * dx, v1.y() + t * dy);

            return c.contains(closest);
        }

        private static boolean segmentsIntersect(Pos2F a, Pos2F b, Pos2F c, Pos2F d) {
            return counterClockwise(a, c, d) != counterClockwise(b, c, d) && counterClockwise(a, b, c) != counterClockwise(a, b, d);
        }

        private static boolean counterClockwise(Pos2F p1, Pos2F p2, Pos2F p3) {
            float p1X = p1.x();
            float p1Y = p1.y();

            return (p3.y() - p1Y) * (p2.x() - p1X) > (p2.y() - p1Y) * (p3.x() - p1X);
        }

        @Override
        public String toString() {
            return "Area.Triangle[a: " + a
                    + ", b: " + b
                    + ", c: " + c + "]";
        }
    }


    record Circle(Pos2F centre, float radius) implements Area {

        public static final Codec<Circle> CODEC = Codec.group(
                Pos2F.CODEC.fieldOf("centre", Functions.identified(Circle::centre, Circle.class, Pos2F.class)),
                Codecs.FLOAT.fieldOf("radius", Functions.identified(Circle::radius, Circle.class, float.class))
        ).apply(Circle::new);


        @Override
        public Type getType() {
            return Type.CIRCLE;
        }

        @Override
        public Area move(float x, float y) {
            return new Circle(centre.add(x, y), radius);
        }

        @Override
        public boolean contains(Pos2F pos) {
            return centre.squaredDistanceTo(pos) <= radius * radius;
        }

        @Override
        public boolean intersects(Area other) {
            return switch (other.getType()) {
                case POINT -> contains(((Point) other).pos);
                case RECTANGLE, TRIANGLE, COMBINED -> other.intersects(this);
                case CIRCLE -> {
                    Circle c = (Circle) other;
                    float r = radius + c.radius;

                    yield centre.squaredDistanceTo(c.centre) <= r * r;
                }
            };
        }

        @Override
        public String toString() {
            return "Area.Circle[centre: " + centre
                    + ", radius: " + radius + "]";
        }
    }


    record Combined(Area[] areas) implements Area {

        public static final Codec<Combined> CODEC = Area.CODEC.collection()
                .map(
                        Functions.identified(combined -> Arrays.asList(combined.areas), "Area.Combined", "List<Area>"),
                        Functions.identified(areas -> new Combined(areas.toArray(Area[]::new)), "List<Area>", "Area.Combined")
                );


        @Override
        public Type getType() {
            return Type.COMBINED;
        }

        @Override
        public Area move(float x, float y) {
            int count = areas.length;
            Area[] movedAreas = new Area[count];

            for (int i = 0; i < count; i++)
                movedAreas[i] = areas[i].move(x, y);

            return new Combined(movedAreas);
        }

        @Override
        public boolean contains(Pos2F pos) {
            for (Area area : areas)
                if (area.contains(pos))
                    return true;

            return false;
        }

        @Override
        public boolean intersects(Area other) {
            for (Area area : areas)
                if (area.intersects(other))
                    return true;

            return false;
        }

        @Override
        public String toString() {
            return "Area.Combined[areas: " + Arrays.toString(areas) + "]";
        }
    }


    enum Type implements StringIdentifiable {

        POINT("point"),
        RECTANGLE("rectangle"),
        TRIANGLE("triangle"),
        CIRCLE("circle"),
        COMBINED("combined");

        public static final Codec<Type> CODEC = Codec.forEnum(Functions.<String, Type>identified(id -> valueOf((id).toUpperCase(Locale.ROOT)), "String", "Area.Type"));

        private final String id;

        Type(String id) {
            this.id = id;
        }


        @Override
        public String asString() {
            return id;
        }
    }
}
