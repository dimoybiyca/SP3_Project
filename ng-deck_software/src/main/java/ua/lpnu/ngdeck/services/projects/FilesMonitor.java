package ua.lpnu.ngdeck.services.projects;

import lombok.extern.log4j.Log4j2;
import ua.lpnu.ngdeck.config.ConfigService;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
public class FilesMonitor extends Thread {

    private static final ConfigService configService = ConfigService.getInstance();
    private AtomicBoolean isChanged = new AtomicBoolean();

    public FilesMonitor() {
        isChanged.set(false);
    }

    @Override
    public void run() {
       log.info("FilesMonitor Thread start");
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            String[] directories = configService.getDirectoriesToWatch();
            for (String path : directories) {
                Path pathToDirectory = Paths.get(path);
                pathToDirectory.register(
                        watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE);
            }

            WatchKey key;
            while ((key = watchService.take()) != null) {
                isChanged.set(true);
                key.pollEvents().forEach(event -> log.info("Files changed {}",event.context()));
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    public boolean isChanged() {
        return isChanged.get();
    }

    public void isActual() {
        log.debug("Changes were accepted");
        isChanged.set(false);
    }
}