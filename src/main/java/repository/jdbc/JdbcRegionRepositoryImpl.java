package repository.jdbc;

import model.Region;
import repository.RegionRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static repository.jdbc.JdbcUtils.*;

public class JdbcRegionRepositoryImpl implements RegionRepository {

    private ResultSet resultSet;
    private final Statement statement = JdbcConnection.getConnection();

    @Override
    public Region getById(Long id) {

        Region region = new Region();
        try {

            resultSet = statement.executeQuery(String.format(REGION_GET_BY_ID, id));

            if (resultSet.next()) {
                region = new Region(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getLong(3));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return region;
    }


    @Override
    public Region create(String regionName, Long writerId) {

        Region region = new Region();
        try {

            if (statement.executeUpdate(String.format(REGION_CREATE, regionName, writerId)) > 0) {

                resultSet = statement.executeQuery(RESULT_REGION_CREATE);

                if (resultSet.next()) {
                    region = new Region(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getLong(3));
                }

                resultSet.close();
            } else {
                System.out.println(" Писатель с таким ID уже существует...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return region;
    }


    @Override
    public Region update(Long id, String regionName, Long writerId) {

        Region region = new Region();
        try {

            if (statement.executeUpdate(String.format(REGION_UPDATE, regionName, writerId, id)) > 0) {

                resultSet = statement.executeQuery(String.format(RESULT_REGION_UPDATE, id));

                if (resultSet.next()) {
                    region = new Region(resultSet.getLong(1), resultSet.getString(2),
                            resultSet.getLong(3));
                }

                resultSet.close();

            } else {
                System.out.println("Не возможно изменить запись...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return region;
    }


    @Override
    public void deleteById(Long id) {

        try {

            if (statement.executeUpdate(String.format(REGION_DELETE, id)) > 0) {

                System.out.println("... Данные удалены ...");
            } else {
                System.out.println("... Такой страны нет ...");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<Region> getAll() {

        List<Region> regionList = new ArrayList<>();

        try {

            resultSet = statement.executeQuery(REGION_GET_ALL);

            while (resultSet.next()) {
                Region region = new Region(resultSet.getLong(1),
                        resultSet.getString(2), resultSet.getLong(3));

                regionList.add(region);
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return regionList;
    }

}
