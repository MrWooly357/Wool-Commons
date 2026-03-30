package net.mrwooly357.wool_commons.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public record FilePath(Path path) {

    public static final FilePath HOME = new FilePath(System.getProperty("user.dir"));
    public static final FilePath RUN = HOME.resolve("run");

    public FilePath(String path) {
        this(Path.of(path));
    }


    public boolean exists(LinkOption... options) {
        return Files.exists(path, options);
    }

    public boolean doesNotExist(LinkOption... options) {
        return Files.notExists(path, options);
    }

    public boolean isFile(LinkOption... options) {
        return Files.isRegularFile(path, options);
    }

    public boolean isDirectory(LinkOption... options) {
        return Files.isDirectory(path, options);
    }

    public FilePath getName() {
        return new FilePath(path.getFileName());
    }

    public boolean endsWith(String suffix) {
        return path.endsWith(suffix);
    }

    public FilePath getParent() {
        return new FilePath(path.getParent());
    }

    public FilePath resolve(String other) {
        return new FilePath(path.resolve(other));
    }

    public FilePath resolve(FilePath other) {
        return new FilePath(path.resolve(other.path));
    }

    public BufferedWriter createWriter(OpenOption... options) {
        try {
            return Files.newBufferedWriter(path, options);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create a writer for " + path + "!", e);
        }
    }

    public BufferedReader createReader() {
        try {
            return Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create a reader for " + path + "!", e);
        }
    }

    public BufferedOutputStream createOutputStream(OpenOption... options) {
        try {
            return new BufferedOutputStream(Files.newOutputStream(path, options));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create an output stream for " + path + "!", e);
        }
    }

    public BufferedInputStream createInputStream(OpenOption... options) {
        try {
            return new BufferedInputStream(Files.newInputStream(path, options));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create an input stream for " + path + "!", e);
        }
    }

    public Stream<FilePath> find(int maxDepth, BiPredicate<FilePath, BasicFileAttributes> matcher, FileVisitOption... options) {
        try {
            return Files.find(path, maxDepth, (path1, attributes) -> matcher.test(new FilePath(path1), attributes), options)
                    .map(FilePath::new);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void createDirectories(FileAttribute<?>... attributes) {
        try {
            Files.createDirectories(path, attributes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directories for " + path + "!", e);
        }
    }

    public void delete() {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete " + path + "!", e);
        }
    }

    public void deleteIfExists() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete " + path + "!", e);
        }
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
