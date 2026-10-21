import java.nio.file.Path;
import java.io.IOException;

public interface Reportable {
    void writeReportToFile(String filePath);
}
