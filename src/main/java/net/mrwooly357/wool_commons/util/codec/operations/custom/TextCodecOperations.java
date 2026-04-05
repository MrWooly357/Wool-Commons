package net.mrwooly357.wool_commons.util.codec.operations.custom;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextCodecOperations implements CodecOperations<String> {

    public static final TextCodecOperations PRETTY = new TextCodecOperations(false);
    public static final TextCodecOperations COMPRESSED = new TextCodecOperations(true);
    private static final DataResult<String> EMPTY = new DataResult.Success<>("");
    private static final DataResult<String> TRUE = new DataResult.Success<>(String.valueOf(true));
    private static final DataResult<String> FALSE = new DataResult.Success<>(String.valueOf(false));

    private final boolean compressed;
    private final ThreadLocal<Integer> depth = ThreadLocal.withInitial(() -> 0);

    protected TextCodecOperations(boolean compressed) {
        this.compressed = compressed;
    }


    @Override
    public boolean usesStringFieldIds() {
        return true;
    }

    @Override
    public DataResult<String> empty() {
        return EMPTY;
    }

    @Override
    public boolean isEmpty(String input) {
        return input.isEmpty();
    }

    @Override
    public DataResult<String> encodeByte(byte b) {
        return new DataResult.Success<>(String.valueOf(b));
    }

    @Override
    public DataResult<String> encodeShort(short s) {
        return new DataResult.Success<>(String.valueOf(s));
    }

    @Override
    public DataResult<String> encodeInt(int i) {
        return new DataResult.Success<>(String.valueOf(i));
    }

    @Override
    public DataResult<String> encodeLong(long l) {
        return new DataResult.Success<>(String.valueOf(l));
    }

    @Override
    public DataResult<String> encodeFloat(float f) {
        return new DataResult.Success<>(String.valueOf(f));
    }

    @Override
    public DataResult<String> encodeDouble(double d) {
        return new DataResult.Success<>(String.valueOf(d));
    }

    @Override
    public DataResult<String> encodeBoolean(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    @Override
    public DataResult<String> encodeChar(char c) {
        return encodeString(String.valueOf(c));
    }

    @Override
    public DataResult<String> encodeString(String s) {
        return new DataResult.Success<>("\"" +
                s.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\t", "\\t")
                        .replace("\r", "\\r")
                        .replace("\n", "\\n")
                + "\"");
    }

    @Override
    public <E> DataResult<String> encodeCollection(Collection<E> c, Function<? super E, ? extends DataResult<String>> valueEncoder) {
        if (c.isEmpty())
            return new DataResult.Success<>("[]");
        else {
            int d = depth.get();
            depth.set(d + 1);

            try {
                return new DataResult.Success<>("[" + c.stream()
                        .map(valueEncoder)
                        .filter(DataResult::isSuccess)
                        .map(r -> {
                            String element = r.getOrThrow();

                            if (!compressed || element.contains("\n"))
                                element = element.replace("\n", "\n" + " ".repeat(d * 2));

                            return indent(d + 1) + element;
                        })
                        .collect(Collectors.joining(",")) + indent(d) + "]");
            } finally {
                depth.set(d);
            }
        }
    }

    @Override
    public <K, V> DataResult<String> encodeMap(Map<K, V> m, Function<? super K, ? extends DataResult<String>> keyEncoder, BiFunction<? super K, ? super V, ? extends DataResult<String>> valueEncoder) {
        if (m.isEmpty())
            return new DataResult.Success<>("{}");
        else {
            int d = depth.get();
            depth.set(d + 1);

            try {
                return new DataResult.Success<>("{" + m.entrySet().stream()
                        .map(entry -> {
                            K key = entry.getKey();

                            return new Pair<>(keyEncoder.apply(key), valueEncoder.apply(key, entry.getValue()));
                        })
                        .filter(pair -> pair.first().isSuccess() && pair.second().isSuccess())
                        .map(pair -> {
                            String key = pair.first().getOrThrow();
                            String value = pair.second().getOrThrow();

                            if (!compressed && value.contains("\n"))
                                value = value.replace("\n", "\n  ");

                            return indent(d + 1) + key + ":" + space() + value;
                        })
                        .collect(Collectors.joining(",")) + indent(d) + "}");
            } finally {
                depth.set(d);
            }
        }
    }

    private String indent(int depth) {
        return compressed ? "" : "\n" + " ".repeat(depth * 2);
    }

    @Override
    public <F, S> DataResult<String> encodePair(Pair<F, S> pair, Function<? super F, ? extends DataResult<String>> firstEncoder, Function<? super S, ? extends DataResult<String>> secondEncoder) {
        DataResult<String> first = firstEncoder.apply(pair.first());
        DataResult<String> second = secondEncoder.apply(pair.second());

        if (first.isSuccess() && second.isSuccess())
            return new DataResult.Success<>("(" + first.getOrThrow() + ";" + space() + second.getOrThrow() + ")");
        else
            return new DataResult.Error<>("Failed to encode pair " + pair + "!", new IllegalArgumentException());
    }

    private String space() {
        return compressed ? "" : " ";
    }

    @Override
    public DataResult<Byte> decodeByte(String input) {
        try {
            return new DataResult.Success<>(Byte.valueOf(input));
        } catch (NumberFormatException e) {
            return new DataResult.Error<>("Malformed text byte " + input + "!", e);
        }
    }

    @Override
    public DataResult<Short> decodeShort(String input) {
        try {
            return new DataResult.Success<>(Short.valueOf(input));
        } catch (NumberFormatException e) {
            return new DataResult.Error<>("Malformed text short " + input + "!", e);
        }
    }

    @Override
    public DataResult<Integer> decodeInt(String input) {
        try {
            return new DataResult.Success<>(Integer.valueOf(input));
        } catch (NumberFormatException e) {
            return new DataResult.Error<>("Malformed text int " + input + "!", e);
        }
    }

    @Override
    public DataResult<Long> decodeLong(String input) {
        try {
            return new DataResult.Success<>(Long.valueOf(input));
        } catch (NumberFormatException e) {
            return new DataResult.Error<>("Malformed text long " + input + "!", e);
        }
    }

    @Override
    public DataResult<Float> decodeFloat(String input) {
        try {
            return new DataResult.Success<>(Float.valueOf(input));
        } catch (NumberFormatException e) {
            return new DataResult.Error<>("Malformed text float " + input + "!", e);
        }
    }

    @Override
    public DataResult<Double> decodeDouble(String input) {
        try {
            return new DataResult.Success<>(Double.valueOf(input));
        } catch (NumberFormatException e) {
            return new DataResult.Error<>("Malformed text double " + input + "!", e);
        }
    }

    @Override
    public DataResult<Boolean> decodeBoolean(String input) {
        return input.equals("true") || input.equals("false") ? new DataResult.Success<>(Boolean.valueOf(input)) : new DataResult.Error<>("Malformed text boolean " + input + "!", new IllegalArgumentException());
    }

    @Override
    public DataResult<Character> decodeChar(String input) {
        return decodeString(input).map(s -> s.isEmpty() ? ' ' : s.charAt(0));
    }

    @Override
    public DataResult<String> decodeString(String input) {
        String s = input.trim();

        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\""))
            return new DataResult.Success<>(s.substring(1, s.length() - 1)
                    .replace("\\n", "\n")
                    .replace("\\r", "\r")
                    .replace("\\t", "\t")
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\"));
        else
            return new DataResult.Success<>(s);
    }

    @Override
    public <E> DataResult<Collection<E>> decodeCollection(String input, Function<? super String, ? extends DataResult<E>> valueDecoder) {
        String s = input.trim();

        if (!s.startsWith("[") || !s.endsWith("]"))
            return new DataResult.Error<>("Malformed text collection " + input + "!", new IllegalArgumentException());
        else {
            String content = s.substring(1, s.length() - 1);

            if (content.isEmpty())
                return new DataResult.Success<>(List.of());
            else
                return new DataResult.Success<>(splitTopLevel(content, ',').stream()
                        .map(String::trim)
                        .filter(s1 -> !s1.isEmpty())
                        .map(valueDecoder)
                        .filter(DataResult::isSuccess)
                        .map(DataResult::getOrThrow)
                        .toList());
        }
    }

    @Override
    public <K, V> DataResult<Map<K, V>> decodeMap(String input, Function<? super String, ? extends DataResult<K>> keyDecoder, BiFunction<? super K, ? super String, ? extends DataResult<V>> valueDecoder) {
        String s = input.trim();

        if (!s.startsWith("{") || !s.endsWith("}"))
            return new DataResult.Error<>("Malformed text map " + input + "!", new IllegalArgumentException());
        else {
            String content = s.substring(1, s.length() - 1).trim();

            if (content.isEmpty())
                return new DataResult.Success<>(Map.of());
            else {
                Map<K, V> result = new HashMap<>();
                splitTopLevel(content, ',').forEach(s1 -> {
                    List<String> list = splitTopLevel(s1, ':');

                    if (list.size() == 2) {
                        DataResult<K> key = keyDecoder.apply(list.getFirst().trim());
                        K k = key.getOrThrow();
                        DataResult<V> value = valueDecoder.apply(k, list.get(1).trim());

                        if (key.isSuccess() && value.isSuccess())
                            result.put(k, value.getOrThrow());
                    }
                });

                return new DataResult.Success<>(Map.copyOf(result));
            }
        }
    }

    @Override
    public <F, S> DataResult<Pair<F, S>> decodePair(String input, Function<? super String, ? extends DataResult<F>> firstDecoder, Function<? super String, ? extends DataResult<S>> secondDecoder) {
        if (!input.startsWith("(") || !input.endsWith(")"))
            return new DataResult.Error<>("Malformed text pair " + input + "!", new IllegalArgumentException());
        else {
            String content = input.substring(1, input.length() - 1);

            if (content.isEmpty())
                return new DataResult.Error<>("Empty text pair " + input + "!", new IllegalArgumentException());
            else {
                List<String> s = splitTopLevel(content, ';');

                if (s.size() != 2)
                    return new DataResult.Error<>("Malformed text pair " + input + "!", new IllegalArgumentException());
                else {
                    DataResult<F> first = firstDecoder.apply(s.getFirst().trim());
                    DataResult<S> second = secondDecoder.apply(s.get(1).trim());

                    if (first.isSuccess() && second.isSuccess())
                        return new DataResult.Success<>(new Pair<>(first.getOrThrow(), second.getOrThrow()));
                    else
                        return new DataResult.Error<>("Failed to decode text pair " + input + "!", new IllegalArgumentException());
                }
            }
        }
    }

    private static List<String> splitTopLevel(String input, char delimiter) {
        List<String> parts = new ArrayList<>();
        int bracket = 0;
        int curly = 0;
        boolean inQuotes = false;
        boolean escaped = false;
        int start = 0;
        int length = input.length();

        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);

            if (escaped) {
                escaped = false;

                continue;
            } else if (c == '\\') {
                escaped = true;

                continue;
            } else if (c == '\"')
                inQuotes = !inQuotes;

            if (!inQuotes)

                if (c == '[')
                    bracket++;
                else if (c == ']')
                    bracket--;
                else if (c == '{')
                    curly++;
                else if (c == '}')
                    curly--;
                else if (c == delimiter && bracket == 0 && curly == 0) {
                    parts.add(input.substring(start, i));
                    start = i + 1;
                }
        }

        if (start < length)
            parts.add(input.substring(start));
        else if (start == length && length > 0)
            parts.add("");

        return List.copyOf(parts);
    }

    @Override
    public String toString() {
        return "TextCodecOperations[compressed: " + compressed + "]";
    }
}
