package repository.jdbc;

import model.Post;
import model.Region;
import model.Writer;
import repository.WriterRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static repository.jdbc.JdbcUtils.*;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private ResultSet resultSet;
    private final Statement statement = JdbcConnection.getConnection();


    @Override
    public Writer getById(Long id) {

        Writer writer = new Writer();
        List<Post> postList = new ArrayList<>();


        try {

            resultSet = statement.executeQuery(String.format(WRITER_GET_BY_ID_FOR_POSTS, id));

            while (resultSet.next()) {
                postList.add(new Post(resultSet.getString(1)));
            }

            resultSet = statement.executeQuery(String.format(WRITER_GET_BY_ID, id));

            if (resultSet.next()) {

                writer = new Writer(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3),
                        new Region(resultSet.getString(4)), postList);
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writer;
    }


    @Override
    public Writer create(String firstName, String lastName) {

        Writer writer = new Writer();
        List<Post> postList = new ArrayList<>();

        try {

            statement.execute(String.format(WRITER_CREATE, firstName, lastName));
            statement.execute(WRITER_CREATE_IN_REGION);
            statement.execute(String.format(WRITER_CREATE_IN_POST,Timestamp.valueOf(LocalDateTime.now())));

            statement.executeQuery(WRITER_CREATE_FOR_POSTS);

            while (resultSet.next()) {
                postList.add(new Post(resultSet.getString(1)));
            }

            resultSet = statement.executeQuery(RESULT_WRITER_CREATE);

            if (resultSet.next()) {

                writer = new Writer(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3),
                        new Region(resultSet.getString(4)), postList);
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writer;
    }


    @Override
    public Writer update(Long id, String firstName, String lastName) {

        Writer writer = new Writer();
        List<Post> postList = new ArrayList<>();

        try {

            if (statement.executeUpdate(String.format(WRITER_UPDATE, firstName, lastName, id)) > 0) {

                resultSet = statement.executeQuery(String.format(WRITER_UPDATE_FOR_POSTS, id));

                while (resultSet.next()) {
                    postList.add(new Post(resultSet.getString(1)));
                }

                resultSet = statement.executeQuery(String.format(RESULT_WRITER_UPDATE,id));

                if (resultSet.next()) {

                    writer = new Writer(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getString(3),
                            new Region(resultSet.getString(4)), postList);
                }

                resultSet.close();

            } else {
                System.out.println("Не возможно изменить не существующую запись");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writer;
    }


    @Override
    public void deleteById(Long id) {

        try {

            if (statement.executeUpdate(String.format(WRITER_DELETE, id)) > 0) {

                if (statement.executeUpdate(String.format(DELETE_WRITER_ID_IN_POST, id)) > 0) {
                    System.out.println("... ID  писателя в папке пост удален ...");

                    if (statement.executeUpdate(String.format(DELETE_WRITER_ID_IN_REGION, id)) > 0) {
                        System.out.println("... ID  писателя в папке регион удален ...");

                    } else {
                        System.out.println("... ID  писателя в папке регион не существует ...");
                    }

                } else {
                    System.out.println("... ID  писателя в папке пост не существует ...");
                }

                System.out.println("... Данные удалены ...");
            } else {
                System.out.println("... Такого писателя нет ...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Writer> getAll() {

        List<Writer> writerList = new ArrayList<>();
        List<Post> postList = new ArrayList<>();


        // для отслеживания повторных ID
        Set<Long> idSet = new HashSet<>();
        List<Long> idList = new LinkedList<>();


        try {

            resultSet = statement.executeQuery(WRITER_GET_ALL);

            while (resultSet.next()) {

                Writer writer = new Writer();

                // одноразовый постлист
                List<Post> posts = new ArrayList<>();

                // поиск двух или нескольких постов у одного писателя
                if (idSet.add(resultSet.getLong(1))) {

                    idList.add(resultSet.getLong(1));
                    postList.add(new Post(resultSet.getString(5)));
                    posts.add(new Post(resultSet.getString(5)));

                    writer = new Writer(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getString(3),
                            new Region(resultSet.getString(4)), posts);

                    writerList.add(writer);

                } else {

                    int indexDuplicateId = idList.indexOf(resultSet.getLong(1));

                    posts.add(postList.get(indexDuplicateId));
                    posts.add(new Post(resultSet.getString(5)));

                    writer = new Writer(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getString(3), writer.getRegionName(), posts);

                    writerList.set(indexDuplicateId, writer);
                }
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return writerList;
    }
}
