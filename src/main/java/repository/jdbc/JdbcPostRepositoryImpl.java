package repository.jdbc;

import model.Post;
import repository.PostRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static repository.jdbc.JdbcUtils.*;

public class JdbcPostRepositoryImpl implements PostRepository {

    private ResultSet resultSet;
    private final Statement statement = JdbcConnection.getConnection();


    @Override
    public Post getById(Long id) {

        Post post = new Post();

        try {
            resultSet = statement.executeQuery(String.format(POST_GET_BY_ID, id));

            if (resultSet.next()) {

                post = new Post(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getTimestamp(3).toLocalDateTime().toString(),
                        resultSet.getTimestamp(4).toLocalDateTime().toString(),
                        resultSet.getLong(5));
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }


    @Override
    public Post create(Long writer_id, String content) {

        Post post = new Post();

        try {
            statement.execute(String.format(POST_CREATE, content, Timestamp.valueOf(LocalDateTime.now()), writer_id));
            resultSet = statement.executeQuery(RESULT_POST_CREATE);

            if (resultSet.next()) {

                post = new Post(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getTimestamp(3).toLocalDateTime().toString(),
                        resultSet.getTimestamp(4).toLocalDateTime().toString(),
                        resultSet.getLong(5));
            }

            resultSet.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }


    @Override
    public Post update(Long id, Long writer_id, String content) {

        Post post = new Post();

        try {
            if (statement.executeUpdate(String.format(POST_UPDATE, content, Timestamp.valueOf(LocalDateTime.now()),
                    writer_id, id)) > 0) {

                resultSet = statement.executeQuery(String.format(RESULT_POST_UPDATE, id));

                if (resultSet.next()) {

                    post = new Post(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getTimestamp(3).toLocalDateTime().toString(),
                            resultSet.getTimestamp(4).toLocalDateTime().toString(),
                            resultSet.getLong(5));
                }

                resultSet.close();

            } else {
                System.out.println("Не возможно изменить не существующую запись");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }


    @Override
    public void deleteById(Long id) {

        try {

            if (statement.executeUpdate(String.format(POST_DELETE, id)) > 0) {

                System.out.println("... Данные удалены ...");
            } else {
                System.out.println("... Такой записи нет ...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<Post> getAll() {

        List<Post> postList = new ArrayList<>();

        try {
            resultSet = statement.executeQuery(POST_GET_ALL);

            while (resultSet.next()) {

                Post post = new Post(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getTimestamp(3).toLocalDateTime().toString(),
                        resultSet.getTimestamp(4).toLocalDateTime().toString(),
                        resultSet.getLong(5));

                postList.add(post);
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postList;
    }

}
