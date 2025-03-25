package com.sprint.mission.blog.repository;

import com.sprint.mission.blog.entity.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class BasicUserRepository implements UserRepository {

    private final Map<String, User> users;
    private final Path DIRECTORY;
    private final String EXTENSION = ".ser";

    public BasicUserRepository(@Value("${discodeit.repository.file-directory:data}") String fileDirectory) {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), fileDirectory, User.class.getSimpleName());
        if (Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.users = loadUsers();
    }

    private Map<String, User> loadUsers() {
        try (Stream<Path> paths = Files.list(DIRECTORY)) {
            return paths
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try (
                            FileInputStream fis = new FileInputStream(path.toFile());
                            ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            return (User) ois.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException("유저 파일을 읽는 도중 오류 발생: " + path.getFileName(), e);
                        }
                    })
                    .collect(Collectors.toMap(User::getId, user -> user));
        } catch (IOException e) {
            throw new RuntimeException("사용자 데이터를 불러오는 중 오류 발생", e);
        }
    }

    @Override
    public User save(User user) {
        Path filePath = DIRECTORY.resolve(user.getId() + EXTENSION);
        try (
            FileOutputStream fos = new FileOutputStream(filePath.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException("유저 데이터를 저장하는 중 오류 발생", e);
        }

        users.put(user.getId(), user);

        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public boolean existsById(String id) {
        return users.containsKey(id);
    }

    @Override
    public void deleteById(String id) {
        Path filePath = DIRECTORY.resolve(id + EXTENSION);
        try {
            Files.deleteIfExists(filePath);  // 파일이 있으면 삭제, 없으면 무시
        } catch (IOException e) {
            throw new RuntimeException("사용자 파일 삭제 중 오류 발생: " + filePath.getFileName(), e);
        }
        users.remove(id);
    }
}
