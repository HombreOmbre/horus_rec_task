import java.util.List;

interface MultiFolder extends Folder {
    List<Folder> getFolders();
}