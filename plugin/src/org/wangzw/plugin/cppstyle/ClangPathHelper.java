package org.wangzw.plugin.cppstyle;

import static org.wangzw.plugin.cppstyle.replacement.Logger.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ClangPathHelper {

    private static String cachedValidClangFormatPath = null;

    private static String cachedValidClangFormatStylePath = null;

    /**
     * Returns the first path which exists and is runnable and caches it.
     * @param pathCandidates
     * @return
     */
    public Optional<String> getFirstValidClangFormatPath(List<String> pathCandidates) {
        Optional<String> validPath = findFirstValidPath(pathCandidates, FilePathUtil::isFileRunnable);
        if (validPath.isPresent()) {
            cachedValidClangFormatPath = FilePathUtil.toNormalizedAbsolutePath(validPath.get());
            logInfo("cachedValidClangFormatPath: " + cachedValidClangFormatPath);
        }
        return validPath;
    }

    /**
     * Returns the first path which exists and caches it.
     * @param pathCandidates
     * @return
     */
    public Optional<String> getFirstValidClangFormatStylePath(List<String> pathCandidates) {
        Optional<String> validPath = findFirstValidPath(pathCandidates, FilePathUtil::fileExists);
        if (validPath.isPresent()) {
            cachedValidClangFormatStylePath = FilePathUtil.toNormalizedAbsolutePath(validPath.get());
            logInfo("cachedValidClangFormatStylePath: " + cachedValidClangFormatStylePath);
        }
        return validPath;
    }

    public Optional<String> findFirstValidPath(List<String> pathCandidates, Predicate<? super String> predicate) {
        return pathCandidates.stream().filter(predicate).findFirst();
    }

    public String getCachedClangFormatPath() {
        return cachedValidClangFormatPath;
    }

    public String getCachedClangFormatStylePath() {
        return cachedValidClangFormatStylePath;
    }
}
