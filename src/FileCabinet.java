import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    private static Stream<Folder> flattenMultiFolder(Folder folder) {
        return folder instanceof MultiFolder ?
                Stream.concat(Stream.of(folder), ((MultiFolder) folder).getFolders().stream()
                        .flatMap(FileCabinet::flattenMultiFolder)) :
                Stream.of(folder);
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return Optional.ofNullable(folders).orElseGet(Collections::emptyList).stream()
                .flatMap(FileCabinet::flattenMultiFolder)
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return Optional.ofNullable(folders).orElseGet(Collections::emptyList).stream()
                .flatMap(FileCabinet::flattenMultiFolder)
                .filter(folder -> folder.getSize().equals(size))
                .toList();
    }

    @Override
    public int count() {
        return (int) Optional.ofNullable(folders).orElseGet(Collections::emptyList).stream()
                .flatMap(FileCabinet::flattenMultiFolder)
                .count();
    }
}

