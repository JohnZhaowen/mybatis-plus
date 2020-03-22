package mp.my;

public interface Executor {

    <E> E query(String sql, Object param);
}
