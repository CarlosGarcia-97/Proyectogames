package mx.edu.utez.model.games;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import mx.edu.utez.model.category.BeanCategory;
import mx.edu.utez.service.ConnectionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DaoGames {
    private Connection con;
    private CallableStatement cstm;
    private ResultSet rs;
    final private Logger CONSOLE = LoggerFactory.getLogger(DaoGames.class);

    public List<BeanGames> findAll(){
        List<BeanGames> listGames = new ArrayList<>();
        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_findGames}");
            rs = cstm.executeQuery();

            while (rs.next()){
                BeanCategory beanCategory= new BeanCategory();
                BeanGames beanGames = new BeanGames();

                beanCategory.setIdCategory(rs.getInt("idCategory"));
                beanCategory.setName(rs.getString("name"));
                beanCategory.setStatus(rs.getInt("status"));

                beanGames.setIdGames(rs.getInt("idGames"));
                beanGames.setName(rs.getString("name"));
                byte[] imgBytes = rs.getBytes("imgGames");
                String photo = Base64.getEncoder().encodeToString(imgBytes);
                beanGames.setDatePremiere(rs.getString("datePremiere"));
                beanGames.setStatus(rs.getInt("status"));
                beanGames.setCategory_idCategory(beanCategory);
                listGames.add(beanGames);


            }
        }catch (SQLException e){
            CONSOLE.error("Ocurrio un error : " + e.getMessage());

        }finally {
            ConnectionMySQL.closeConnections(con, cstm, rs);

        }

        return listGames;

    }

    public BeanGames findById(long id) {
        BeanGames games = null;
        try {

            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_findById(?)}");
            cstm.setLong(1, id);
            rs = cstm.executeQuery();

            if (rs.next()) {
                games = new BeanGames();
                BeanCategory beanCategory = new BeanCategory();
                beanCategory.setIdCategory(rs.getInt("idCategory"));
                beanCategory.setName(rs.getString("name"));
                beanCategory.setStatus(rs.getInt("status"));
                games.setIdGames(rs.getInt("idGames"));
                games.setName(rs.getString("name"));
                byte[] imgBytes = rs.getBytes("imgGames");
                String photo = Base64.getEncoder().encodeToString(imgBytes);
                games.setDatePremiere(rs.getString("date_premiere"));
                games.setStatus(rs.getInt("status"));
                games.setCategory_idCategory(beanCategory);

            }
        } catch (SQLException e) {
            CONSOLE.error("Ha ocurrido un error: " + e.getMessage());
        } finally {
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return games;
    }

    public boolean create(BeanGames games) {
        boolean flag = false;
        try {
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_create(?,?,?,?)}");
            cstm.setString(1, games.getName());
            cstm.setBytes(2, Base64.getDecoder().decode(games.getImgGame()));
            cstm.setString(3, games.getDatePremiere());
            cstm.setInt(4, games.getCategory_idCategory().getIdCategory());




            flag = cstm.execute();
        } catch (SQLException e) {
            CONSOLE.error("Ha ocurrido un error: " + e.getMessage());
        } finally {
            ConnectionMySQL.closeConnections(con, cstm);
        }
        return flag;
    }

    public boolean update(BeanGames games) {
        boolean flag = false;
        try {
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_update(?,?,?,?,?)}");
            cstm.setInt(1, games.getIdGames());
            cstm.setString(2, games.getName());
            cstm.setBytes(3, Base64.getDecoder().decode(games.getImgGame()));
            cstm.setString(4, games.getDatePremiere());
            cstm.setInt(5, games.getCategory_idCategory().getIdCategory());


            flag = cstm.execute();
        } catch (SQLException e) {
            CONSOLE.error("Ha ocurrido un error: " + e.getMessage());
        } finally {
            ConnectionMySQL.closeConnections(con, cstm);
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        try {
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("{call sp_delete(?)}");
            cstm.setLong(1, id);

            flag = cstm.execute();
        } catch (SQLException e) {
            CONSOLE.error("Ha ocurrido un error: " + e.getMessage());
        } finally {
            ConnectionMySQL.closeConnections(con, cstm);
        }
        return flag;
    }

    

}
