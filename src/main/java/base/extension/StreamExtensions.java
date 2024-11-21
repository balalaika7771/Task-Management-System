package base.extension;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;


/**
 * @author Ivan Zhendorenko
 */
@UtilityClass
public class StreamExtensions {


  /**
   * Обёртка для {@link  Collectors#toMap(Function, Function) метода}
   */
  public static <K, V, E> Map<K, V> toMap(Stream<E> stream,
                                          Function<? super E, ? extends K> keyMapper,
                                          Function<? super E, ? extends V> valueMapper) {
    return stream.collect(Collectors.toMap(keyMapper, valueMapper));
  }
}
