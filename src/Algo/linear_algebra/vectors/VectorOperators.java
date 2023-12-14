package Algo.linear_algebra.vectors;

public interface VectorOperators<V> {

    public abstract V plus(V v);

    public abstract V minus(V v);

    public abstract V dot(V v);

    public boolean equalsTo(V v);

    public abstract V negate();

    public double modulus();

}
